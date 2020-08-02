package be.chaidev.chronote.model

data class User (
    val email: String,
    val topics: List<Topic>,
    val userDefinedTags: Set<String>
)
