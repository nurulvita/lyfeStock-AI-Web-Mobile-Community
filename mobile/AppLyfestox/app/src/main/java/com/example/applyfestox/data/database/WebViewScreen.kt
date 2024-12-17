//package com.example.applyfestox.database
//
//import android.webkit.CookieManager
//import android.webkit.WebView
//import android.webkit.WebViewClient
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.viewinterop.AndroidView
//
//// WebViewActivity.kt
//@Composable
//fun WebViewActivity(url: String, onCookieReady: (String) -> Unit) {
//    AndroidView(factory = {
//        WebView(it).apply {
//            settings.javaScriptEnabled = true // Aktifkan JavaScript
//            webViewClient = object : WebViewClient() {
//                override fun onPageFinished(view: WebView?, url: String?) {
//                    super.onPageFinished(view, url)
//                    val cookie = CookieManager.getInstance().getCookie(url)
//                    onCookieReady(cookie)
//                }
//            }
//            loadUrl(url)
//        }
//    })
//}
