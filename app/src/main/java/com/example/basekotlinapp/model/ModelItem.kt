package com.example.basekotlinapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ModelItem(
    val id: String,
    val code: String,
    val title: String,
    val name: String
) : Parcelable