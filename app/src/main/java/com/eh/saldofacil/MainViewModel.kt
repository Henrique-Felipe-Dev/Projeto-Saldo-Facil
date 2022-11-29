package com.eh.saldofacil

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chaquo.python.Python
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jsoup.Connection
import org.jsoup.Jsoup

class MainViewModel : ViewModel() {

    fun logar(rg: String, rgDig: String, estado: String, cpf: String, senha: String, captcha: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/107.0.0.0 Safari/537.36"
                val response = Jsoup.connect("https://scapub.sbe.sptrans.com.br/sa/acessoPublico/index.action")
                    .method(Connection.Method.GET)
                    .userAgent(USER_AGENT)
                    .execute()

                val loginDoc = response.parse()

                val cookies = HashMap<String, String>()
                val formData = HashMap<String, String>()

                cookies.putAll(response.cookies())

                formData.put("usuLoginUserId", "")
                formData.put("escolherUsuario", "")
                formData.put("oferecerCadastro", "")
                formData.put("redirect", "")
                formData.put("usuLoginRg", rg)
                formData.put("usuLoginRgDigit", rgDig)
                formData.put("usuLoginRgState", estado)
                formData.put("usuLoginCpf", cpf)
                formData.put("usuLogin", "")
                formData.put("_usuSenha", "")
                formData.put("usuSenha", senha)
                formData.put("g-recaptcha-response", captcha)

                val homePage = Jsoup.connect("https://scapub.sbe.sptrans.com.br/sa/acessoPublico/login.action")
                    .cookies(cookies)
                    .data(formData)
                    .method(Connection.Method.POST)
                    .userAgent(USER_AGENT)
                    .execute()

                Log.d("sptrans", homePage.parse().html())
            }catch (e:Exception) {
                Log.e("sptrans", e.message.toString())
            }
        }
    }

}