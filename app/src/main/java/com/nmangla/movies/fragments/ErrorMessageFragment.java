package com.nmangla.movies.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nmangla.movies.R;

// Generic fragment to show error messages during service aclls
public class ErrorMessageFragment extends Fragment {

    // Message to be displayed on screen
    private static final String ARG_ERROR_MESSAGE = "errorMessage";

    // Toolbar title for this fragment
    private static final String ARG_TITLE = "title";

    private static OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick();
    }

    public static ErrorMessageFragment newInstance(String errorMessage, String title) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_ERROR_MESSAGE, errorMessage);
        args.putSerializable(ARG_TITLE, title);
        ErrorMessageFragment fragment = new ErrorMessageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_error_message, container, false);
        String errorMessage = (String) getArguments().getSerializable(ARG_ERROR_MESSAGE);
        String title = (String) getArguments().getSerializable(ARG_TITLE);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.tool_bar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(title);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        TextView errorMessageMessageTextView = (TextView) view.findViewById(R.id.error_message);
        RelativeLayout errorMessageRefreshButton = (RelativeLayout) view.findViewById(R.id.refresh_layout);
        errorMessageMessageTextView.setText(errorMessage);

        // User can click on refresh to make service all again
        errorMessageRefreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick();
            }
        });

        return view;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }
}
