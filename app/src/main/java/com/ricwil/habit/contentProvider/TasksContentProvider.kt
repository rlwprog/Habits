package com.ricwil.habit.contentProvider

import android.content.*
import android.database.Cursor
import android.net.Uri
import com.ricwil.habit.data.TaskDao
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.components.ApplicationComponent

private const val TASKS_TABLE = "tasks"

private const val AUTHORITY = "com.ricwil.habit.provider"

private const val CODE_TASKS_DIR = 1

private const val CODE_TASKS_ITEM = 2

class TasksContentProvider : ContentProvider() {
    private val matcher: UriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
        addURI(AUTHORITY, TASKS_TABLE, CODE_TASKS_DIR)
        addURI(AUTHORITY, "$TASKS_TABLE/*", CODE_TASKS_ITEM)
    }

    @InstallIn(ApplicationComponent::class)
    @EntryPoint
    interface TasksContentProviderEntryPoint {
        fun taskDao(): TaskDao
    }

    private fun getTaskDao(appContext: Context): TaskDao {
        val hiltEntryPoint = EntryPointAccessors.fromApplication(
            appContext,
            TasksContentProviderEntryPoint::class.java
        )
        return hiltEntryPoint.taskDao()
    }

    override fun onCreate(): Boolean {
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        val code: Int = matcher.match(uri)
        return if (code == CODE_TASKS_DIR || code == CODE_TASKS_ITEM) {
            val appContext = context?.applicationContext ?: throw IllegalStateException()
            val taskDao: TaskDao = getTaskDao(appContext)

            val cursor: Cursor? = if (code == CODE_TASKS_DIR) {
                taskDao.selectAllTasksCursor()
            } else {
                taskDao.selectTaskById(ContentUris.parseId(uri))
            }
            cursor?.setNotificationUri(appContext.contentResolver, uri)
            cursor
        } else {
            throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        throw UnsupportedOperationException("Only reading operations are allowed")
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        throw UnsupportedOperationException("Only reading operations are allowed")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        throw UnsupportedOperationException("Only reading operations are allowed")
    }

    override fun getType(uri: Uri): String? {
        throw java.lang.UnsupportedOperationException("Only reading operations are allowed")
    }
}