package pl.polsl.wf.data.repository;

import java.util.List;

import pl.polsl.wf.domain.model.Language;
import pl.polsl.wf.domain.repository.ILanguagesRepository;

import javax.inject.Singleton;

/**
 * Managest the list of languages and their statuses (active, downloaded).
 */
@Singleton
public class LanguagesRepository implements ILanguagesRepository
{
    @Override
    public List<Language> getAllLanguages()
    {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public Language getLanguageByCode(String languageCode)
    {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void updateLanguage(Language language)
    {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public boolean isDictionaryDownloaded(String languageCode)
    {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
