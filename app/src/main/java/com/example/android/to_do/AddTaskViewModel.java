package com.example.android.to_do;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.persistence.room.Database;

import com.example.android.to_do.Database.TaskEntry;
import com.example.android.to_do.Database.TodoDatabase;

public class AddTaskViewModel extends ViewModel{
    private LiveData<TaskEntry> task;

    //  The constructor should receive the database and the taskId
    public AddTaskViewModel(TodoDatabase database, int taskId) {
        task = database.taskDao().loadTaskById(taskId);
    }

    //  getter for the task variable
    public LiveData<TaskEntry> getTask() {
        return task;
    }

}
