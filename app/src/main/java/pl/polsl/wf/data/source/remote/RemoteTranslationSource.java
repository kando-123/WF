package pl.polsl.wf.data.source.remote;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import pl.polsl.wf.data.model.TranslationDto;
import pl.polsl.wf.data.model.TranslationEntryDto;
import pl.polsl.wf.data.model.TranslationEntryPhraseDto;

public class RemoteTranslationSource {

    private RemoveComments removeComments;
    private ResolveLinks resolveLinks;
    private RemoveHTML removeHTML;
    private ResolveTemplates resolveTemplates;
    private Pattern translationsToForeignRegex;
    private Pattern transliterationRegex;
    private  Pattern translationChunkRegex;

    private WiktionaryApi wiktionaryApi;


    private static final List<String> allAttributes = List.of(
            "Number", "Phonogram", "Gerund", "Adverb", "Determiner", "Compound part",
            "Suffix", "Ideophone", "Prepositional phrase", "Affixations", "Preposition",
            "Classifier", "Relative", "Punctuation mark", "Conjunction", "Article",
            "Letter", "Numeral", "Affix", "Noun", "Verb", "Pronoun", "Contraction", "Inflection",
            "Phrase", "Proper noun", "Postposition", "Adjective", "Prefix", "Syllable", "Particle",
            "Participle", "Stem set", "Counter", "Adnominal", "Symbol", "Interjection"
    );


    public RemoteTranslationSource()
    {
        removeHTML = new RemoveHTML();
        resolveLinks = new ResolveLinks();
        removeComments = new RemoveComments();
        resolveTemplates = new ResolveTemplates();
        translationsToForeignRegex  = Pattern.compile("\\{\\{tt?\\+?\\|.+?\\|(.+?)\\}\\}");
        transliterationRegex = Pattern.compile("tr=(.+?)(\\}\\}|\\||$)");
        translationChunkRegex =
                Pattern.compile("\\{\\{trans-top\\|([\\w\\W]+?)\\}\\}([\\w\\W]+?)\\{\\{trans-bottom\\}\\}");
        wiktionaryApi = new WiktionaryApi();
    }
    private List<TranslationEntryPhraseDto> extractTranslationsToForeign(CharSequence input)
    {
        Matcher m = translationsToForeignRegex.matcher(input);
        List<TranslationEntryPhraseDto> res = new ArrayList<>();
        while (m.find())
        {
            String translation = transliterationRegex.matcher(m.group(1)).replaceAll("($1)").replace("|"," ");
            res.addAll(resolveLinks.process(translation));
//            res.add(new TranslationEntryPhraseDto(translation));
        }
        return res;
    }
    public List<TranslationDto> getTranslationsToForeign(
            String headword,
            List<String> foreigns
    ) throws Exception
    {

        List<TranslationDto> res = new ArrayList<>();

        String markdown = wiktionaryApi.getPageContents(headword);
        markdown = removeComments.process(markdown);

        MarkdownHeader langHeader = MarkdownHeader.headerWithAnyKeyword(List.of("English"), 0, markdown);
        if (langHeader.next())
        {
            Pattern singleTranslationRegex =
                    Pattern.compile("\\n\\*:? ("+String.join("|", foreigns)+")(.+)");


            MarkdownChunk langChunk = new MarkdownChunk(langHeader, markdown);
            MarkdownHeader attrHeader =
                    MarkdownHeader.headerWithAnyKeyword(allAttributes, 0, langChunk.contents);
            while(attrHeader.next())
            {
                MarkdownChunk attrChunk = new MarkdownChunk(attrHeader, langChunk.contents);
                Matcher translationsChunk = translationChunkRegex.matcher(attrChunk.contents);
                while (translationsChunk.find())
                {
                    Matcher singleTranslationMatcher = singleTranslationRegex.matcher(translationsChunk.group());
                    while(singleTranslationMatcher.find())
                    {
                        res.add(new TranslationDto(
                                headword,
                                List.of(attrHeader.getHeadword()),
                                "English",
                                 singleTranslationMatcher.group(1),
                                List.of(new TranslationEntryDto(
                                        translationsChunk.group(1),
                                        extractTranslationsToForeign(singleTranslationMatcher.group(2))))
                        ));
                    }
                }
            }
        }

        return res;
    }

    private List<TranslationEntryDto> translationsFromChunk(MarkdownHeader attrHeader, MarkdownChunk langChunk ) throws Exception
    {
        LeafMarkdownChunk attrChunk = new LeafMarkdownChunk(attrHeader, langChunk.contents);
        List<TranslationEntryDto> translationEntryDtos = new ArrayList<>();

        for (String translation: attrChunk.getTranslations())
        {
            translation = removeHTML.process(translation);
            translation = resolveTemplates.process(translation);
            List<TranslationEntryPhraseDto> translationEntryPhraseDtos =
                    resolveLinks.process(translation);

            translationEntryDtos.add(
                    new TranslationEntryDto(null, translationEntryPhraseDtos));
        }

        return translationEntryDtos;
    }


    public List<TranslationDto> getTranslationsToEnglish(
            String headword,
            List<String> foreigns
    ) throws Exception
    {
        List<TranslationDto> res = new ArrayList<>();

        String markdown = wiktionaryApi.getPageContents(headword);
        markdown = removeComments.process(markdown);

        MarkdownHeader langHeader = MarkdownHeader.headerWithAnyKeyword(foreigns, 0, markdown);
        while (langHeader.next())
        {
            MarkdownChunk langChunk = new MarkdownChunk(langHeader, markdown);
            MarkdownHeader attrHeader =
                    MarkdownHeader.headerWithAnyKeyword(allAttributes, 0, langChunk.contents);
            while(attrHeader.next())
            {
                res.add(new TranslationDto(
                        headword,
                        List.of(attrHeader.getHeadword()),
                        langHeader.getHeadword(),
                        "English",
                        translationsFromChunk(attrHeader, langChunk)
                ));
            }
        }

        return res;
    }
}
