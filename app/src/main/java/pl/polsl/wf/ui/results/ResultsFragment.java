package pl.polsl.wf.ui.results;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;
import java.util.stream.Collectors;

import dagger.hilt.android.AndroidEntryPoint;
import pl.polsl.wf.R;
import pl.polsl.wf.common.state.UiState;
import pl.polsl.wf.data.source.TranslationDirection;
import pl.polsl.wf.domain.model.Language;
import pl.polsl.wf.domain.model.Translation;
import pl.polsl.wf.domain.model.TranslationEntry;
import pl.polsl.wf.domain.model.TranslationEntryPhrase;
import pl.polsl.wf.ui.main.MainViewModel;

@AndroidEntryPoint
public class ResultsFragment extends Fragment
{
    private ResultsViewModel resultsViewModel;
    private LinearLayout resultsContainer;

    private TranslationRenderer renderer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_results, container, false);
    }

    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        resultsContainer = view.findViewById(R.id.results_list);
        renderer = new DefaultTranslationRenderer(getContext(), getLayoutInflater(), resultsContainer);

        MainViewModel mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        LiveData<String> translationText = mainViewModel.getTranslationText();
        LiveData<Language> mainLanguage = mainViewModel.getMainLanguage();
        LiveData<List<Language>> foreignLanguages = mainViewModel.getForeignLanguages();
        LiveData<TranslationDirection> translationDirection = mainViewModel.getTranslationDirection();

        resultsViewModel = new ViewModelProvider(requireActivity() /* this? */ ).get(ResultsViewModel.class);

        translationText.observe(getViewLifecycleOwner(), text ->
        {
            resultsViewModel.translate(text,
                    mainLanguage.getValue(),
                    foreignLanguages.getValue(),
                    translationDirection.getValue());
        });

        resultsViewModel.getState().observe(getViewLifecycleOwner(), state ->
        {
            if (state.isSuccess())
            {
                var success = (UiState.Success<List<Translation>>) state;
                renderTranslationsList(success.data);

                Toast.makeText(getContext(),
                        "Translation successful",
                        Toast.LENGTH_LONG)
                        .show();
            }
            else if (state.isError())
            {
                var failure = (UiState.Error<List<Translation>>) state;
                Toast.makeText(getContext(),
                        "Translation failed: " + failure.cause.getMessage(),
                        Toast.LENGTH_LONG)
                        .show();
            }
        });
    }

    private void renderTranslationsList(List<Translation> translations)
    {
        resultsContainer.removeAllViews();
        for (var translation : translations)
        {
           View translationView = renderer.renderTranslation(translation);
           resultsContainer.addView(translationView);
        }
    }
}
