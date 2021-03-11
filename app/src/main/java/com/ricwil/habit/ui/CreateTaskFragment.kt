package com.ricwil.habit.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.ricwil.habit.R
import com.ricwil.habit.data.TaskDataSource
import com.ricwil.habit.di.TaskKey
import com.ricwil.habit.navigator.AppNavigator
import com.ricwil.habit.navigator.Screens
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CreateTaskFragment : Fragment() {

    @TaskKey
    @Inject
    lateinit var taskSource: TaskDataSource

    @Inject
    lateinit var navigator: AppNavigator


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.create_task_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<Button>(R.id.create_task_button).setOnClickListener {
            val nameInput = view.findViewById<EditText>(R.id.create_new_task_name)
            if (nameInput.text != null) {
                taskSource.addTask(nameInput.text.toString(), false)
                navigator.navigateTo(Screens.TASKS)
            } else {
                nameInput.hint = "Please enter task name!"
            }
        }
    }



}