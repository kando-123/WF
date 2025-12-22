package pl.polsl.wf.ui.activity;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.*;

import javax.inject.Inject;
import dagger.hilt.android.AndroidEntryPoint;

import pl.polsl.wf.R;
import pl.polsl.wf.common.state.UiState;
import pl.polsl.wf.domain.model.Language;
import pl.polsl.wf.data.source.TranslationDirection;
import pl.polsl.wf.ui.languages.LanguagesViewModel;
import pl.polsl.wf.ui.main.MainViewModel;
import pl.polsl.wf.ui.results.ResultsViewModel;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity
{
    private NavController navController;

    @Inject
    public MainActivity(MainViewModel mainViewModel,
                        LanguagesViewModel languagesViewModel,
                        ResultsViewModel resultsViewModel)
    {

    }


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
        NavigationUI.setupWithNavController(bottomNav, navController);

        // languagesViewModel.getLanguagesState().observe(this, this::onLanguagesStateChanged);

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
            updateLanguagesDisplay(success.data);
        }
    }

    public void navigateToLanguages()
    {
        //navController.navigate(R.id.action_mainFragment_to_languagesFragment);
    }

    public void navigateToResults(String query,
                                  List<String> languages,
                                  TranslationDirection direction)
    {
        Bundle args = new Bundle();
        args.putString("query", query);
        args.putStringArrayList("languages", new ArrayList<>(languages));
        args.putSerializable("directions", direction);
        //navController.navigate(R.id.action_mainFragment_to_resultsFragment, args);
    }

    public void navigateToMain()
    {
        navController.popBackStack(R.id.mainFragment, false);
    }

    protected void onDestroy()
    {
        super.onDestroy();
        //languagesViewModel.getLanguagesState().removeObservers(this);
    }

    private void updateLanguagesDisplay(List<Language> languages)
    {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}