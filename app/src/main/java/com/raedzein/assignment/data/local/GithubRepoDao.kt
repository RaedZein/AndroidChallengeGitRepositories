package com.raedzein.assignment.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.raedzein.assignment.domain.model.GithubRepo

/**
 * @author Raed Zein
 * created on Thursday, 24 March, 2022
 */
@Dao
interface GithubRepoDao {
    /**
     * Room knows how to return a LivePagedListProvider, from which we can get a LiveData and serve
     * it back to UI via ViewModel.
     */
    @Query("SELECT * FROM GithubRepo ORDER BY starsCount DESC")
    fun allGithubRepos(): PagingSource<Int, GithubRepo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(repos: List<GithubRepo>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(repos: GithubRepo)

    @Query("DELETE FROM GithubRepo")
    suspend fun deleteAll()
}