package com.example.basekotlinapp.data.local

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "items_table")
data class ItemRoomModel(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val remoteId: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phone: String
): Parcelable