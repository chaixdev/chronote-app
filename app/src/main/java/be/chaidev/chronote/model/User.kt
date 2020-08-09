package be.chaidev.chronote.model

import be.chaidev.chronote.datasources.cache.entity.TopicEntity

data class User (
    val email: String,
    val topics: List<TopicEntity>,
    val userDefinedTags: Set<String>
)
