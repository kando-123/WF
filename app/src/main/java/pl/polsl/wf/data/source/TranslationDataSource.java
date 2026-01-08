package pl.polsl.wf.data.source;

import java.util.List;

import pl.polsl.wf.common.util.DataCallback;
import pl.polsl.wf.data.model.TranslationDto;

/**
 * Common interface for data sources.
 */
public interface TranslationDataSource
{
    /**
     * Asynchronously performs translation of the {@code headword} from the language defined by
     * {@code sourceLanguageCode} to the languages defined by {@code targetLanguageCodes}.
     * <p>
     * It is recommended to execute the business logic in a separate thread and use the {@code
     * callback} to return the result.
     *
     * @param headword
     * @param mainLanguageCode
     * @param foreignLanguageCodes
     * @param direction
     * @param callback
     */
    void getTranslations(String headword,
                         String mainLanguageCode,
                         List<String> foreignLanguageCodes,
                         TranslationDirection direction,
                         DataCallback<List<TranslationDto>> callback);
    void getHints(String input,
                          String mainLanguageCode,
                          List<String> foreignLanguageCodes,
                          TranslationDirection direction,
                          int maxCount,
                          DataCallback<List<String>> callback);
}
