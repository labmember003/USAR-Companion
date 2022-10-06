package com.falcon.usarcompanion.ui.main

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.falcon.usarcompanion.databinding.FragmentWebviewBinding

class WebviewFragment : Fragment() {
    private var _binding: FragmentWebviewBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWebviewBinding.inflate(inflater, container, false)
        val url = "https://www.ipuranklist.com/student"
        binding.resultWebView.settings.javaScriptEnabled = true
        binding.resultWebView.settings.userAgentString = "Android"
//        binding.resultWebView.goBack()

        binding.resultWebView.webViewClient = object: WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                binding.progressBar.visibility = View.GONE
                binding.resultWebView.visibility = View.VISIBLE
            }
        }
        binding.resultWebView.loadUrl(url)

        requireActivity()
            .onBackPressedDispatcher
            .addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    Log.d(TAG, "Fragment back pressed invoked")
                    if (binding.resultWebView.canGoBack()) {
                        binding.resultWebView.goBack()
                    } else {
                        requireActivity().onBackPressed()
                    }
                    if (isEnabled) isEnabled = false
                }
            }
            )
        return binding.root
    }
}