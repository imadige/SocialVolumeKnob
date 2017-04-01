package org.ligi.socialvolumeknob

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.print.PrintAttributes
import android.print.PrintManager
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Base64
import android.webkit.WebView
import android.webkit.WebViewClient
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_intro.*
import net.glxn.qrgen.android.QRCode
import java.io.ByteArrayOutputStream

class IntroActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_intro)

        print_button.setOnClickListener {
            doWebViewPrint()
        }

        fab.setOnClickListener {
            val text = identifier_edittext.text

            if (text.isBlank()) {
                AlertDialog.Builder(this@IntroActivity)
                        .setMessage("You need to enter a identifier")
                        .show()
            } else {
                State.identifier = text.toString()
                startActivity(Intent(this, MainActivity::class.java))
            }
        }

    }

    var mWebView: WebView? = null
    private fun doWebViewPrint() {
        // Create a WebView object specifically for printing
        val webView = WebView(this)
        webView.setWebViewClient(object : WebViewClient() {

            override fun shouldOverrideUrlLoading(view: WebView, url: String) = false

            override fun onPageFinished(view: WebView, url: String) {
                createWebPrintJob(view)
                mWebView = null
            }
        })

        val bos = ByteArrayOutputStream()
        val qrCode = QRCode.from("https://t2m7u.app.goo.gl/?link=https://socialvolumeknob.firebaseapp.com?token=${FirebaseInstanceId.getInstance().getToken()}&user=bar&apn=org.ligi.socialvolumeknob")
        qrCode.bitmap().compress(Bitmap.CompressFormat.PNG, 100, bos)
        bos.close()
        val encode = Base64.encodeToString(bos.toByteArray(), Base64.DEFAULT)

        // Generate an HTML document on the fly:
        val htmlDocument = "<html><body><h1>Social Volume Knob</h1>" +
                "<p><font size='21'>With this qr-code you can adjust the volume of user: ${identifier_edittext.text}<br/><br/>" +
                "Scan the qr-code, install the app, adjust the volume, enjoy your time</font></p>" +
                "<center><img width=\"80%\" src=\"data:image/png;base64,$encode\"/></center></body></html>"
        webView.loadDataWithBaseURL(null, htmlDocument, "text/HTML", "UTF-8", null)

        mWebView = webView
    }

    private fun createWebPrintJob(webView: WebView) {

        val printAdapter = webView.createPrintDocumentAdapter()

        val jobName = getString(R.string.app_name) + " Document"
        val printManager = getSystemService(Context.PRINT_SERVICE) as PrintManager
        printManager.print(jobName, printAdapter, PrintAttributes.Builder().build())

    }
}
