package com.example.android.to_do;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import com.example.android.to_do.Database.TaskEntry;
import com.example.android.to_do.Database.TodoDatabase;

import java.util.List;
import java.util.concurrent.Executors;

import static android.widget.LinearLayout.VERTICAL;

public class MainActivity extends AppCompatActivity implements TaskAdapter.ItemClickListener {


    private TaskAdapter mAdapter;
    private RecyclerView mRecyclerView;
    //Member variable for the database
    private TodoDatabase mDb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Set the Recyclerview to its corresponding view
        mRecyclerView = findViewById(R.id.rv_Tasks);


        //Set the layout for the recyclerview to be a linearlayout
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Initialize the adapter and attach it to the recycler view
        mAdapter = new TaskAdapter(this, this);
        mRecyclerView.setAdapter(mAdapter);


        DividerItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(decoration);

           /*
         Add a touch helper to the RecyclerView to recognize when a user swipes to delete an item.
         An ItemTouchHelper enables touch behavior (like swipe and move) on each ViewHolder,
         and uses callbacks to signal when a user is performing these actions.
         */
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            // Called when a user swipes left or right on a ViewHolder
            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int swipeDir) {
                // Get the diskIO Executor from the instance of TodoExecutors and
                // call the diskIO execute method with a new Runnable and implement its run method
                TodoExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        //  get the position from the viewHolder parameter
                        int position = viewHolder.getAdapterPosition();
                        List<TaskEntry> tasks = mAdapter.getTasks();
                        // Call deleteTask in the taskDao with the task at that position
                        mDb.taskDao().deleteTask(tasks.get(position));
                    }
                });
            }
        }).attachToRecyclerView(mRecyclerView);


        // This opens up the AddTaskActivity when the floating action button is clicked

        FloatingActionButton fabButton = findViewById(R.id.fab);
        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating an intent that starts AddTaskActivity

                Intent addTaskIntent = new Intent(MainActivity.this, AddTaskActivity.class);
                startActivity(addTaskIntent);
            }
        });
        mDb = TodoDatabase.getInstance(getApplicationContext());
        setupViewModel();
    }


    private void setupViewModel() {
        //  Declaring and initializing viewmodel by calling it ViewModelProviders.of
        TodoViewModel viewModel = ViewModelProviders.of(this).get(TodoViewModel.class);
        // Observe the Livedata object in the viewModel
        viewModel.getTasks().observe(this, new Observer<List<TaskEntry>>() {
            @Override
            public void onChanged(@Nullable List<TaskEntry> taskEntries) {

                mAdapter.setTasks(taskEntries);
            }
        });
    }



    @Override
    public void onItemClickListener(int itemId) {
        // Launch AddTaskActivity adding the itemId as an extra in the intent
        Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
        intent.putExtra(AddTaskActivity.EXTRA_TASK_ID, itemId );
        startActivity(intent);
    }
}