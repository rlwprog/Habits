package com.ricwil.habit.data

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TaskDao {

    @Query("SELECT * FROM tasks ORDER BY id DESC")
    fun getAll(): List<Task>

    @Query("SELECT * FROM tasks ORDER BY id DESC")
    fun selectAllTasksCursor(): Cursor

    @Query("SELECT * FROM tasks WHERE id = :id")
    fun selectTaskById(id: Long): Cursor?

    @Insert
    fun insertAll(vararg tasks: Task)

    @Query("DELETE FROM tasks")
    fun nukeTable()
}