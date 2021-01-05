package io.robusta.team.repository

import io.robusta.team.entity.Team
import org.springframework.data.jpa.repository.JpaRepository

interface TeamRepository: JpaRepository<Team, Long>
