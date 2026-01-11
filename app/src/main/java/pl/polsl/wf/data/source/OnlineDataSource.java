package pl.polsl.wf.data.source;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import pl.polsl.wf.common.util.DataCallback;
import pl.polsl.wf.data.model.TranslationDto;
import pl.polsl.wf.data.source.remote.RemoteTranslationSource;
import pl.polsl.wf.data.source.remote.WiktionaryApi;

public class OnlineDataSource implements TranslationDataSource
{
    private RemoteTranslationSource source;
    private Pattern titleRegex;
    private WiktionaryApi wiktionaryApi;
    public OnlineDataSource()
    {
        source = new RemoteTranslationSource();
        wiktionaryApi = new WiktionaryApi();
        titleRegex = Pattern.compile("\"title\": ?\"(.+?)\"");
    }

    @Override
    public void getTranslations(
            String headword,
            String mainLanguageCode,
            List<String> foreignLanguageCodes,
            TranslationDirection direction,
            DataCallback<List<TranslationDto>> callback
    )
    {
        try {
            List<TranslationDto> res;

            switch (direction) {
                case UNIDIRECTIONAL_TO_MAIN -> {
                    res = source.getTranslationsToEnglish(headword, foreignLanguageCodes);
                }
                case UNIDIRECTIONAL_TO_FOREIGN -> {
                    res = source.getTranslationsToForeign(headword, foreignLanguageCodes);
                }
                case BIDIRECTIONAL -> {
                    res = source.getTranslationsToForeign(headword, foreignLanguageCodes);
                    res.addAll(
                            source.getTranslationsToEnglish(headword, foreignLanguageCodes)
                    );
                }
                default -> {
                    res = new ArrayList<>();
                }
            }
            callback.onSuccess(res);
        }
        catch (Exception e)
        {
            callback.onError(e);
        }
    }
    @Override
    public void getHints(
            String input,
            String mainLanguageCode,
            List<String> foreignLanguageCodes,
            TranslationDirection direction,
            int maxCount,
            DataCallback<List<String>> callback
    )
    {
        try {
            List<String> languages;
            switch (direction) {
                case UNIDIRECTIONAL_TO_MAIN -> {
                    languages = foreignLanguageCodes;
                }
                case UNIDIRECTIONAL_TO_FOREIGN -> {
                    languages = List.of(mainLanguageCode);
                }
                case BIDIRECTIONAL -> {
                    languages = foreignLanguageCodes;
                    languages.add(mainLanguageCode);
                }
                default -> {
                    languages = new ArrayList<>();
                }
            }

            String json = wiktionaryApi.getHints(input, languages, maxCount );
            Matcher m = titleRegex.matcher(json);
            List<String> res = new ArrayList<>();
            while (m.find())
                res.add(m.group(1));

            callback.onSuccess(res);
        } catch (Exception e) {
            callback.onError(e);
        }
    }
}
