package com.raedzein.assignment.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author Raed Zein
 * created on Thursday, 24 March, 2022
 */
@Entity
data class FavouritedRepo(
    @PrimaryKey(autoGenerate = false)
    val githubRepoId: Long = 0
)
