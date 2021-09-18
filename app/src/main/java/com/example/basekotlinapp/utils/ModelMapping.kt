package com.example.basekotlinapp.utils

import com.example.basekotlinapp.local.ItemRoomModel
import com.example.basekotlinapp.model.ItemModel

abstract class ModelMapping {

    companion object {
        fun itemRoomToItemModel(itemRoomModel: ItemRoomModel): ItemModel = itemRoomModel.run {
            ItemModel(
                id = remoteId,
                firstName = firstName,
                lastName = lastName,
                email = email,
                phone = phone
            )
        }

        fun itemRoomToItemModel(roomItems: List<ItemRoomModel>): List<ItemModel> =
            roomItems.map { itemRoomToItemModel(it) }

        fun itemModelToItemRoom(remoteId: String, itemModel: ItemModel): ItemRoomModel =
            itemModel.run {
                ItemRoomModel(
                    id = 0,
                    remoteId = remoteId,
                    firstName = firstName,
                    lastName = lastName,
                    email = email,
                    phone = phone
                )
            }
    }
}