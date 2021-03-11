package com.ricwil.habit.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class Task(val name: String, val complete: Boolean) {

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}