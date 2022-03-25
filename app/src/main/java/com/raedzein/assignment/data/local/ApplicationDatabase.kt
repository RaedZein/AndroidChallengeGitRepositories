package com.raedzein.assignment.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.raedzein.assignment.domain.model.FavouritedRepo
import com.raedzein.assignment.domain.model.GithubRepo

/**
 * The Room database that contains the GithubRepo table
 * @author Raed Zein
 * created on Thursday, 24 March, 2022
 */
@Database(entities = [GithubRepo::class, FavouritedRepo::class], version = 1)
abstract class ApplicationDatabase : RoomDatabase() {

    abstract fun githubRepo(): GithubRepoDao
    abstract fun favouritedRepo(): FavouritedRepoDao

    companion object {

        @Volatile private var INSTANCE: ApplicationDatabase? = null

        fun getInstance(context: Context): ApplicationDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                ApplicationDatabase::class.java, "androidchallenge.db")
                .build()
    }
}

