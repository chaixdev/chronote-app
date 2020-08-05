package be.chaidev.chronote.data.cache

import be.chaidev.chronote.data.EntityMapper
import be.chaidev.chronote.data.cache.entity.TopicEntity
import be.chaidev.chronote.data.model.Subject
import be.chaidev.chronote.data.model.Topic
import be.chaidev.chronote.data.model.Type
import javax.inject.Inject

class CacheMapper @Inject constructor() : EntityMapper<TopicEntity, Topic> {
    override fun mapFromEntity(entity: TopicEntity): Topic {
        val tags = entity.tags.split(";")
        val subject = Subject(Type.valueOf(entity.subjectType),entity.subjectUri,entity.subjectTitle,entity.subjectDuration)
        return Topic(
            entity.id,
            subject,
            tags,
            entity.dateCreated,
            entity.dateModified
        )
    }

    override fun mapToEntity(model: Topic): TopicEntity {
        return TopicEntity(
            model.id,
            model.subject.title,
            model.subject.uri,
            model.subject.type.name,
            model.subject.duration,
            model.tags.joinToString(";"),
            model.dateCreated,
            model.dateModified
        )
    }

    fun mapToEntitiesList(entities: List<Topic>): List<TopicEntity>{
        return entities.map(this::mapToEntity)
    }

    fun mapFromEntitiesList(entities: List<TopicEntity>): List<Topic>{
        return entities.map(this::mapFromEntity)
    }
}