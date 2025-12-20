package pl.polsl.wf.data.repository;

import java.util.List;

import pl.polsl.wf.domain.model.Language;
import pl.polsl.wf.domain.repository.LanguagesRepository;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class LanguagesRepositoryImpl implements LanguagesRepository
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
