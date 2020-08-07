package be.chaidev.chronote.data.cache

import androidx.lifecycle.LiveData

interface DataCache<Item> {

    fun find(topidId:String): LiveData<Item>

    fun getAll():LiveData<List<Item>>

    fun save(topic:Item)

    fun saveAll(topics:List<Item>)

    fun delete(topicId:String)

    fun returnOrderedQuery(filterAndOrder: String): LiveData<List<Item>>
}