package io.robusta.team.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class TeamLog(
        @Id
        @GeneratedValue(strategy=GenerationType.IDENTITY)
        val id: Long? = null,
        val log: String
)
