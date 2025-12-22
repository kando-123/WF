package pl.polsl.wf.domain.usecase;

import pl.polsl.wf.common.util.DataCallback;
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
        var result = new DataCallbackImpl();
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

    private static class DataCallbackImpl implements DataCallback<Language>
    {
        private Language language;
        private Exception exception;

        @Override
        public void onSuccess(Language language)
        {
            this.language = language;
        }

        @Override
        public void onError(Exception exception)
        {
            this.exception = exception;
        }

        public void reset()
        {
            language = null;
            exception = null;
        }

        public Language get() throws Exception
        {
            if (exception != null)
            {
                throw exception;
            }
            return language;
        }
    }
}
