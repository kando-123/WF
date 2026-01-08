package pl.polsl.wf.domain.usecase;

import java.util.List;
import java.util.concurrent.ExecutorService;

import javax.inject.Inject;

import pl.polsl.wf.common.util.DataCallback;
import pl.polsl.wf.data.source.TranslationDirection;
import pl.polsl.wf.domain.model.Translation;
import pl.polsl.wf.domain.repository.*;

public class TranslateUseCase
{
    private final TranslationRepository translationRepo;
    private final ExecutorService executor;

    @Inject
    public TranslateUseCase(TranslationRepository translationRepo,
                            ExecutorService executor)
    {
        this.translationRepo = translationRepo;
        this.executor = executor;
    }

    public void execute(String headword,
                        String mainLanguageCode,
                        List<String> foreignLanguageCodes,
                        TranslationDirection direction,
                        DataCallback<List<Translation>> callback)
    {
        executor.execute(new Runnable()
        {
            @Override
            public void run()
            {
                translationRepo.getTranslations(headword,
                        mainLanguageCode,
                        foreignLanguageCodes,
                        direction,
                        callback);
            }
        });
    }
}
