package be.chaidev.chronote.data.cache

import be.chaidev.chronote.data.EntityMapper
import be.chaidev.chronote.data.cache.entity.TopicEntity
import be.chaidev.chronote.data.model.Topic
import be.chaidev.chronote.data.network.dto.TopicDto
import javax.inject.Inject

class CacheMapper @Inject constructor() : EntityMapper<TopicEntity, Topic> {
    override fun mapFromEntity(entity: TopicEntity): Topic {
        return Topic(
            entity.id,
            entity.title,
            entity.duration,
            entity.tags,
            entity.dateCreated,
            entity.dateModified
        )
    }

    override fun mapToEntity(model: Topic): TopicEntity {
        return TopicEntity(
            model.id,
            model.title,
            model.duration,
            model.tags,
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