package pl.polsl.wf.domain.repository;

import java.util.List;

import pl.polsl.wf.common.util.DataCallback;
import pl.polsl.wf.data.model.TranslationDto;
import pl.polsl.wf.data.source.TranslationDirection;

public interface TranslationRepository
{
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
