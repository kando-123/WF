package pl.polsl.wf.data.source;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.File;
import java.io.FileWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import pl.polsl.wf.common.util.DataCallback;
import pl.polsl.wf.data.model.TranslationDto;

/**
 * Implementation of ITranslationDataSource interface for online data source. It communicates
 * with Wiktionary and parses the responses.s
 */

class MarkdownHeader
{
    public final int resStart;
    /// position AFTER the markdown ends
    public final int resEnd;
    public final String headword;
    public MarkdownHeader(int level, int startPos, String markdown)
    {
        Pattern regex = Pattern.compile("(^|[^=])={"+level+"} ?([^=]+?) ?={"+level+"}($|[^=])");
        Matcher matcher = regex.matcher(markdown);

        if (!matcher.find(startPos))
        {
            resStart = -1;
            resEnd = -1;
            headword = "";
        }
        else
        {
            // if the first symbol was ^, the result starts at 0
            // else remove the additional character
            resStart = matcher.start() + matcher.group(1).length();
            // if the last symbol was $, remove nothing
            // else remove the additional character
            resEnd = matcher.end() - matcher.group(3).length();
            headword = matcher.group(2);
        }
    }
}

public class OnlineDataSource implements TranslationDataSource
{



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


    private String getMarkdownChunk(int level, int start, String headword, String markdow)
    {
        return  "";

    }
    @Override
    public void getTranslations(String headword, String mainLanguageCode, List<String> foreignLanguageCodes, TranslationDirection direction, DataCallback<List<TranslationDto>> callback)
    {
        String markdown = getMarkdownForHeadword(headword);

        if (direction == TranslationDirection.UNIDIRECTIONAL_TO_MAIN)
        {
            for (String language: foreignLanguageCodes)
            {
                String langChunk = getMarkdownChunk(2, 0, language, markdown);
                for (String attribute : TranslationDto.getAllAttributes())
                {
                    String attrChunk = getMarkdownChunk(3, 0, attribute, langChunk);
                    
                }
            }

        }

        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public List<String> getHints(String input, String mainLanguageCode, List<String> foreignLanguageCodes, TranslationDirection direction, int maxCount, DataCallback<List<String>> callback)
    {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}
