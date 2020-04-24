package com.example.android.to_do;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.android.to_do.Database.TodoDatabase;

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
        mRecyclerView .setLayoutManager(new LinearLayoutManager(this));

        //Initialize the adapter and attach it to the recycler view
        mAdapter = new TaskAdapter(this, this);
        mRecyclerView.setAdapter(mAdapter);


        DividerItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), VERTICAL);
        mRecyclerView.addItemDecoration(decoration);


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
    }

    /**
     * This method is called after this activity has been paused or restarted.
     * Often, this is after new data has been inserted through an AddTaskActivity,
     * so this re-queries the database data for any changes.
     */
    @Override
    protected void onResume() {
        super.onResume();
        // Call the adapter's setTasks method using the result
        // of the loadAllTasks method from the taskDao
        mAdapter.setTasks(mDb.taskDao().loadAllTasks());
    }


        //Launch AddTaskActivity adding the itemId as an extra in the intent
    @Override
    public void onItemClickListener(int itemId) {

    }
}
