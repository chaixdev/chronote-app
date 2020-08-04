package be.chaidev.chronote.data.model

import be.chaidev.chronote.data.cache.entity.TopicEntity

data class User (
    val email: String,
    val topics: List<TopicEntity>,
    val userDefinedTags: Set<String>
)
