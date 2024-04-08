package com.world.horizon.view.articles

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.world.horizon.databinding.ActivityWebArticlesBinding
import com.world.horizon.model.Article
import com.world.horizon.shared.utils.getParcelableIntent

class WebArticlesView : AppCompatActivity() {
    companion object{
        private const val ARTICLE = "article"
        fun newIntent(context: Context, article: Article) = Intent(context, WebArticlesView::class.java).apply {
            putExtra(ARTICLE, article)
        }
    }

    private lateinit var binding: ActivityWebArticlesBinding
    private lateinit var myWebView: WebView
    private var article = Article()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebArticlesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initData()
        initView()
    }

    private fun initData(){
        article = intent.getParcelableIntent(ARTICLE) ?: Article()
    }


    private fun initView(){
        binding.modifyToolbar.setTitle(article.name)
        myWebView = binding.webView
        setUpWebView(article.url)
        binding.btnClose.setOnClickListener { finish() }
    }

    private fun setUpWebView(url: String){
        myWebView.apply {
            settings.javaScriptEnabled = true
            settings.databaseEnabled = true
            settings.domStorageEnabled = true
            settings.allowContentAccess = true
            settings.mediaPlaybackRequiresUserGesture = true
            settings.cacheMode = WebSettings.LOAD_NO_CACHE
            settings.loadWithOverviewMode = true
            settings.userAgentString = "Mozilla/5.0 (Linux; Android 10) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/110.0.5481.65 Mobile Safari/537.36"
            loadUrl(url)
        }
        myWebView.webViewClient = object : WebViewClient(){
        }
    }

    override fun onBackPressed() {
        if (myWebView.canGoBack()){
            myWebView.goBack()
        }else{
            super.onBackPressed()
        }
    }
}