package com.example.basekotlinapp.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {

    @Query("SELECT * FROM items_table WHERE remoteId=:remoteId")
    fun findByRemoteId(remoteId: String): Flow<ItemRoomModel>

    @Query("SELECT * FROM items_table")
    fun findAll(): Flow<List<ItemRoomModel>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun add(itemRoomModel: ItemRoomModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAll(roomItems: List<ItemRoomModel>)

    @Delete
    fun delete(itemRoomModel: ItemRoomModel)

    @Query("DELETE FROM items_table")
    fun deleteAll()

    @Query("DELETE FROM items_table WHERE remoteId IN (:remoteIds)")
    suspend fun deleteByRemoteIds(remoteIds: List<String>)

    @Transaction
    suspend fun refreshAll(roomItems: List<ItemRoomModel>) {
        deleteAll()
        addAll(roomItems)
    }

    @Transaction
    suspend fun refreshByRemoteIds(remoteIds: List<String>, roomItems: List<ItemRoomModel>) {
        deleteByRemoteIds(remoteIds)
        addAll(roomItems)
    }
}