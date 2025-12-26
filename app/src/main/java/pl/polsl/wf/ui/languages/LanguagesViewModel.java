package pl.polsl.wf.ui.languages;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import pl.polsl.wf.common.state.UiState;
import pl.polsl.wf.common.util.DataCallback;
import pl.polsl.wf.common.util.WrapperDataCallback;
import pl.polsl.wf.domain.model.Language;
import pl.polsl.wf.domain.repository.LanguagesRepository;
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
        getAllLanguagesUseCase.execute(new DataCallback<>()
        {
            @Override
            public void onSuccess(List<Language> data)
            {
                languages.setValue(data);
            }

            @Override
            public void onError(Exception exception)
            {
                /* Nothing, leave the list empty */
            }
        });
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
            var listCallback = new WrapperDataCallback<List<Language>>();
            getAllLanguagesUseCase.execute(listCallback);
            try
            {
                languages.setValue(listCallback.get());
            }
            catch (Exception ignore)
            {
                /* Nothing (?) */
            }
        }
    }

    public void downloadLanguage(String languageCode, DataCallback<Language> callback)
    {
        var downloadCallback = new WrapperDataCallback<Language>();
        downloadLanguageUseCase.execute(languageCode, downloadCallback);
        try
        {
            Language language = downloadCallback.get();

            var listCallback = new WrapperDataCallback<List<Language>>();
            getAllLanguagesUseCase.execute(listCallback);

            languages.setValue(listCallback.get());
            callback.onSuccess(downloadCallback.get());
        }
        catch (Exception exc)
        {
            callback.onError(exc);
        }
    }
}