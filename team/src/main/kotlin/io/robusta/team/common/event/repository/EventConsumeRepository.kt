package io.robusta.team.common.event.repository

import io.robusta.team.common.event.entity.EventConsume
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface EventConsumeRepository: JpaRepository<EventConsume, UUID>
