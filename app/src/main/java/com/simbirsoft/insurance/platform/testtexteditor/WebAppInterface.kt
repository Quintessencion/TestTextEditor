package com.simbirsoft.insurance.platform.testtexteditor

import android.content.Context
import android.webkit.JavascriptInterface
import android.widget.Toast

class WebAppInterface(val context: Context) {

    @JavascriptInterface
    fun handleChanges(message: String) {
//        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
    }
}