package com.ricwil.habit.di

import com.ricwil.habit.data.TaskDataSource
import com.ricwil.habit.data.TaskLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
annotation class TaskKey

@InstallIn(ApplicationComponent::class)
@Module
abstract class TaskDatabaseModule {

    @TaskKey
    @Singleton
    @Binds
    abstract fun bindTaskKey(impl: TaskLocalDataSource): TaskDataSource

}