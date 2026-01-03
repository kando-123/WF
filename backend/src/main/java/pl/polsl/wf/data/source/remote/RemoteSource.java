package pl.polsl.wf.data.source.remote;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

import pl.polsl.wf.data.model.TranslationDto;
import pl.polsl.wf.data.model.TranslationEntryDto;
import pl.polsl.wf.data.model.TranslationEntryPhraseDto;

public class RemoteSource {

    RemoveComments removeComments;
    ResolveLinks resolveLinks;
    RemoveHTML removeHTML;
    ResolveTemplates resolveTemplates;

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

    public List<TranslationDto> getTranslationsToForeign(
            String headword,
            List<String> foreigns
    ) throws Exception
    {
        String markdown = getMarkdownForHeadword(headword);
        // all translation blocks are either english or multilingual
        // we can include both
        // there might be multiple blocks for multiple etymologies.
        throw new UnsupportedOperationException();

//        return res;
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
