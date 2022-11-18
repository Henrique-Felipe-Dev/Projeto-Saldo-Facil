package com.eh.saldofacil

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chaquo.python.Python
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    var result = MutableLiveData<String>()

    fun getPythonHelloWorld() {
        viewModelScope.launch {
            val python = Python.getInstance()
            val pythonFile = python.getModule("requisicoes")
            result.value = pythonFile.callAttr("helloworld").toString()
        }
    }

}