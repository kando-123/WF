package pl.polsl.wf.data.source.remote;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pl.polsl.wf.data.model.TranslationDto;
import pl.polsl.wf.data.model.TranslationEntryDto;
import pl.polsl.wf.data.model.TranslationEntryPhraseDto;

public class RemoteSource {

    private RemoveComments removeComments;
    private ResolveLinks resolveLinks;
    private RemoveHTML removeHTML;
    private ResolveTemplates resolveTemplates;
    private Pattern translationsToForeignRegex;
    private Pattern transliterationRegex;
    private  Pattern translationChunkRegex;


    private static final List<String> allAttributes = List.of(
            "Number", "Phonogram", "Gerund", "Adverb", "Determiner", "Compound part",
            "Suffix", "Ideophone", "Prepositional phrase", "Affixations", "Preposition",
            "Classifier", "Relative", "Punctuation mark", "Conjunction", "Article",
            "Letter", "Numeral", "Affix", "Noun", "Verb", "Pronoun", "Contraction", "Inflection",
            "Phrase", "Proper noun", "Postposition", "Adjective", "Prefix", "Syllable", "Particle",
            "Participle", "Stem set", "Counter", "Adnominal", "Symbol", "Interjection"
    );


    public RemoteSource()
    {
        removeHTML = new RemoveHTML();
        resolveLinks = new ResolveLinks();
        removeComments = new RemoveComments();
        resolveTemplates = new ResolveTemplates();
        translationsToForeignRegex  = Pattern.compile("\\{\\{tt?\\+?\\|.+?\\|(.+?)\\}\\}");
        transliterationRegex = Pattern.compile("tr=(.+?)(\\}\\}|\\||$)");
        translationChunkRegex =
                Pattern.compile("\\{\\{trans-top\\|([\\w\\W]+?)\\}\\}([\\w\\W]+?)\\{\\{trans-bottom\\}\\}");

    }
    private String getMarkdownForHeadword(String headword) throws Exception
    {
        URI uri = new URI("https://en.wiktionary.org/w/index.php?title="
                +headword.replace(" ", "_")
                +"&action=raw");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .header("User-Agent", "WiktionaryFiltering/0.0 (tichalik@gmail.com)")
                .GET()
                .build();
        HttpResponse<String> response = HttpClient.newBuilder()
                .build()
                .send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }
    private List<TranslationEntryPhraseDto> extractTranslationsToForeign(CharSequence input)
    {
        Matcher m = translationsToForeignRegex.matcher(input);
        List<TranslationEntryPhraseDto> res = new ArrayList<>();
        while (m.find())
        {
            res.add(new TranslationEntryPhraseDto(
                    transliterationRegex.matcher(m.group(1)).replaceAll("($1)").replace("|"," ")
            ));
        }
        return res;
    }
    public List<TranslationDto> getTranslationsToForeign(
            String headword,
            List<String> foreigns
    ) throws Exception
    {

        List<TranslationDto> res = new ArrayList<>();

        String markdown = getMarkdownForHeadword(headword);
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

        String markdown = getMarkdownForHeadword(headword);
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
