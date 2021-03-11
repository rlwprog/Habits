package com.ricwil.habit.navigator

import androidx.fragment.app.FragmentActivity
import com.ricwil.habit.R
import com.ricwil.habit.ui.CreateTaskFragment
import com.ricwil.habit.ui.TasksFragment
import javax.inject.Inject

class AppNavigatorImpl @Inject constructor(private val activity: FragmentActivity) : AppNavigator {

    override fun navigateTo(screen: Screens) {
        val fragment = when(screen) {
            Screens.TASKS -> TasksFragment()
            Screens.CREATE_TASK -> CreateTaskFragment()
        }

        activity.supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, fragment)
                .addToBackStack(fragment::class.java.canonicalName)
                .commit()
    }
}