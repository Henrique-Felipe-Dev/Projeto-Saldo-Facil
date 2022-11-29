package com.eh.saldofacil.utils

import android.content.Context
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class AssetReader {
    // https://stackoverflow.com/a/4867192/12734824

    companion object {
        @Throws(IOException::class)
        fun readFileAsString(context: Context, fileName: String?): String? {
            val reader = BufferedReader(InputStreamReader(fileName?.let { context.assets.open(it) }))
            var line: String?
            var results: String? = ""
            while (reader.readLine().also { line = it } != null) {
                results += line
            }
            reader.close()
            return results
        }
    }


}