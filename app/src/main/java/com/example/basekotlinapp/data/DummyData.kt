package com.example.basekotlinapp.data

import com.example.basekotlinapp.model.Post
import retrofit2.Response
import kotlin.random.Random

class DummyData {

    companion object {
       private fun loadData(): List<Post> = listOf(
            Post(id = Random.nextInt().toString(), title = "hello", name = "Max"),
            Post(id = Random.nextInt().toString(), title = "rtertyb", name = "Karl"),
            Post(id = Random.nextInt().toString(), title = "erbyterby", name = "Milano"),
            Post(id = Random.nextInt().toString(), title = "ertwert", name = "John"),
            Post(id = Random.nextInt().toString(), title = "mghjkfrt", name = "Poul"),
            Post(id = Random.nextInt().toString(), title = "dtfybbtd", name = "Mary"),
            Post(id = Random.nextInt().toString(), title = "rtbyretb", name = "Wolter"),
            Post(id = Random.nextInt().toString(), title = "ertverv", name = "Luck"),
            Post(id = Random.nextInt().toString(), title = "ewrvtwevr", name = "Jack"),
            Post(id = Random.nextInt().toString(), title = "edcrfwec", name = "Lara"),
            Post(id = Random.nextInt().toString(), title = "qwre", name = "Vasya"),
            Post(id = Random.nextInt().toString(), title = "ghjkmhy", name = "Liza"),
            Post(id = Random.nextInt().toString(), title = "dsfgv", name = "Lora"),
            Post(id = Random.nextInt().toString(), title = "sdvt", name = "Luck"),
            Post(id = Random.nextInt().toString(), title = "rfvrev", name = "Max")
        )

        fun fetchList(): Response<List<Post>> = Response.success(loadData())
    }
}