package pl.polsl.wf.domain.usecase;

import static pl.polsl.wf.common.util.Constants.MIN_HINT_INPUT_LENGTH;

import androidx.annotation.NonNull;

import java.util.List;

import pl.polsl.wf.common.util.DataCallback;
import pl.polsl.wf.data.source.TranslationDirection;
import pl.polsl.wf.domain.repository.TranslationRepository;

public class HintUseCase
{
    private final TranslationRepository translationRepo;

    public HintUseCase(TranslationRepository translationRepo)
    {
        this.translationRepo = translationRepo;
    }

    public void execute(String input,
                        String mainLanguageCode,
                        List<String> foreignLanguageCodes,
                        TranslationDirection direction,
                        int maxCount,
                        DataCallback<List<String>> callback)
    {
        if (input != null && input.length() >= MIN_HINT_INPUT_LENGTH)
        {
            translationRepo.getHints(input,
                    mainLanguageCode,
                    foreignLanguageCodes,
                    direction,
                    maxCount,
                    callback);
        }
    }
}
