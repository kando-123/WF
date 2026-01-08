package pl.polsl.wf.domain.repository;

import java.util.List;

import pl.polsl.wf.common.util.DataCallback;
import pl.polsl.wf.domain.model.Language;

public interface LanguagesRepository
{
    void getAllLanguages(DataCallback<List<Language>> callback);

    void getLanguageByCode(String languageCode,
                           DataCallback<Language> callback);

    void setLanguageActive(String languageCode,
                           boolean active,
                           DataCallback<Language> callback);

    void downloadLanguage(String languageCode, DataCallback<Language> callback);
}
