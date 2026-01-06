package pl.polsl.wf.ui.main;

import static pl.polsl.wf.data.source.TranslationDirection.UNIDIRECTIONAL_TO_FOREIGN;
import static pl.polsl.wf.data.source.TranslationDirection.UNIDIRECTIONAL_TO_MAIN;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;

import java.util.List;
import java.util.stream.Collectors;

import dagger.hilt.android.AndroidEntryPoint;
import pl.polsl.wf.R;
import pl.polsl.wf.common.util.ActivableButtonObserverModel;
import pl.polsl.wf.data.source.TranslationDirection;
import pl.polsl.wf.domain.model.Language;

@AndroidEntryPoint
public class MainFragment extends Fragment
{
    private MainViewModel mainViewModel;

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

        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        TextView tvMainLanguage = view.findViewById(R.id.tv_main_language);
        LiveData<Language> mainLanguage = mainViewModel.getMainLanguage();
        mainLanguage.observe(getViewLifecycleOwner(),
                language -> tvMainLanguage.setText(language.name()));

        TextView tvForeignLanguages = view.findViewById(R.id.tv_foreign_languages);
        LiveData<List<Language>> foreignLanguagesNames = mainViewModel.getForeignLanguages();
        foreignLanguagesNames.observe(getViewLifecycleOwner(), new Observer<List<Language>>()
        {
            @Override
            public void onChanged(List<Language> languages)
            {
                var strings = languages.stream()
                        .map(Language::name)
                        .collect(Collectors.toList());
                tvForeignLanguages.setText(String.join(", ", strings));
            }
        });

        LiveData<TranslationDirection> translationDirection = mainViewModel.getTranslationDirection();
        final TranslationDirection current = translationDirection.getValue();

        MaterialButton bnArrowDown = view.findViewById(R.id.ibn_arrow_down);
        var modelDown = new ActivableButtonObserverModel<MaterialButton, TranslationDirection>
                (bnArrowDown, UNIDIRECTIONAL_TO_FOREIGN.isIncludedIn(current))
        {
            @Override
            public void onClick(View v)
            {
                mainViewModel.toggle(UNIDIRECTIONAL_TO_FOREIGN);
            }

            @Override
            public void onChanged(TranslationDirection updated)
            {
                toggle(UNIDIRECTIONAL_TO_FOREIGN.isIncludedIn(updated));
            }
        };
        modelDown
                .setOnActivateCallback(b ->
                {
                    b.setIconResource(R.drawable.ic_circled_arrow_down_filled);
                    b.setIconTintResource(R.color.green);
                })
                .setOnDeactivateCallback(b ->
                {
                    b.setIconResource(R.drawable.ic_circled_arrow_down_empty);
                    b.setIconTintResource(R.color.gray);
                })
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
                mainViewModel.toggle(UNIDIRECTIONAL_TO_MAIN);
            }

            @Override
            public void onChanged(TranslationDirection updated)
            {
                toggle(UNIDIRECTIONAL_TO_MAIN.isIncludedIn(updated));
            }
        };
        modelUp
                .setOnActivateCallback(b ->
                {
                    b.setIconResource(R.drawable.ic_circled_arrow_up_filled);
                    b.setIconTintResource(R.color.green);
                })
                .setOnDeactivateCallback(b ->
                {
                    b.setIconResource(R.drawable.ic_circled_arrow_up_empty);
                    b.setIconTintResource(R.color.gray);
                })
                .call();
        bnArrowUp.setOnClickListener(modelUp);
        translationDirection.observe(getViewLifecycleOwner(), modelUp);

        AutoCompleteTextView actvInput = view.findViewById(R.id.actv_input);
        Button confirm = view.findViewById(R.id.bn_confirm);
        confirm.setOnClickListener(v ->
        {
            String text = actvInput.getText().toString().trim();
            if (!text.isEmpty())
            {
                mainViewModel.triggerTranslation(text);
            }
        });
    }

    @Override
    public void onResume()
    {
        super.onResume();
        mainViewModel.refreshLanguages();
    }
}
