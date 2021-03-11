package com.ricwil.habit.data

import android.os.Handler
import android.os.Looper
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskLocalDataSource @Inject constructor(private val taskDao: TaskDao) : TaskDataSource {

    private val executorService: ExecutorService = Executors.newFixedThreadPool(4)
    private val mainThreadHandler by lazy {
        Handler(Looper.getMainLooper())
    }

    override fun addTask(name: String, complete: Boolean) {
        executorService.execute {
            taskDao.insertAll(
                Task(
                    name,
                    complete
                )
            )
        }
    }

    override fun getAllTasks(callback: (List<Task>) -> Unit) {
        executorService.execute {
            val tasks = taskDao.getAll()
            mainThreadHandler.post { callback(tasks) }
        }
    }

    override fun removeTasks() {
        executorService.execute {
            taskDao.nukeTable()
        }
    }

}