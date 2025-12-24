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

import dagger.hilt.android.AndroidEntryPoint;

import pl.polsl.wf.R;
import pl.polsl.wf.domain.model.Language;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity
{
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        if (navHostFragment == null)
        {
            throw new IllegalStateException("NavHostFragment not found");
        }
        navController = navHostFragment.getNavController();

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        NavigationUI.setupWithNavController(bottomNav, navController);

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

    public void navigateToLanguages()
    {
        navController.navigate(R.id.action_mainFragment_to_languagesFragment);
    }

    public void navigateToResults(String query)
    {
        Bundle args = new Bundle();
        args.putString("query", query);
        navController.navigate(R.id.action_mainFragment_to_resultsFragment, args);
    }

    public void navigateToMain()
    {
        navController.popBackStack(R.id.mainFragment, false);
    }

    protected void onDestroy()
    {
        super.onDestroy();
    }

    private void updateLanguagesDisplay(List<Language> languages)
    {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}