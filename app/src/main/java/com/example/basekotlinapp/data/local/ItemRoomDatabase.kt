package com.example.basekotlinapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [ItemRoomModel::class], version = 1, exportSchema = false)
abstract class ItemRoomDatabase: RoomDatabase() {

    abstract fun itemDao(): ItemDao
}