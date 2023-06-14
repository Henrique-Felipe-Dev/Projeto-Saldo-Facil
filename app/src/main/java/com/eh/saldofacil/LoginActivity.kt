package com.eh.saldofacil

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.graphics.Color
import android.os.Bundle
import android.view.WindowManager
import android.webkit.*
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.eh.saldofacil.databinding.ActivityLoginBinding
import com.eh.saldofacil.utils.AssetReader
import kotlinx.coroutines.launch
import java.io.IOException


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val mainViewModel: MainViewModel by viewModels()
    private var captcha = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mainIntent = Intent(this, MainActivity::class.java)

        mainIntent.putExtra("cookies", mainViewModel.cookies)

        binding.buttonEntrar.setOnClickListener {

            val rg = binding.editRg.text.toString()
            val digito = binding.editDigito.text.toString()
            val cpf = binding.editCpf.text.toString()
            val email = binding.editEmail.text.toString()
            val senha = binding.editSenha.text.toString()

            if(email.isNotBlank() && (rg.isNotBlank() || digito.isNotBlank() || cpf.isNotBlank())){
                Toast.makeText(this, "Informe seu RG e CPF ou e-mail.", Toast.LENGTH_LONG).show()
            }else if(captcha.isBlank()){
                Toast.makeText(this, "Resolva o Captcha!", Toast.LENGTH_LONG).show()
            }else{
                mainViewModel.logar(rg, digito, "SP", cpf, email, senha, captcha)
            }
        }

        mainViewModel.statusCode.observe(this) {
            if(it == 500){
                Toast.makeText(this, "Verifique os campos...", Toast.LENGTH_LONG).show()
            }else{
                if(it != 0){
                    startActivity(mainIntent)
                    finish()
                }
            }
        }

        binding.checkCaptcha.setOnClickListener {
            binding.checkCaptcha.isChecked = false
            getRecaptchaToken()
        }

    }

    private fun getRecaptchaToken() {

        val url = "https://scapub.sbe.sptrans.com.br/sa/acessoPublico/index.action"
        val dialogBuilder: AlertDialog.Builder = AlertDialog.Builder(this)
        val webView = WebView(this)
        webView.setBackgroundColor(Color.TRANSPARENT)
        val settings = webView.settings
        settings.javaScriptEnabled = true
        settings.loadWithOverviewMode = true
        settings.builtInZoomControls = false
        settings.cacheMode = WebSettings.LOAD_DEFAULT
        settings.domStorageEnabled = true
        webView.isVerticalScrollBarEnabled = false
        dialogBuilder.setView(webView)
        dialogBuilder.setCancelable(false)
        dialogBuilder.setTitle("reCAPTCHA")
        val recaptchaDialog: AlertDialog = dialogBuilder.show()
        recaptchaDialog.getWindow()!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        webView.webChromeClient = object : WebChromeClient() {
            override fun onConsoleMessage(consoleMessage: ConsoleMessage): Boolean {
                val message = consoleMessage.message()
                if (message.startsWith("koubilgicaptchatoken:")) {
                    val token = message.substring(21)
                    recaptchaDialog.dismiss()
                    captcha = token
                    if(token != ""){
                        binding.checkCaptcha.isChecked = true
                        binding.checkCaptcha.text = "Captcha Resolvido!"
                    }
                }
                return super.onConsoleMessage(consoleMessage)
            }
        }

        var recaptchaHtml: String? = null
        try{
            recaptchaHtml = AssetReader.readFileAsString(this, "recaptcha.html")
        }catch(e: IOException){
            e.printStackTrace()
        }

        webView.loadDataWithBaseURL(
            url, recaptchaHtml!!,
            "text/html",
            "UTF-8",
            null
        )


    }

}