package pl.polsl.wf.data.source;

import java.util.ArrayList;
import java.util.List;

import pl.polsl.wf.common.util.DataCallback;
import pl.polsl.wf.data.model.TranslationDto;
import pl.polsl.wf.data.source.remote.RemoteSource;

public class OnlineDataSource implements TranslationDataSource
{
    RemoteSource source;
    public OnlineDataSource()
    {
        source = new RemoteSource();
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
    public List<String> getHints(String input, String mainLanguageCode, List<String> foreignLanguageCodes, TranslationDirection direction, int maxCount, DataCallback<List<String>> callback)
    {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}