package pl.polsl.wf.ui.main;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.*;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import pl.polsl.wf.data.source.TranslationDirection;
import pl.polsl.wf.domain.model.Language;
import pl.polsl.wf.domain.model.Translation;
import pl.polsl.wf.domain.usecase.GetActiveLanguagesUseCase;
import pl.polsl.wf.domain.usecase.GetMainLanguageUseCase;

import static pl.polsl.wf.data.source.TranslationDirection.*;

@HiltViewModel
public class MainViewModel extends ViewModel
{
    private final MutableLiveData<Language> mainLanguage;
    private final MutableLiveData<List<Language>> foreignLanguages;
    private final MutableLiveData<TranslationDirection> translationDirection;
    private final MutableLiveData<String> translationText;

    private final GetMainLanguageUseCase getMainLanguageUseCase;
    private final GetActiveLanguagesUseCase getActiveLanguagesUseCase;

    @Inject
    public MainViewModel(GetMainLanguageUseCase getMainLanguageUseCase,
                         GetActiveLanguagesUseCase getActiveLanguagesUseCase)
    {
        mainLanguage = new MutableLiveData<>();
        foreignLanguages = new MutableLiveData<>();
        translationDirection = new MutableLiveData<>(UNIDIRECTIONAL_TO_FOREIGN);
        translationText = new MutableLiveData<>();

        this.getMainLanguageUseCase = getMainLanguageUseCase;
        this.getActiveLanguagesUseCase = getActiveLanguagesUseCase;

        refreshLanguages();
    }

    @NonNull
    public LiveData<Language> getMainLanguage()
    {
        return mainLanguage;
    }

    @NonNull
    public LiveData<List<Language>> getForeignLanguages()
    {
        return foreignLanguages;
    }

    @NonNull
    public LiveData<TranslationDirection> getTranslationDirection()
    {
        return translationDirection;
    }

    @NonNull
    public LiveData<String> getTranslationText()
    {
        return translationText;
    }

    void toggle(TranslationDirection toggled)
    {
        TranslationDirection current = translationDirection.getValue();
        TranslationDirection updated = current;
        if (toggled == UNIDIRECTIONAL_TO_FOREIGN)
        {
            if (current == UNIDIRECTIONAL_TO_MAIN)
            {
                updated = BIDIRECTIONAL;
            }
            else if (current == BIDIRECTIONAL)
            {
                updated = UNIDIRECTIONAL_TO_MAIN;
            }
        }
        else if (toggled == UNIDIRECTIONAL_TO_MAIN)
        {
            if (current == UNIDIRECTIONAL_TO_FOREIGN)
            {
                updated = BIDIRECTIONAL;
            }
            else if (current == BIDIRECTIONAL)
            {
                updated = UNIDIRECTIONAL_TO_FOREIGN;
            }
        }

        if (updated != current)
        {
            translationDirection.setValue(updated);
        }
    }

    void refreshLanguages()
    {
        Language main = getMainLanguageUseCase.execute();
        mainLanguage.setValue(main);

        List<Language> activeLanguages = getActiveLanguagesUseCase.execute();
        foreignLanguages.setValue(activeLanguages != null
                ? activeLanguages
                : Collections.emptyList());
    }

    void triggerTranslation(String text)
    {
        translationText.setValue(text);
    }
}
