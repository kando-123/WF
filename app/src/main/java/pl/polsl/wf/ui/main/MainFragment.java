package pl.polsl.wf.ui.main;

import static pl.polsl.wf.data.source.TranslationDirection.UNIDIRECTIONAL_TO_FOREIGN;
import static pl.polsl.wf.data.source.TranslationDirection.UNIDIRECTIONAL_TO_MAIN;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import pl.polsl.wf.R;
import pl.polsl.wf.common.util.ActivableButtonObserverModel;
import pl.polsl.wf.data.source.TranslationDirection;

@AndroidEntryPoint
public class MainFragment extends Fragment
{
    private MainViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        TextView tvMainLanguage = view.findViewById(R.id.tv_main_language);
        LiveData<String> mainLanguageName = viewModel.getMainLanguageName();
        mainLanguageName.observe(getViewLifecycleOwner(), tvMainLanguage::setText);

        TextView tvForeignLanguages = view.findViewById(R.id.tv_foreign_languages);
        LiveData<List<String>> foreignLanguagesNames = viewModel.getForeignLanguagesNames();
        foreignLanguagesNames.observe(getViewLifecycleOwner(), new Observer<List<String>>()
        {
            @Override
            public void onChanged(List<String> strings)
            {
                tvForeignLanguages.setText(String.join(", ", strings));
            }
        });

        LiveData<TranslationDirection> translationDirection = viewModel.getTranslationDirection();
        final TranslationDirection current = translationDirection.getValue();

        MaterialButton bnArrowDown = view.findViewById(R.id.ibn_arrow_down);
        var modelDown = new ActivableButtonObserverModel<MaterialButton, TranslationDirection>
                (bnArrowDown, UNIDIRECTIONAL_TO_FOREIGN.isIncludedIn(current))
        {
            @Override
            public void onClick(View v)
            {
                viewModel.toggle(UNIDIRECTIONAL_TO_FOREIGN);
            }

            @Override
            public void onChanged(TranslationDirection updated)
            {
                toggle(UNIDIRECTIONAL_TO_FOREIGN.isIncludedIn(updated));
            }
        };
        modelDown
                .setOnActivateCallback(b -> b.setIconResource(R.drawable.ic_circled_arrow_down_filled))
                .setOnDeactivateCallback(b -> b.setIconResource(R.drawable.ic_circled_arrow_down_empty))
                .call();
        bnArrowDown.setOnClickListener(modelDown);
        translationDirection.observe(getViewLifecycleOwner(), modelDown);

        MaterialButton bnArrowUp = view.findViewById(R.id.ibn_arrow_up);
        var modelUp = new ActivableButtonObserverModel<MaterialButton, TranslationDirection>
                (bnArrowUp, UNIDIRECTIONAL_TO_MAIN.isIncludedIn(current))
        {
            @Override
            public void onClick(View v)
            {
                viewModel.toggle(UNIDIRECTIONAL_TO_MAIN);
            }

            @Override
            public void onChanged(TranslationDirection updated)
            {
                toggle(UNIDIRECTIONAL_TO_MAIN.isIncludedIn(updated));
            }
        };
        modelUp
                .setOnActivateCallback(b -> b.setIconResource(R.drawable.ic_circled_arrow_up_filled))
                .setOnDeactivateCallback(b -> b.setIconResource(R.drawable.ic_circled_arrow_up_empty))
                .call();
        bnArrowUp.setOnClickListener(modelUp);
        translationDirection.observe(getViewLifecycleOwner(), modelUp);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        viewModel.refreshActiveLanguages();
    }
}
