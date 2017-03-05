package com.nmangla.movies.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.nmangla.movies.R;

public class BookFragment extends Fragment {

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book, container, false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.tool_bar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Book Movie");
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);

        // Initialize webview
        final WebView webView = (WebView) view.findViewById(R.id.webview);

        // Enable javascript
        webView.getSettings().setJavaScriptEnabled(true);

        // Only display webview once it's loaded
        webView.setVisibility(View.INVISIBLE);
        webView.loadUrl("http://www.cathaycineplexes.com.sg/");

        // Wait for webview to be loaded
        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int progress) {
                if (progress == 100) {

                    // Hide progress bar when webview is loaded
                    progressBar.setVisibility(View.GONE);
                    webView.setVisibility(View.VISIBLE);
                }
            }
        });

        return view;
    }
}
