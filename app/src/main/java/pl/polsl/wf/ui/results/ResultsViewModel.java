package pl.polsl.wf.ui.results;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import pl.polsl.wf.common.state.UiState;
import pl.polsl.wf.common.util.DataCallback;
import pl.polsl.wf.data.source.TranslationDirection;
import pl.polsl.wf.domain.model.Language;
import pl.polsl.wf.domain.model.Translation;
import pl.polsl.wf.domain.usecase.TranslateUseCase;

@HiltViewModel
public class ResultsViewModel extends ViewModel
{
    private final MutableLiveData<UiState<List<Translation>>> state = new MutableLiveData<>();

    private final TranslateUseCase translateUseCase;

    @Inject
    public ResultsViewModel(TranslateUseCase translateUseCase)
    {
        this.translateUseCase = translateUseCase;
    }

    LiveData<UiState<List<Translation>>> getState()
    {
        return state;
    }

    void translate(String text,
                   Language mainLanguage,
                   List<Language> foreignLanguages,
                   TranslationDirection translationDirection)
    {
        state.setValue(new UiState.Loading<>());

        String mainLanguageCode = mainLanguage.code();
        List<String> foreignLanguageCodes = foreignLanguages.stream()
                .map(Language::code)
                .toList();

        translateUseCase.execute(text, mainLanguageCode, foreignLanguageCodes, translationDirection,
                new DataCallback<List<Translation>>()
                {
                    @Override
                    public void onSuccess(List<Translation> data)
                    {
                        state.postValue(new UiState.Success<>(data));
                        Log.d("Translation", "Success: '" + data.toString() + "' @ ResultsViewModel");
                    }

                    @Override
                    public void onError(Exception exception)
                    {
                        state.postValue(new UiState.Error<>(exception));
                        Log.d("Translation", "Failure: '" + exception.toString() + "', "
                                + "message: '" + exception.getMessage() + "' @ ResultsViewModel");
                    }
                });
    }
}
