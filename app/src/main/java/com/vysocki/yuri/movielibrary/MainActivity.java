package com.vysocki.yuri.movielibrary;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements MovieListFragment.OnMovieSelectedListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private int fragmentContainerId;
    private ArrayList<Integer> menuStack;
    private final String MENU_STACK_TAG = "MENU_STACK_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        fragmentContainerId = R.id.fragment_container;
        menuStack = new ArrayList<>();

        if (savedInstanceState != null) {
            menuStack = savedInstanceState.getIntegerArrayList(MENU_STACK_TAG);
        }

        navigationView.setNavigationItemSelectedListener(drawerClickListener);

        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
            replaceFragment(MovieListFragment.newInstance(R.id.nav_popular), R.id.nav_popular);
            navigationView.setCheckedItem(R.id.nav_popular);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putIntegerArrayList(MENU_STACK_TAG, menuStack);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() < 2) {
            finish();
        } else {
            super.onBackPressed();
            menuStack.remove(menuStack.size()-1);
            navigationView.setCheckedItem(menuStack.get(menuStack.size()-1));
        }
    }

    public void onMovieSelected(Integer movieId) {
        MovieDetailsFragment detailsFragment =
                MovieDetailsFragment.newInstance(movieId);
        replaceFragment(detailsFragment, null);
    }

    private NavigationView.OnNavigationItemSelectedListener drawerClickListener =
            new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    if (menuItem.isChecked()) {
                        drawerLayout.closeDrawers();
                        return true;
                    } else {
                        menuItem.setChecked(true);
                        drawerLayout.closeDrawers();

                        Fragment selectedFragment = defineSelectedMenuItem(menuItem.getItemId());
                        replaceFragment(selectedFragment, menuItem.getItemId());

                        return true;
                    }

                }
            };


    private Fragment defineSelectedMenuItem(int menuItemId) {
        Fragment selectedFragment = null;
        switch (menuItemId) {
            case R.id.nav_popular:
                selectedFragment = MovieListFragment.newInstance(R.id.nav_popular);
                break;
            case R.id.nav_upcoming:
                selectedFragment = MovieListFragment.newInstance(R.id.nav_upcoming);
                break;
            case R.id.nav_top_rated:
                selectedFragment = MovieListFragment.newInstance(R.id.nav_top_rated);
                break;
            case R.id.nav_favorites:
                selectedFragment = MovieFavoritesFragment.newInstance();
                break;
        }
        return selectedFragment;
    }

    private void replaceFragment(Fragment fragment, @Nullable Integer menuItemId) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(fragmentContainerId, fragment, fragment.getClass().toString());
            fragmentTransaction.addToBackStack(null);
            if (menuItemId != null) {
                //check if menu item is specified
                menuStack.add(menuItemId);
            } else {
                // if not, use the last one
                Integer lastMenuStackItem = menuStack.get(menuStack.size()-1);
                menuStack.add(lastMenuStackItem);
            }
        fragmentTransaction.commit();
    }

}
