package com.example.prac11;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.prac11.databinding.FragmentMainBinding;
import com.example.prac11.databinding.FragmentWebBinding;


public class WebFragment extends Fragment {
    private WebView webView;
    FragmentWebBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentWebBinding.inflate(inflater, container, false);

        webView = (WebView) binding.webview;
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://gist.github.com/MattIPv4/045239bc27b16b2bcf7a3a9a4648c08a");

        return binding.getRoot();
    }
}