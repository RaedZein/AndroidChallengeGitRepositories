package com.raedzein.assignment.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.raedzein.assignment.domain.model.FavouritedRepo

/**
 * @author Raed Zein
 * created on Thursday, 24 March, 2022
 */
@Dao
interface FavouritedRepoDao {

    @Query("SELECT * FROM FavouritedRepo where githubRepoId = :id")
    fun getLiveData(id: Long): LiveData<FavouritedRepo?>

    @Query("SELECT * FROM FavouritedRepo where githubRepoId = :id")
    suspend fun get(id: Long): FavouritedRepo?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(repo: FavouritedRepo)

    @Query("DELETE FROM FavouritedRepo where githubRepoId = :id")
    suspend fun delete(id: Long)
}