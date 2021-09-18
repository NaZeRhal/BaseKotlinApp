package com.example.basekotlinapp.data.api

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.basekotlinapp.model.ItemModel
import com.example.basekotlinapp.presentation.ui.TAG
import com.google.gson.Gson
import retrofit2.Response
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import kotlin.random.Random

class DummyRemoteDb(private val context: Context) {

    private val itemsLiveData = MutableLiveData<List<ItemModel>>()
    private val emptyModelItem = ItemModel(
        id = "",
        firstName = "",
        lastName = "",
        email = "",
        phone = ""
    )

    init {
        val itemsJson = loadAsString("items_db.json")
        Log.i(TAG, ": $itemsJson")
        val itemsList: List<ItemModel> =
            Gson().fromJson(itemsJson, Array<ItemModel>::class.java).toList()
        itemsLiveData.value = itemsList
    }

    fun fetchList(): Response<List<ItemModel>> = Response.success(itemsLiveData.value)

    fun fetchItemById(id: String): Response<ItemModel> {
        val item = itemsLiveData.value?.firstOrNull { it.id == id } ?: emptyModelItem
        return Response.success(item)
    }

    fun addItem(itemModel: ItemModel): Response<String> {
        var items = itemsLiveData.value ?: emptyList()
        val addedItemModel = itemModel.run {
            ItemModel(
                id = Random.nextInt(0, 10000).toString(),
                firstName = firstName,
                lastName = lastName,
                email = email,
                phone = phone
            )
        }
        items = listOf(addedItemModel) + items
        itemsLiveData.value = items
        return Response.success(addedItemModel.id)
    }

    fun updateItem(itemId: String, itemModel: ItemModel): Response<ItemModel> {
        var items = itemsLiveData.value ?: emptyList()
        val indexOfOldItem = items.indexOfFirst { it.id == itemId }
        if (indexOfOldItem >= 0) {
            items = listOf(itemModel) + items - listOf(items[indexOfOldItem])
        }
        itemsLiveData.value = items
        return Response.success(itemModel)
    }

    fun deleteItem(itemModelId: String): Response<ItemModel> {
        var items = itemsLiveData.value ?: emptyList()
        val index = items.indexOfFirst { it.id == itemModelId }
        val itemForDelete = items[index]
        items = items - listOf(itemForDelete)
        itemsLiveData.value = items
        return Response.success(itemForDelete)
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