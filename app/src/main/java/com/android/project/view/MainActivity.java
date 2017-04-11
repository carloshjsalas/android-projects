package com.android.project.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.android.project.R;

public class MainActivity extends AppCompatActivity {

    private ListFragment mFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupUI();
        addListFragment();
    }

    private void setupUI() {
        getSupportActionBar().setTitle(getString(R.string.app_name));
    }

    private void addListFragment() {
        mFragment = ListFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.content_frame, mFragment)
                .commit();
    }
}
