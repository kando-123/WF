package pl.polsl.wf.domain.usecase;

import pl.polsl.wf.common.util.DataCallback;
import pl.polsl.wf.common.util.WrapperDataCallback;
import pl.polsl.wf.domain.model.Language;
import pl.polsl.wf.domain.repository.LanguagesRepository;

public class ToggleLanguageUseCase
{
    private final LanguagesRepository languagesRepo;

    public ToggleLanguageUseCase(LanguagesRepository languagesRepo)
    {
        this.languagesRepo = languagesRepo;
    }

    public void execute(String languageCode,
                        DataCallback<Language> callback)
    {
        var result = new WrapperDataCallback<Language>();
        languagesRepo.getLanguageByCode(languageCode, result);
        try
        {
            Language language = result.get();
            languagesRepo.setLanguageActive(languageCode, !language.active(), result);

            callback.onSuccess(language);
        }
        catch (Exception e)
        {
            callback.onError(e);
        }
    }
}
