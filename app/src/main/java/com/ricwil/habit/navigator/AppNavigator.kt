package com.ricwil.habit.navigator

enum class Screens {
    TASKS,
    CREATE_TASK
}

interface AppNavigator {
    fun navigateTo(screen: Screens)
}