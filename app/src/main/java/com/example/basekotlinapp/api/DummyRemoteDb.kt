package com.example.basekotlinapp.api

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.basekotlinapp.TAG
import com.example.basekotlinapp.model.ModelItem
import com.google.gson.Gson
import retrofit2.Response
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class DummyRemoteDb(private val context: Context) {

    private val itemsLiveData = MutableLiveData<List<ModelItem>>()
    private val emptyModelItem = ModelItem(
        id = "",
        firstName = "",
        lastName = "",
        email = "",
        phone = ""
    )

    init {
        val itemsJson = loadAsString("items_db.json")
        Log.i(TAG, ": $itemsJson")
        val itemsList: List<ModelItem> =
            Gson().fromJson(itemsJson, Array<ModelItem>::class.java).toList()
        itemsLiveData.value = itemsList
    }

    fun fetchList(): Response<List<ModelItem>> = Response.success(itemsLiveData.value)

    fun fetchItemById(id: String): Response<ModelItem> {
        val item = itemsLiveData.value?.firstOrNull { it.id == id } ?: emptyModelItem
        return Response.success(item)
    }

    fun addItem(item: ModelItem): Response<Any> {
        var items = itemsLiveData.value ?: emptyList()
        items = listOf(item) + items
        itemsLiveData.value = items
        return Response.success(true)
    }

    fun updateItem(itemId: String, item: ModelItem): Response<ModelItem> {
        var items = itemsLiveData.value ?: emptyList()
        val indexOfOldItem = items.indexOfFirst { it.id == itemId }
        if (indexOfOldItem >= 0) {
            items = listOf(item) + items - listOf(items[indexOfOldItem])
        }
        itemsLiveData.value = items
        return Response.success(item)
    }


    private fun loadAsString(filename: String): String {
        var result = ""
        var reader: BufferedReader? = null
        try {
            reader = BufferedReader(InputStreamReader(context.assets.open(filename), "UTF-8"))

            var line: String? = reader.readLine()
            while (line != null) {
                result += "$line\n"
                line = reader.readLine()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                reader?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return result
    }
}