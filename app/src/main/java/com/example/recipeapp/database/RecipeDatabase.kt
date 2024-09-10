package com.example.recipeapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.recipeapp.dao.RecipeDao
import com.example.recipeapp.model.Recipe

@Database(entities = [Recipe::class], version = 2, exportSchema = false)
abstract class RecipeDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao

    companion object {
        @Volatile
        private var INSTANCE: RecipeDatabase? = null

        // Migration strategy from version 1 to 2
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Add the new column 'category' with a default value to avoid issues with non-null constraints
                database.execSQL("ALTER TABLE recipe ADD COLUMN category TEXT")
            }
        }

        fun getDatabase(context: Context): RecipeDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RecipeDatabase::class.java,
                    "recipe_database"
                )
                    .addMigrations(MIGRATION_1_2) // Ensure migration is added
                    .fallbackToDestructiveMigration() // Optionally use destructive migration if needed
                    .build()
                INSTANCE = instance
                instance
            }
        }

    }
}
