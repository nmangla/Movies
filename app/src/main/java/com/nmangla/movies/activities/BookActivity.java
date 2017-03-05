package com.nmangla.movies.activities;

import android.support.v4.app.Fragment;
import android.view.MenuItem;

import com.nmangla.movies.fragments.BookFragment;

public class BookActivity extends SingleFragmentActivity {

    // Loading single fragment
    protected Fragment createFragment () {
        return new BookFragment();
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
}
