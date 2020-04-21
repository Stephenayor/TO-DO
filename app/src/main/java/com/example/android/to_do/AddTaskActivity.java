package com.example.android.to_do;

import android.arch.persistence.room.Database;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.example.android.to_do.Database.TaskEntry;
import com.example.android.to_do.Database.TodoDatabase;

public class AddTaskActivity extends AppCompatActivity {



        // Extra for the task ID to be received in the intent
        public static final String EXTRA_TASK_ID = "extraTaskId";

        // Extra for the task ID to be received after rotation
        public static final String INSTANCE_TASK_ID = "instanceTaskId";

        // Constant for default task id to be used when not in update mode

        private static final int DEFAULT_TASK_ID = -1;

        // Constant for logging
        private static final String TAG = AddTaskActivity.class.getSimpleName();

        // Fields for views
        EditText mTitle;
        EditText mEditText;
        Button mButton;


        private int mTaskId = DEFAULT_TASK_ID;

        //member variable for the Database
        private TodoDatabase mDb;

        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);

            setContentView(R.layout.activity_add_task);


            initViews();

            //Initializing member variable for the database
            mDb = TodoDatabase.getInstance(getApplicationContext());

            if (savedInstanceState != null && savedInstanceState.containsKey(INSTANCE_TASK_ID)) {

                mTaskId = savedInstanceState.getInt(INSTANCE_TASK_ID, DEFAULT_TASK_ID);

            }


            Intent intent = getIntent();

            if (intent != null && intent.hasExtra(EXTRA_TASK_ID)) {

                mButton.setText("Update");

                if (mTaskId == DEFAULT_TASK_ID) {

                    // populate the UI
                }
            }
        }

    @Override

    protected void onSaveInstanceState(Bundle outState) {

        outState.putInt(INSTANCE_TASK_ID, mTaskId);

        super.onSaveInstanceState(outState);

    }



    /**

     * initViews is called from onCreate to init the member variable views

     */

    private void initViews() {

        mTitle = findViewById(R.id.edit_task_title);
        mEditText = findViewById(R.id.edit_task_description);
        mButton = findViewById(R.id.saveButton);

        mButton.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View view) {

                onSaveButtonClicked();

            }

        });
    }
    /**

     * populateUI would be called to populate the UI when in update mode

     *

     * @param task the taskEntry to populate the UI

     */

    private void populateUI(TaskEntry task) {



    }
/**

 * onSaveButtonClicked is called when the "save" button is clicked.

 * It retrieves user input and inserts that new task data into the underlying database.

 */
public void onSaveButtonClicked() {

    //  Create a description variable and assign to it the value in the edit text
    String description = mEditText.getText().toString();

    // Create a title variable and assign to it the value in the edit text
    String title = mTitle.getText().toString();


    // COMPLETED (8) Create taskEntry variable using the variables defined above

    TaskEntry taskEntry = new TaskEntry(title, description);


    mDb.taskDao().insertTask(taskEntry);

    finish();

}
}