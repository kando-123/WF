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

    public RemoteSource()
    {
        removeHTML = new RemoveHTML();
        resolveLinks = new ResolveLinks();
        removeComments = new RemoveComments();
        resolveTemplates = new ResolveTemplates();
    }
    private String getMarkdownForHeadword(String headword)
    {
        String res = "";
        System.out.println("hjello world");
        try {
            URI uri = new URI("https://en.wiktionary.org/w/index.php?title="+headword+"&action=raw");
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .header("User-Agent", "WiktionaryFiltering/0.0 (tichalik@gmail.com)")
                    .GET()
                    .build();
            HttpResponse<String> response = HttpClient.newBuilder()
                    .build()
                    .send(request, HttpResponse.BodyHandlers.ofString());

            res  = response.body();
        }
        catch(Exception e)
        {
            System.out.println("u fucked up:/");
        }

        return res;
    }

    public List<TranslationDto> getTranslationsToForeign(
            String headword,
            List<String> foreigns
    )
    {

        throw new UnsupportedOperationException();

//        return res;
    }

    private List<TranslationEntryDto> translationsFromChunk(MarkdownHeader attrHeader, MarkdownChunk langChunk )
    {
        MarkdownChunk attrChunk = new MarkdownChunk(attrHeader, langChunk.contents);
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
    )
    {
        List<TranslationDto> res = new ArrayList<>();

        String markdown = getMarkdownForHeadword(headword);
        markdown = removeComments.process(markdown);

        int langHeaderStart = 0;
        for (String language: foreigns)
        {
            MarkdownHeader langHeader = new MarkdownHeader(2, langHeaderStart, language, markdown);
            MarkdownChunk langChunk = new MarkdownChunk(langHeader, markdown);
            for (String attribute : TranslationDto.getAllAttributes())
            {
                MarkdownHeader attrHeader = new MarkdownHeader(3, 0, attribute,langChunk.contents);
                List<TranslationEntryDto> translationEntryDtos = translationsFromChunk(attrHeader, langChunk);

                ///  level 4 headers come from words with several etymologies. we need to get them all

                int start = 0;
                while (true)
                {
                    attrHeader = new MarkdownHeader(4, start, attribute, langChunk.contents);
                    if (attrHeader.resEnd == -1)
                    {
                        break;
                    }
                    else
                    {
                        translationEntryDtos.addAll(translationsFromChunk(attrHeader, langChunk));
                        start = attrHeader.resEnd;
                    }
                }

                res.add(new TranslationDto(headword, List.of(attribute), language,
                        "English", translationEntryDtos ));
            }
        }

        return res;
    }
}
