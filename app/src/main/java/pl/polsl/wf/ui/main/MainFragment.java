package pl.polsl.wf.ui.main;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
import pl.polsl.wf.data.source.TranslationDirection;
import pl.polsl.wf.ui.main.model.ArrowButtonModel;

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
        var modelDown = new ArrowButtonModel<>(bnArrowDown,
                TranslationDirection.UNIDIRECTIONAL_TO_FOREIGN,
                viewModel,
                TranslationDirection.UNIDIRECTIONAL_TO_MAIN.isIncludedIn(current));
        modelDown.setOnActivateCallback(b ->
                {
                    b.setIconResource(R.drawable.ic_circled_arrow_down_filled);
                    // Toast.makeText(getContext(), "Translation from main to foreign enabled", Toast.LENGTH_SHORT).show();
                })
                .setOnDeactivateCallback(b ->
                {
                    b.setIconResource(R.drawable.ic_circled_arrow_down_empty);
                    // Toast.makeText(getContext(), "Translation from main to foreign disabled", Toast.LENGTH_SHORT).show();
                })
                .call();
        bnArrowDown.setOnClickListener(modelDown);
        translationDirection.observe(getViewLifecycleOwner(), modelDown);

        MaterialButton bnArrowUp = view.findViewById(R.id.ibn_arrow_up);
        var modelUp = new ArrowButtonModel<>(bnArrowUp,
                TranslationDirection.UNIDIRECTIONAL_TO_MAIN,
                viewModel,
                TranslationDirection.UNIDIRECTIONAL_TO_MAIN.isIncludedIn(current));
        modelUp.setOnActivateCallback(b ->
                {
                    b.setIconResource(R.drawable.ic_circled_arrow_up_filled);
                    // Toast.makeText(getContext(), "Translation from foreign to main enabled", Toast.LENGTH_SHORT).show();
                })
                .setOnDeactivateCallback(b ->
                {
                    b.setIconResource(R.drawable.ic_circled_arrow_up_empty);
                    // Toast.makeText(getContext(), "Translation from foreign to main disabled", Toast.LENGTH_SHORT).show();
                })
                .call();
        bnArrowUp.setOnClickListener(modelUp);
        translationDirection.observe(getViewLifecycleOwner(), modelUp);
    }
}
