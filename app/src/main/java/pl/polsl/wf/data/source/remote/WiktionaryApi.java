package pl.polsl.wf.data.source.remote;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WiktionaryApi {
    private OkHttpClient httpClient;

    private String makeCall(String url) throws Exception
    {
        Request request = new Request.Builder()
                .url(url)
                .header("User-Agent", "WiktionaryFiltering/0.0 (tichalik@gmail.com)")
                .build();
        Response response = httpClient.newCall(request).execute();
        return response.body().string();
    }

    public WiktionaryApi()
    {
        httpClient = new OkHttpClient();

    }
    public String getPageContents(String headword) throws Exception
    {
        String url = ("https://en.wiktionary.org/w/index.php?title="
                +headword.replace(" ", "_")
                +"&action=raw");
        return makeCall(url);
    }

    public String getHints(String input, List<String> languages, int maxCount) throws Exception
    {
        String url = "https://en.wiktionary.org/w/api.php?action=query&list=prefixsearch&pssearch="
                + input.replace(" ", "_") + "&format=json";
        return makeCall(url);
    }
}
