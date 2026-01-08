package pl.polsl.wf.domain.usecase;

import java.util.List;

import javax.inject.Inject;

import pl.polsl.wf.common.util.DataCallback;
import pl.polsl.wf.data.source.TranslationDirection;
import pl.polsl.wf.domain.model.Translation;
import pl.polsl.wf.domain.repository.*;

public class TranslateUseCase
{
    private final TranslationRepository translationRepo;

    @Inject
    public TranslateUseCase(TranslationRepository translationRepo)
    {
        this.translationRepo = translationRepo;
    }

    public void execute(String headword,
                        String mainLanguageCode,
                        List<String> foreignLanguageCodes,
                        TranslationDirection direction,
                        DataCallback<List<Translation>> callback)
    {
        // Validation...

        translationRepo.getTranslations(headword,
                    mainLanguageCode,
                    foreignLanguageCodes,
                    direction,
                    callback);
    }
}
