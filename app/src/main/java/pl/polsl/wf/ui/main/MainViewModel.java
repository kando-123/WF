package pl.polsl.wf.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.*;
import java.util.stream.Collectors;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import pl.polsl.wf.common.util.WrapperDataCallback;
import pl.polsl.wf.data.source.TranslationDirection;
import pl.polsl.wf.domain.model.Language;
import pl.polsl.wf.domain.usecase.GetActiveLanguagesUseCase;
import pl.polsl.wf.domain.usecase.GetMainLanguageUseCase;

import static pl.polsl.wf.data.source.TranslationDirection.*;

@HiltViewModel
public class MainViewModel extends ViewModel
{
    private final MutableLiveData<String> mainLanguageName;
    private final MutableLiveData<List<String>> foreignLanguagesNames;
    private final MutableLiveData<TranslationDirection> translationDirection;

    private final GetMainLanguageUseCase getMainLanguageUseCase;

    private final GetActiveLanguagesUseCase getActiveLanguagesUseCase;

    @Inject
    public MainViewModel(GetMainLanguageUseCase getMainLanguageUseCase,
                         GetActiveLanguagesUseCase getActiveLanguagesUseCase)
    {
        mainLanguageName = new MutableLiveData<>();
        foreignLanguagesNames = new MutableLiveData<>();
        translationDirection = new MutableLiveData<>(UNIDIRECTIONAL_TO_FOREIGN);
        this.getMainLanguageUseCase = getMainLanguageUseCase;
        this.getActiveLanguagesUseCase = getActiveLanguagesUseCase;

        Language mainLanguage = getMainLanguageUseCase.execute();
        mainLanguageName.setValue(mainLanguage.name());

        var wrapper = new WrapperDataCallback<List<Language>>();
        getActiveLanguagesUseCase.execute(wrapper);
        try
        {
            List<Language> languages = wrapper.get();
            List<String> names = languages.stream()
                    .filter(language -> !language.code().equals(mainLanguage.code()))
                    .map(Language::name)
                    .collect(Collectors.toList());
            if (names.isEmpty())
            {
                names.add("[none]");
            }
            foreignLanguagesNames.setValue(names);
        }
        catch (Exception e)
        {
            foreignLanguagesNames.setValue(List.of("[none]"));
        }
    }

    public LiveData<String> getMainLanguageName()
    {
        return mainLanguageName;
    }

    public LiveData<List<String>> getForeignLanguagesNames()
    {
        return foreignLanguagesNames;
    }

    public LiveData<TranslationDirection> getTranslationDirection()
    {
        return translationDirection;
    }

    public void toggle(TranslationDirection toggled)
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

    public void refreshActiveLanguages()
    {
        var wrapper = new WrapperDataCallback<List<Language>>();
        getActiveLanguagesUseCase.execute(wrapper);
        Language mainLanguage = getMainLanguageUseCase.execute();
        if (mainLanguage != null)
        {
            try
            {
                List<Language> languages = wrapper.get();
                List<String> names = languages.stream()
                        .filter(language -> !language.code().equals(mainLanguage.code()))
                        .map(Language::name)
                        .collect(Collectors.toList());
                if (names.isEmpty())
                {
                    names.add("[none]");
                }
                foreignLanguagesNames.setValue(names);
            }
            catch (Exception e)
            {
                foreignLanguagesNames.setValue(List.of("[none]"));
            }
        }
    }
}
