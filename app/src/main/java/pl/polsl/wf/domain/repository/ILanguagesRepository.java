package pl.polsl.wf.domain.repository;

import java.util.List;

import pl.polsl.wf.domain.model.Language;

public interface ILanguagesRepository
{
    List<Language> getAllLanguages();
    Language getLanguageByCode(String languageCode);
    void updateLanguage(Language language);

    boolean isDictionaryDownloaded(String languageCode);
}
