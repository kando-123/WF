package pl.polsl.wf.ui.languages;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import pl.polsl.wf.common.state.UiState;
import pl.polsl.wf.domain.model.Language;
import pl.polsl.wf.domain.usecase.LanguagesUseCase;

public class LanguagesViewModel extends ViewModel
{
    private final LanguagesUseCase languagesUseCase;
    private final MutableLiveData<UiState<List<Language>>> languagesState;

    @Inject
    public LanguagesViewModel(LanguagesUseCase languagesUseCase)
    {
        this.languagesUseCase = languagesUseCase;
        this.languagesState = new MutableLiveData<>();
        loadLanguages();
    }

    public LiveData<UiState<List<Language>>> getLanguagesState()
    {
        return languagesState;
    }

    public void loadLanguages()
    {
        languagesState.setValue(new UiState.Loading<>());
        try
        {
            List<Language> languages = languagesUseCase.getLanguages();
            languagesState.setValue(new UiState.Success<>(languages));
        }
        catch (Exception exc)
        {
            languagesState.setValue(new UiState.Error<>(exc));
        }
    }
}
