package pl.polsl.wf.data.repository;

import java.util.List;

import pl.polsl.wf.common.util.DataCallback;
import pl.polsl.wf.domain.model.Language;
import pl.polsl.wf.domain.repository.LanguagesRepository;

import javax.inject.Singleton;

/**
 * Managest the list of languages and their statuses (active, downloaded).
 */
@Singleton
public class LanguagesRepositoryImpl implements LanguagesRepository
{
    @Override
    public void getAllLanguages(DataCallback<List<Language>> callback)
    {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public void getLanguageByCode(String languageCode, DataCallback<Language> callback)
    {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public void setLanguageActive(String languageCode, boolean active, DataCallback<Language> callback)
    {
        throw new UnsupportedOperationException("Not implemented yet.");
    }

    @Override
    public void downloadLanguage(String languageCode)
    {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}
