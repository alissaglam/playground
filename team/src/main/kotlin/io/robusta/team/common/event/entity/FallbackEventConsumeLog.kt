package io.robusta.team.common.event.entity

import java.time.LocalDateTime
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class FallbackEventConsumeLog(
        @Id
        val id: Long,
        val lastlyConsumedEventId: UUID,
        val lastlyConsumedEventProduceTime: LocalDateTime
)
