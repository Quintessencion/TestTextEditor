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

    private val data =
        "<p>I am referring <strong>Abdulai Ali </strong>for evaluation and consideration for a weight management surgical procedure. He currently weighs [# of lbs] pounds and is [# of in.] inches tall. Her/His BMI is [BMI #].</p><figure class=\"table\"><table><tbody><tr><td>N</td><td>Test</td><td>Hed1</td><td>Hed0,5</td><td>Hed2</td></tr><tr><td>1</td><td>Lalala</td><td>Hi</td><td>123</td><td>50mg</td></tr><tr><td>2</td><td>qwe</td><td>345</td><td>456</td><td>Iuyrffh</td></tr></tbody></table></figure><p><br>&nbsp;</p><p>&nbsp;</p><p>&nbsp;</p><p>I am editing this from the iPhone XR</p><p>12345789 101112131415</p><p>&nbsp;</p><p>I have edited this letter on iPhone.</p><p>testing autosave</p><p><br>&nbsp;</p><p>I have been [patient’s name]’s primary care physician for the past [#of yrs] years.<br>I have supervised several of her/his weight control diets and programs. None of these have resulted in any sustained weight loss. As a result of this persistent morbid obesity, her/his co-morbid conditions are becoming more difficult to <i>manage</i>.</p><ol><li>ios</li><li>test</li><li><br>&nbsp;</li></ol><p><br><br>&nbsp;</p><p>These <strong>co-morbid conditions</strong> are as follows:</p><ol><li>Hypertension</li><li>Diabetes Mellitus</li><li>Obesity Related Depression</li></ol><figure class=\"table\"><table><tbody><tr><td>Test</td><td>Test2</td><td>&nbsp;</td></tr><tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr><tr><td>1</td><td rowspan=\"2\"><p>3</p><p>4</p></td><td>&nbsp;</td></tr><tr><td>2</td><td>&nbsp;</td></tr></tbody></table></figure><ul><li>hhgf</li><li>fhh</li><li><br><br>&nbsp;</li></ul><p><u>Duration:</u></p><p>3 years 5 years 3 years</p><p><br><br>&nbsp;</p><p>Medication:</p><p><i>Norvasc/Tenormin Glucophage Prozac, 5<sup>2&nbsp;</sup>mg, 6<sub>123&nbsp;</sub>sgsd.</i></p><p><br><br>&nbsp;</p><p>Testing bulleted list:&nbsp;</p><ul><li>wer</li><li>asd</li><li>sfet</li></ul><p>Test</p><p><br><br>&nbsp;</p><p>New patch? - No</p><p><br><br>&nbsp;</p><ol><li>Edit in the web client</li><li>edit in iOS client</li><li>edit on<i><strong><u> iOS - part 2!&nbsp;</u></strong></i></li><li>123456</li></ol><ul><li>testing bulleted list</li><li>12346€&amp;@$£#ßyiæ</li></ul><p><br><br>&nbsp;</p><p>Losing weight will certainly make these conditions easier to manage. Since non-surgical programs have failed to provide any long-term benefits for the patient, I feel surgery is her/his only option.</p><p>I hope you will find [patient’s name] a suitable candidate for the surgical weight reduction program. It will provide a tool to assist her/him in losing weight, as well as</p><p>maintain that weight loss. I anticipate that this will provide her/him with a significantly improved quality of life.</p><p><br><br>&nbsp;</p><p>123</p><p><br><br>&nbsp;</p><p>123456</p><p><strong>Razmik edition</strong></p><p><i>muchachos</i></p><p><u>rrrrrrrrrr</u></p><ul><li>rffff</li><li>fdrrdd</li><li>&nbsp;</li><li>&nbsp;</li><li><br>&nbsp;</li></ul><p><br>&nbsp;</p><ol><li>F</li><li>ertfff</li></ol><p>ertty</p><figure class=\"table\"><table><tbody><tr><td>Hdhhcc</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr><tr><td>&nbsp;</td><td>F</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr><tr><td>Ddd</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr><tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr><tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr><tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr><tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>Dd</td><td>&nbsp;</td><td>&nbsp;</td></tr></tbody></table></figure><p>Sincerely,</p>"
    private val TEMPLATE = "place_for_data"

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

        val data = assets.open("editor.html").bufferedReader().use { it.readText() }.replace(TEMPLATE, data)
        webView.loadDataWithBaseURL("file:///android_asset/", data, "text/html", "UTF-8", null)

        nestedScroll.viewTreeObserver.addOnScrollChangedListener { checkVisibilityButton() }
    }

    private fun prepareWebView() {
        webViewProgress.visibility = GONE
        webView.setOnTouchListener(untouchable)
        editButton.visibility = VISIBLE
        checkVisibilityButton()
    }

    private fun checkVisibilityButton() {
        if (isCheckVisibility.not()) return

        val rect = Rect()
        nestedScroll.getDrawingRect(rect)

        val top = editButton.y
        val bottom = top + editButton.height

        editButtonClone.visibility = if (rect.top < top && rect.bottom > bottom) GONE else VISIBLE
    }

    fun onEditClick(view: View) {
        isCheckVisibility = false
        webView.setOnTouchListener(touchable)
        webView.performClick()

        editButton.visibility = GONE
        editButtonClone.visibility = GONE
    }

    override fun onBackPressed() = disableWebView()

    private fun disableWebView() {
        isCheckVisibility = true
        prepareWebView()
        webView.clearFocus()
    }
}
