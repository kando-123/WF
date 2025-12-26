package pl.polsl.wf.domain.usecase;

import javax.inject.Inject;

import pl.polsl.wf.common.util.DataCallback;
import pl.polsl.wf.domain.model.Language;
import pl.polsl.wf.domain.repository.LanguagesRepository;

public class DownloadLanguageUseCase
{
    private final LanguagesRepository languagesRepo;

    @Inject
    public DownloadLanguageUseCase(LanguagesRepository languagesRepo)
    {
        this.languagesRepo = languagesRepo;
    }

    public void execute(String languageCode, DataCallback<Language> callback)
    {
        languagesRepo.downloadLanguage(languageCode, callback);
    }
}
