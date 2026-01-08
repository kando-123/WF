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

public class OnlineDataSource implements TranslationDataSource
{
    private RemoteTranslationSource source;
    private OkHttpClient httpClient;
    private Pattern titleRegex;
    public OnlineDataSource()
    {
        httpClient = new OkHttpClient();
        source = new RemoteTranslationSource();
        titleRegex = Pattern.compile("\"title\": \"(.+?)\"");
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
            String url = "https://en.wiktionary.org/w/api.php?action=query&list=prefixsearch&pssearch="
                    + input.replace(" ", "_") + "&format=json";
            Request request = new Request.Builder()
                    .url(url)
                    .header("User-Agent", "WiktionaryFiltering/0.0 (tichalik@gmail.com)")
                    .build();
            Response response = httpClient.newCall(request).execute();

            Matcher m = titleRegex.matcher(response.body().string());
            List<String> res = new ArrayList<>();
            while (m.find())
                res.add(m.group(1));

            callback.onSuccess(res);
        } catch (Exception e) {
            callback.onError(e);
        }
    }
}
