package pl.polsl.wf.domain.usecase;

import static pl.polsl.wf.common.util.Constants.MAIN_LANGUAGE_CODE;

import javax.inject.Inject;

import pl.polsl.wf.common.util.WrapperDataCallback;
import pl.polsl.wf.domain.model.Language;
import pl.polsl.wf.domain.repository.LanguagesRepository;

public class GetMainLanguageUseCase
{
    private final LanguagesRepository languagesRepo;

    @Inject
    public GetMainLanguageUseCase(LanguagesRepository languagesRepo)
    {
        this.languagesRepo = languagesRepo;
    }

    public Language execute()
    {
        var wrapper = new WrapperDataCallback<Language>();
        languagesRepo.getLanguageByCode(MAIN_LANGUAGE_CODE, wrapper);
        try
        {
            return wrapper.get();
        }
        catch (Exception e)
        {
            return null;
        }
    }
}
