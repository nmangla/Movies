package com.nmangla.movies.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.nmangla.movies.R;
import com.nmangla.movies.fragments.ErrorMessageFragment;
import com.nmangla.movies.fragments.HomeFragment;
import com.nmangla.movies.helpers.MyExceptionHandler;
import com.nmangla.movies.helpers.Util;

public class HomeActivity extends AppCompatActivity {

    private ErrorMessageFragment.OnItemClickListener mErrorMessageFragmentListener;

    private HomeFragment.OnItemClickListener mHomeFragmentListener;

    private boolean mIsActivityInBackground;
    private Fragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread.setDefaultUncaughtExceptionHandler(new MyExceptionHandler(this));
        setContentView(R.layout.fragment_activity);

        // Used to call HomeFragment to retry service call
        mErrorMessageFragmentListener = new ErrorMessageFragment.OnItemClickListener() {
            @Override
            public void onItemClick() {
                switchFragment(getHomeFragment());
            }
        };

        // Used to open ErrorMessage fragment if service call does not work for some reason
        mHomeFragmentListener = new HomeFragment.OnItemClickListener() {
            @Override
            public void onItemClick() {
                if(Util.isOnline(HomeActivity.this)) {
                    switchFragment(getErrorMessageFragment(getResources().getString(R.string.something_went_wrong)));
                } else {
                    switchFragment(getErrorMessageFragment(getResources().getString(R.string.no_internet_connection)));
                }
            }
        };

        switchFragment(getHomeFragment());
    }

    // Returns fragment to show error for service calls
    private ErrorMessageFragment getErrorMessageFragment(String message) {
        ErrorMessageFragment fragment = ErrorMessageFragment.newInstance(message, "Home");
        fragment.setOnItemClickListener(mErrorMessageFragmentListener);
        return fragment;
    }

    // Return main fragment
    private HomeFragment getHomeFragment() {
        HomeFragment fragment = new HomeFragment();
        fragment.setOnItemClickListener(mHomeFragmentListener);
        return fragment;
    }

    // Switch fragments
    private void switchFragment(Fragment fragment) {

        // If activity in background, fragment is held until activity is back on foreground
        if (mIsActivityInBackground) {
            mFragment = fragment;
        } else {
            FragmentManager fm = getSupportFragmentManager();
            Fragment currentFragment = fm.findFragmentById(R.id.fragment_container);

            if (currentFragment != null) {
                fm.beginTransaction().remove(currentFragment).commit();
            }
            fm.beginTransaction().replace(R.id.fragment_container, fragment).commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mIsActivityInBackground = false;
        if (mFragment != null) {
            switchFragment(mFragment);
            mFragment = null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mIsActivityInBackground = true;
    }
}
