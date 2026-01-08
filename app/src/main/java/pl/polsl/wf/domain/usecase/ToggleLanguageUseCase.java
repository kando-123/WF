package pl.polsl.wf.domain.usecase;

import javax.inject.Inject;

import pl.polsl.wf.common.util.DataCallback;
import pl.polsl.wf.common.util.WrapperDataCallback;
import pl.polsl.wf.domain.model.Language;
import pl.polsl.wf.domain.repository.LanguagesRepository;

public class ToggleLanguageUseCase
{
    private final LanguagesRepository languagesRepo;

    @Inject
    public ToggleLanguageUseCase(LanguagesRepository languagesRepo)
    {
        this.languagesRepo = languagesRepo;
    }

    public void execute(String languageCode, DataCallback<Language> callback)
    {
        var wrapper = new WrapperDataCallback<Language>();
        languagesRepo.getLanguageByCode(languageCode, wrapper);
        try
        {
            Language language = wrapper.get();
            languagesRepo.setLanguageActive(languageCode, !language.active(), callback);
        }
        catch (Exception e)
        {
            callback.onError(e);
        }
    }
}
