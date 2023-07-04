package com.eh.saldofacil.infra

import android.content.Context

class SecurityPreferences (context: Context) {

    private val sharedPreferences =
        context.getSharedPreferences("rg_cpf", Context.MODE_PRIVATE)

    fun storeString(chave: String, valor: String){
        sharedPreferences.edit().putString(chave, valor).apply()
    }

    fun storeBoolean(chave: String, valor: Boolean){
        sharedPreferences.edit().putBoolean(chave, valor).apply()
    }

    fun getString(chave: String): String{
        return sharedPreferences.getString(chave, "") ?: ""
    }

    fun getBoolean(chave: String): Boolean{
        return sharedPreferences.getBoolean(chave, false)
    }

    fun clear(){
        sharedPreferences.edit().clear().apply()
    }

}