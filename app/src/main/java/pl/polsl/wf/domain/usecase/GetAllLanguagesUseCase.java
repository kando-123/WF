package pl.polsl.wf.domain.usecase;

import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import pl.polsl.wf.common.util.DataCallback;
import pl.polsl.wf.common.util.WrapperDataCallback;
import pl.polsl.wf.domain.model.Language;
import pl.polsl.wf.domain.repository.LanguagesRepository;

public class GetAllLanguagesUseCase
{
    private final LanguagesRepository languagesRepo;

    @Inject
    public GetAllLanguagesUseCase(LanguagesRepository languagesRepo)
    {
        this.languagesRepo = languagesRepo;
    }

    public void execute(DataCallback<List<Language>> callback)
    {
        WrapperDataCallback<List<Language>> wrapper = new WrapperDataCallback<>();
        languagesRepo.getAllLanguages(wrapper);
        try
        {
            List<Language> list = wrapper.get();
            Comparator<Language> comparator = new Comparator<>()
            {
                @Override
                public int compare(Language lhs, Language rhs)
                {
                    if (lhs.active() == rhs.active())
                    {
                        return lhs.compareByNameTo(rhs);
                    }
                    else
                    {
                        return (rhs.active() ? 1 : 0) - (lhs.active() ? 1 : 0);
                    }
                }
            };
            list.sort(comparator);
            callback.onSuccess(list);
        }
        catch (Exception exc)
        {
            callback.onError(exc);
        }
    }
}
