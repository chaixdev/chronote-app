package be.chaidev.chronote.data.network.retrofit

import be.chaidev.chronote.data.cache.entity.TopicEntity
import be.chaidev.chronote.data.EntityMapper
import be.chaidev.chronote.data.model.Subject
import be.chaidev.chronote.data.model.Topic
import be.chaidev.chronote.data.network.dto.TopicDto
import javax.inject.Inject

class NetworkMapper @Inject constructor(): EntityMapper<Topic, TopicDto> {

    override fun mapFromEntity(entity: Topic): TopicDto {

        return TopicDto(
            entity.id,
            entity.subject,
            entity.tags,
            entity.dateCreated,
            entity.dateModified,
            emptyList()
        )
    }

    override fun mapToEntity(model: TopicDto): Topic {

        return Topic(
            model.id,
            model.subject,
            model.tags,
            model.dateCreated,
            model.dateModified
        )
    }

    fun mapToEntitiesList(entities: List<Topic>): List<TopicDto>{
        return entities.map(this::mapFromEntity)
    }

    fun mapFromEntitiesList(entities: List<TopicDto>): List<Topic>{
        return entities.map(this::mapToEntity)
    }
}