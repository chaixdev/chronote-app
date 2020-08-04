package be.chaidev.chronote.data

interface EntityMapper<Entity, DomainModel>{

    fun mapFromEntity(entity: Entity):DomainModel
    fun mapToEntity(model:DomainModel): Entity

}
