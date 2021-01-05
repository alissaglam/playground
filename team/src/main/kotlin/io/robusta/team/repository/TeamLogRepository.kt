package io.robusta.team.repository

import io.robusta.team.entity.TeamLog
import org.springframework.data.jpa.repository.JpaRepository

interface TeamLogRepository: JpaRepository<TeamLog, Long>
