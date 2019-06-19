package com.simbirsoft.insurance.platform.testtexteditor

import android.annotation.SuppressLint
import android.graphics.Rect
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val untouchable = View.OnTouchListener { _, _ -> true }
    private val touchable = View.OnTouchListener { _, _ -> false }

    private var isCheckVisibility = true

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) = prepareWebView()
        }
        webView.settings.javaScriptEnabled = true
        webView.addJavascriptInterface(WebAppInterface(this), WebAppInterface::class.java.simpleName)

        val data = assets.open("editor2.html").bufferedReader().use { it.readText() }
        webView.loadDataWithBaseURL("file:///android_asset/", data, "text/html", "UTF-8", null)

        nestedScroll.viewTreeObserver.addOnScrollChangedListener { checkVisibilityButton() }
    }

    private fun checkVisibilityButton() {
        if (isCheckVisibility.not()) return

        val rect = Rect()
        nestedScroll.getDrawingRect(rect)

        val top = editButton.y
        val bottom = top + editButton.height

        editButtonClone.visibility = if (rect.top < top && rect.bottom > bottom) GONE else VISIBLE
    }

    override fun onBackPressed() {
        isCheckVisibility = true
        prepareWebView()
        webView.clearFocus()
    }

    fun onEditClick(view: View) {
        isCheckVisibility = false
        webView.setOnTouchListener(touchable)
        webView.performClick()

        editButton.visibility = GONE
        editButtonClone.visibility = GONE
    }

    private fun prepareWebView() {
        webViewProgress.visibility = GONE
        webView.setOnTouchListener(untouchable)
        editButton.visibility = VISIBLE
        checkVisibilityButton()
    }
}
