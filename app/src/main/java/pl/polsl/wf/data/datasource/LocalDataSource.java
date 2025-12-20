package pl.polsl.wf.data.datasource;

import java.util.List;

import pl.polsl.wf.data.datasource.local.entities.LanguageEntity;
import pl.polsl.wf.data.datasource.local.entities.TranslationEntity;

public interface LocalDataSource
{
    List<LanguageEntity> getAllLanguages();
    LanguageEntity getLanguageEntityByCCode(String code);
    void insertLanguages(List<LanguageEntity> languages);
    void updateLanguage(LanguageEntity language);
    void deleteDictionary(String code);
    List<TranslationEntity> searchTranslations(String query, String code);
}
