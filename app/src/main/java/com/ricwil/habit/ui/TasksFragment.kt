package com.ricwil.habit.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.ricwil.habit.R
import com.ricwil.habit.data.Task
import com.ricwil.habit.data.TaskDataSource
import com.ricwil.habit.di.TaskKey
import com.ricwil.habit.navigator.AppNavigator
import com.ricwil.habit.navigator.Screens
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

// Need to add ViewModel and Adapter layers

@AndroidEntryPoint
class TasksFragment : Fragment() {

    @TaskKey
    @Inject lateinit var taskSource: TaskDataSource

    @Inject lateinit var navigator: AppNavigator

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.tasks_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<Button>(R.id.new_task_button).setOnClickListener {
            navigator.navigateTo(Screens.CREATE_TASK)
        }

        view.findViewById<Button>(R.id.delete_tasks_button).setOnClickListener {
            taskSource.removeTasks()
            onResume()
        }

        recyclerView = view.findViewById<RecyclerView>(R.id.tasks_recycler_view).apply {
            setHasFixedSize(true)
        }
    }

    override fun onResume() {
        super.onResume()

        taskSource.getAllTasks { tasks ->
            recyclerView.adapter = TasksViewAdapter(tasks)
        }
    }
}

private class TasksViewAdapter( private val tasksDataSet: List<Task>) : RecyclerView.Adapter<TasksViewAdapter.TasksViewHolder>() {
    class TasksViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksViewHolder {
        return TasksViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.task_row_item, parent, false) as TextView
        )
    }

    override fun getItemCount(): Int {
        return tasksDataSet.size
    }

    override fun onBindViewHolder(holder: TasksViewHolder, position: Int) {
        val task = tasksDataSet[position]
        holder.textView.text = task.name
    }
}