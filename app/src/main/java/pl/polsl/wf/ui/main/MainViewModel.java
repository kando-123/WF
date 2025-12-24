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
import pl.polsl.wf.domain.repository.LanguagesRepository;
import pl.polsl.wf.domain.usecase.GetMainLanguageUseCase;
import pl.polsl.wf.ui.main.model.Togglable;

@HiltViewModel
public class MainViewModel extends ViewModel implements Togglable<TranslationDirection>
{
    private final MutableLiveData<String> mainLanguageName;
    private final MutableLiveData<List<String>> foreignLanguagesNames;
    private final MutableLiveData<TranslationDirection> translationDirection;

    private final LanguagesRepository languagesRepo;

    @Inject
    public MainViewModel(GetMainLanguageUseCase getMainLanguageUseCase,
                         LanguagesRepository languagesRepo)
    {
        mainLanguageName = new MutableLiveData<>();
        foreignLanguagesNames = new MutableLiveData<>();
        translationDirection = new MutableLiveData<>();

        this.languagesRepo = languagesRepo;

        Language mainLanguage = getMainLanguageUseCase.execute();
        mainLanguageName.setValue(mainLanguage.name());

        var callback = new WrapperDataCallback<List<Language>>();
        languagesRepo.getAllLanguages(callback);
        try
        {
            List<Language> languages = callback.get();
            List<String> activeLanguageNames = languages.stream()
                    .filter(language ->
                            language.active() && !language.code().equals(mainLanguage.code()))
                    .map(Language::name)
                    .collect(Collectors.toList());
            if (activeLanguageNames.isEmpty())
            {
                activeLanguageNames.add("[none]");
            }
            foreignLanguagesNames.setValue(activeLanguageNames);
        }
        catch (Exception e)
        {
            foreignLanguagesNames.setValue(List.of("[none]"));
        }

        translationDirection.setValue(TranslationDirection.UNIDIRECTIONAL_TO_FOREIGN);
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
        if (toggled == TranslationDirection.UNIDIRECTIONAL_TO_FOREIGN)
        {
            if (current == TranslationDirection.UNIDIRECTIONAL_TO_MAIN)
            {
                updated = TranslationDirection.BIDIRECTIONAL;
            }
            else if (current == TranslationDirection.BIDIRECTIONAL)
            {
                updated = TranslationDirection.UNIDIRECTIONAL_TO_MAIN;
            }
        }
        else if (toggled == TranslationDirection.UNIDIRECTIONAL_TO_MAIN)
        {
            if (current == TranslationDirection.UNIDIRECTIONAL_TO_FOREIGN)
            {
                updated = TranslationDirection.BIDIRECTIONAL;
            }
            else if (current == TranslationDirection.BIDIRECTIONAL)
            {
                updated = TranslationDirection.UNIDIRECTIONAL_TO_FOREIGN;
            }
        }

        if (updated != current)
        {
            translationDirection.setValue(updated);
        }
    }
}
