package io.robusta.team.repository

import io.robusta.team.entity.Team
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

interface TeamRepository: JpaRepository<Team, Long>
