package io.robusta.team.entity

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Team(
        @Id
        @GeneratedValue
        val id: Long? = null,
        val name: String
)
