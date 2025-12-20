package pl.polsl.wf.ui;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import java.util.*;

import javax.inject.Inject;
import dagger.hilt.android.AndroidEntryPoint;

import pl.polsl.wf.R;
import pl.polsl.wf.common.state.UiState;
import pl.polsl.wf.domain.model.Language;
import pl.polsl.wf.domain.model.TranslationDirection;
import pl.polsl.wf.ui.languages.LanguagesViewModel;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity
{
    @Inject
    LanguagesViewModel languagesViewModel;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        assert navHostFragment != null;
        navController = navHostFragment.getNavController();

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        NavigationUI.setUpWithNavController(bottomNav, navController);

        languagesViewModel.getLanguagesState().observe(this, this::onLanguagesStateChanged);

        OnBackPressedDispatcher dispatcher = getOnBackPressedDispatcher();
        dispatcher.addCallback(this, new OnBackPressedCallback(true)
        {
            @Override
            public void handleOnBackPressed()
            {
                if (!navController.popBackStack())
                {
                    finish();
                }
            }
        });
    }

    private void onLanguagesStateChanged(UiState<List<Language>> state)
    {
        if (state instanceof UiState.Success<List<Language>> success)
        {
            updateLanguagesDisplay((List<Language>) success.data);
        }
    }

    public void navigateToLanguages()
    {
        navController.navigate(R.id.action_mainFragment_to_languagesFragment);
    }

    public void navigateToResults(String query,
                                  List<String> languages,
                                  TranslationDirection direction)
    {
        Bundle args = new Bundle();
        args.putString("query", query);
        args.putStringArrayList("languages", new ArrayList<>(languages));
        args.putSerializable("directions", direction);
        navController.navigate(R.id.action_mainFragment_to_resultsFragment, args);
    }

    public void navigateToMain()
    {
        navController.popBackStack(R.id.mainFragment, false);
    }

    protected void onDestroy()
    {
        super.onDestroy();
        languagesViewModel.getLanguagesState().removeObservers(this);
    }

    private void updateLanguagesDisplay(List<Language> languages)
    {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}