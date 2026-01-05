package pl.polsl.wf.domain.usecase;

import java.util.List;

import javax.inject.Inject;

import pl.polsl.wf.common.util.DataCallback;
import pl.polsl.wf.common.util.WrapperDataCallback;
import pl.polsl.wf.domain.model.Language;
import pl.polsl.wf.domain.repository.LanguagesRepository;

public class GetActiveLanguagesUseCase
{
    private final LanguagesRepository languagesRepo;

    @Inject
    public GetActiveLanguagesUseCase(LanguagesRepository languagesRepo)
    {
        this.languagesRepo = languagesRepo;
    }

    public List<Language> execute()
    {
        WrapperDataCallback<List<Language>> wrapper = new WrapperDataCallback<>();
        languagesRepo.getAllLanguages(wrapper);
        try
        {
            List<Language> languages = wrapper.get();
            languages.removeIf(language -> !language.active());
            return languages;
        }
        catch (Exception exc)
        {
            return null;
        }
    }
}
