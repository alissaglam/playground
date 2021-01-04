package io.robusta.team.common.event.repository

import io.robusta.team.common.event.entity.EventProduce
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface EventProduceRepository: JpaRepository<EventProduce, UUID>
