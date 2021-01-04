package io.robusta.team.common.event.repository

import io.robusta.team.common.event.entity.FallbackEventConsumeLog
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface FallbackEventConsumeLogRepository: JpaRepository<FallbackEventConsumeLog, UUID>
