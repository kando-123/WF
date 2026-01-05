package pl.polsl.wf.ui.languages;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.button.MaterialButton;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import pl.polsl.wf.R;
import pl.polsl.wf.common.util.ActivableButtonModel;
import pl.polsl.wf.common.util.DataCallback;
import pl.polsl.wf.domain.model.Language;

@AndroidEntryPoint
public class LanguagesFragment extends Fragment
{
    private LanguagesViewModel viewModel;
    private LinearLayout languageContainer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_languages, container, false);
    }

    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(LanguagesViewModel.class);
        languageContainer = view.findViewById(R.id.languages_list);

        /* The list of languages is observed. On change, the UI list is re-rendered. */
        viewModel.getLanguages().observe(getViewLifecycleOwner(), this::renderLanguages);
    }

    private void renderLanguages(List<Language> languages)
    {
        languageContainer.removeAllViews();
        for (var language : languages)
        {
            View itemView = getLayoutInflater().inflate(R.layout.item_language,
                    languageContainer, false);
            bindLanguage(itemView, language);
            languageContainer.addView(itemView);
        }
    }

    private void bindLanguage(View itemView, Language language)
    {
        TextView tvLanguageName = itemView.findViewById(R.id.tv_language_name);

        MaterialButton mbnActive = itemView.findViewById(R.id.mbn_active);
        var activeModel = new ActivableButtonModel<MaterialButton>(mbnActive, language.active())
        {
            @Override
            public void onClick(View v)
            {
                viewModel.toggleLanguage(language.code());
            }
        };
        activeModel
                .setOnActivateCallback(b ->
                {
                    b.setIconResource(R.drawable.ic_circle_play);
                    b.setIconTintResource(R.color.green);
                })
                .setOnDeactivateCallback(b ->
                {
                    b.setIconResource(R.drawable.ic_circle_filled);
                    b.setIconTintResource(R.color.gray);
                })
                .call();
        mbnActive.setOnClickListener(activeModel);

        MaterialButton mbnDownload = itemView.findViewById(R.id.mbn_download);
        var downloadModel = new ActivableButtonModel<MaterialButton>(mbnDownload, language.downloaded())
        {
            @Override
            public void onClick(View v)
            {
                if (isActive())
                {
                    return;
                }
                viewModel.downloadLanguage(language.code(), new DataCallback<Language>()
                {
                    @Override
                    public void onSuccess(Language data)
                    {
                        Toast.makeText(getContext(), getDownloadSuccessMessage(data), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Exception exception)
                    {
                        Toast.makeText(getContext(), exception.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };
        downloadModel
                .setOnActivateCallback(b ->
                {
                    b.setIconResource(R.drawable.ic_circle_check);
                    b.setIconTintResource(R.color.blue);
                })
                .setOnDeactivateCallback(b ->
                {
                    b.setIconResource(R.drawable.ic_download);
                    b.setIconTintResource(R.color.gray);
                })
                .call();
        mbnDownload.setOnClickListener(downloadModel);

        tvLanguageName.setText(language.name());
    }

    private String getDownloadSuccessMessage(Language language)
    {
        return new StringBuilder("Successfully downloaded the ")
                .append(language.name())
                .append(" language.\n")
                .toString();
    }
}
