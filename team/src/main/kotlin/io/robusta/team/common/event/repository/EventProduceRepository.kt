package io.robusta.team.common.event.repository

import io.robusta.team.common.event.entity.EventProduce
import org.springframework.data.domain.Page
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime
import java.util.*

interface EventProduceRepository: JpaRepository<EventProduce, UUID> {

    @Query("select p from EventProduce p left join EventConsume c on p.id = c.id where c.id is null and p.createdAt > :offset order by p.createdAt asc")
    fun getUnprocessedEventsTop1000(offset: LocalDateTime): Page<EventProduce>
}
