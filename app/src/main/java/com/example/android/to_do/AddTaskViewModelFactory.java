package com.example.android.to_do;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.persistence.room.Database;

import com.example.android.to_do.Database.TodoDatabase;

public class AddTaskViewModelFactory extends ViewModelProvider.NewInstanceFactory{
    //  member variables. for the database and for the taskId
    private final TodoDatabase mDb;
    private final int mTaskId;

    // Initialize the member variables in the constructor with the parameters received
    public AddTaskViewModelFactory(TodoDatabase database, int taskId) {
        mDb = database;
        mTaskId = taskId;
    }


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new AddTaskViewModel(mDb, mTaskId);
    }

}
