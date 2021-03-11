package com.ricwil.habit.data

interface TaskDataSource {
    fun addTask(name: String, complete: Boolean)
    fun getAllTasks(callback: (List<Task>) -> Unit)
    fun removeTasks()
}