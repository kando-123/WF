package pl.polsl.wf.data.datasource.local;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import pl.polsl.wf.data.datasource.LocalDataSource;
import pl.polsl.wf.data.datasource.local.database.LanguageDao;
import pl.polsl.wf.data.datasource.local.database.TranslationDao;
import pl.polsl.wf.data.datasource.local.entities.LanguageEntity;
import pl.polsl.wf.data.datasource.local.entities.TranslationEntity;

@Singleton
public class RoomLocalDataSource implements LocalDataSource
{
    private final LanguageDao languageDao;
    private final TranslationDao translationDao;

    @Inject
    public RoomLocalDataSource(LanguageDao languageDao,
                               TranslationDao translationDao)
    {
        this.languageDao = languageDao;
        this.translationDao = translationDao;
    }

    @Override
    public List<LanguageEntity> getAllLanguages()
    {
        return Collections.emptyList();
    }

    @Override
    public LanguageEntity getLanguageEntityByCCode(String code)
    {
        return null;
    }

    @Override
    public void insertLanguages(List<LanguageEntity> languages)
    {

    }

    @Override
    public void updateLanguage(LanguageEntity language)
    {

    }

    @Override
    public void deleteDictionary(String code)
    {

    }

    @Override
    public List<TranslationEntity> searchTranslations(String query, String code)
    {
        return Collections.emptyList();
    }
}
