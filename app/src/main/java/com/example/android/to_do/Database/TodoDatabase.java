package com.example.android.to_do.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

@Database(entities = {TaskEntry.class}, version = 1, exportSchema = false)
public abstract class TodoDatabase extends RoomDatabase {

    private static final String LOG_TAG = TodoDatabase.class.getSimpleName();

    private static final Object LOCK = new Object();

    private static final String DATABASE_NAME = "todolist";

    private static TodoDatabase sInstance;



    public static TodoDatabase getInstance(Context context) {

        if (sInstance == null) {

            synchronized (LOCK) {

                Log.d(LOG_TAG, "Creating new database instance");

                sInstance = Room.databaseBuilder(context.getApplicationContext(),

                        TodoDatabase.class, TodoDatabase.DATABASE_NAME)

                        // Queries should be done in a separate thread to avoid locking the UI
                        .build();
            }

        }

        Log.d(LOG_TAG, "Getting the database instance");

        return sInstance;

    }


    public abstract TaskDao taskDao();

}
