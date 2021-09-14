package com.example.basekotlinapp.api

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.basekotlinapp.TAG
import com.example.basekotlinapp.model.ModelItem
import org.koin.core.component.KoinComponent
import retrofit2.Response

class DummyData {

    private val defaultModelItem = ModelItem("", "", "", "")

    private val itemsLiveData: MutableLiveData<List<ModelItem>> by lazy {
        val list = listOf(
            ModelItem(id = "1", code = "DER", title = "hello", name = "Max"),
            ModelItem(id = "2", code = "SWE", title = "rtertyb", name = "Karl"),
            ModelItem(id = "3", code = "ASW", title = "erbyterby", name = "Milano"),
            ModelItem(id = "4", code = "GTY", title = "ertwert", name = "John"),
            ModelItem(id = "5", code = "JYU", title = "mghjkfrt", name = "Poul"),
            ModelItem(id = "6", code = "DFG", title = "dtfybbtd", name = "Mary"),
            ModelItem(id = "7", code = "QWE", title = "rtbyretb", name = "Wolter"),
            ModelItem(id = "8", code = "POI", title = "ertverv", name = "Luck"),
            ModelItem(id = "9", code = "YUJ", title = "ewrvtwevr", name = "Jack"),
            ModelItem(id = "10", code = "HBD", title = "edcrfwec", name = "Lara"),
            ModelItem(id = "11", code = "SCN", title = "qwre", name = "Vasya"),
            ModelItem(id = "12", code = "TIM", title = "ghjkmhy", name = "Liza"),
            ModelItem(id = "13", code = "WZV", title = "dsfgv", name = "Lora"),
            ModelItem(id = "14", code = "UYH", title = "sdvt", name = "Luck"),
            ModelItem(id = "15", code = "TGF", title = "rfvrev", name = "Max")
        )
        MutableLiveData(list)
    }

    fun fetchList(): Response<List<ModelItem>> = Response.success(itemsLiveData.value)

    fun fetchItemById(id: String): Response<ModelItem> {
        val item = itemsLiveData.value?.firstOrNull { it.id == id } ?: defaultModelItem
        Log.i(TAG, "fetchItemById: $item")
        return Response.success(item)
    }

    fun addItem(item: ModelItem): Response<Any> {
        var items = itemsLiveData.value ?: emptyList()
        items = items + listOf(item)
        itemsLiveData.value = items
        return Response.success(true)
    }
}