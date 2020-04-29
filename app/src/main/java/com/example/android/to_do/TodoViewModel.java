package com.example.android.to_do;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.android.to_do.Database.TaskEntry;
import com.example.android.to_do.Database.TodoDatabase;

import java.util.List;

public class TodoViewModel extends AndroidViewModel {
    //For Logging
    private static final String TAG = TodoViewModel.class.getSimpleName();

    private LiveData<List<TaskEntry>> tasks;
    public TodoViewModel(@NonNull Application application) {
        super(application);
        TodoDatabase database = TodoDatabase.getInstance(this.getApplication());
        Log.d(TAG, "Actively retrieving the tasks from the database");
        tasks = database.taskDao().loadAllTasks();
    }
    public LiveData<List<TaskEntry>> getTasks(){
        return  tasks;
    }
}
