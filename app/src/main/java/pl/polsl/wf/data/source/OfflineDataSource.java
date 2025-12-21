package pl.polsl.wf.data.source;

import java.util.Collections;
import java.util.List;

import pl.polsl.wf.common.util.DataCallback;
import pl.polsl.wf.data.model.TranslationDto;

/**
 * Implementation of ITranslationDataSource interface for offline data source. It provides
 * translations based on the local files.
 */
public class OfflineDataSource implements TranslationDataSource
{
    @Override
    public void getTranslations(String headword, String mainLanguageCode, List<String> foreignLanguageCodes, TranslationDirection direction, DataCallback<List<TranslationDto>> callback)
    {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public List<String> getHints(String input, String mainLanguageCode, List<String> foreignLanguageCodes, TranslationDirection direction, int maxCount, DataCallback<List<String>> callback)
    {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}
