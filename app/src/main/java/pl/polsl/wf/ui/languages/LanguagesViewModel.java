package pl.polsl.wf.ui.languages;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import pl.polsl.wf.common.util.DataCallback;
import pl.polsl.wf.common.util.WrapperDataCallback;
import pl.polsl.wf.domain.model.Language;
import pl.polsl.wf.domain.usecase.DownloadLanguageUseCase;
import pl.polsl.wf.domain.usecase.GetAllLanguagesUseCase;
import pl.polsl.wf.domain.usecase.ToggleLanguageUseCase;

@HiltViewModel
public class LanguagesViewModel extends ViewModel
{
    private final MutableLiveData<List<Language>> languages;
    private final GetAllLanguagesUseCase getAllLanguagesUseCase;
    private final ToggleLanguageUseCase toggleLanguageUseCase;
    private final DownloadLanguageUseCase downloadLanguageUseCase;

    @Inject
    public LanguagesViewModel(GetAllLanguagesUseCase getAllLanguagesUseCase,
                              ToggleLanguageUseCase toggleLanguageUseCase,
                              DownloadLanguageUseCase downloadLanguageUseCase)
    {
        this.getAllLanguagesUseCase = getAllLanguagesUseCase;
        this.toggleLanguageUseCase = toggleLanguageUseCase;
        this.downloadLanguageUseCase = downloadLanguageUseCase;

        languages = new MutableLiveData<>(Collections.emptyList());
        languages.setValue(getAllLanguagesUseCase.execute());
    }

    public MutableLiveData<List<Language>> getLanguages()
    {
        return languages;
    }

    public void toggleLanguage(String languageCode)
    {
        var toggleCallback = new WrapperDataCallback<Language>();
        toggleLanguageUseCase.execute(languageCode, toggleCallback);

        if (toggleCallback.isSuccess())
        {
            languages.setValue(getAllLanguagesUseCase.execute());
        }
    }

    public void downloadLanguage(String languageCode, DataCallback<Language> callback)
    {
        downloadLanguageUseCase.execute(languageCode, callback);
        languages.setValue(getAllLanguagesUseCase.execute());
    }
}
