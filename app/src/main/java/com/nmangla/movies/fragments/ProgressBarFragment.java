package com.nmangla.movies.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.nmangla.movies.R;

// Generic fragment to show progress when service calls are being made
public class ProgressBarFragment extends Fragment {

    // Toolbar title for this fragment
    private static final String ARG_TITLE = "title";

    public static ProgressBarFragment newInstance(String title) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_TITLE, title);
        ProgressBarFragment fragment = new ProgressBarFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_progress_bar, container, false);
        String title = (String) getArguments().getSerializable(ARG_TITLE);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.tool_bar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(title);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);

        progressBar.setVisibility(View.VISIBLE);

        return view;
    }
}
