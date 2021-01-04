package io.robusta.team.common.event.entity

import java.time.Instant
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class EventProduce(
        @Id
        val id: UUID,
        val event: String,
        val payload: String,
        val createdAt: Instant
)
