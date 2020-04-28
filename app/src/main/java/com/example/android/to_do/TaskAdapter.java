package com.example.android.to_do;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.to_do.Database.TaskEntry;

import java.util.List;

//The TaskAdapter binds data to a Viewholder
public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder>{

    //Member variable to handle itemclicks
    final private ItemClickListener mItemClickListener;
    // class variables for the list that hold task data and the Context
     private List<TaskEntry> mTaskEntries;
     private Context mContext;

    /**
     * Constructor for the TaskAdapter that initializes the Context.
     *  @param context  the current Context
     * @param listener the ItemClickListener
     */

    public TaskAdapter(Context context, ItemClickListener listener) {
        mItemClickListener = listener;
        mContext = context;
    }

    /**
     * Called when ViewHolders are created to fill a RecyclerView.
     *
     * @return A new TaskViewHolder that holds the view for each task
     */

    @NonNull
      @Override
      public TaskAdapter.TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the task_layout to a view
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.task_layout, parent, false);

        return new TaskViewHolder(view);

      }

    /**
     * Called by the RecyclerView to display data at a specified position in the Cursor.
     *
     * @param holder   The ViewHolder to bind Cursor data to
     * @param position The position of the data in the Cursor
     */

      @Override
      public void onBindViewHolder(@NonNull TaskAdapter.TaskViewHolder holder, int position) {
          // Determine the values of the wanted data
          TaskEntry taskEntry = mTaskEntries.get(position);
          String description = taskEntry.getDescription();
          String title = taskEntry.getTitle();

          //Set values
          holder.taskDescriptionView.setText(description);
          holder.taskTitleView.setText(title);

      }

      // Returns the number of item to display
      @Override
      public int getItemCount() {
          if (mTaskEntries == null) {
              return 0;
          }
          return mTaskEntries.size();
      }

      public List<TaskEntry> getTasks(){
          return mTaskEntries;
      }

    /**
     * When data changes, this method updates the list of taskEntries
     * and notifies the adapter to use the new values on it
     */
    public void setTasks(List<TaskEntry> taskEntries) {
        mTaskEntries = taskEntries;
        notifyDataSetChanged();
    }


    public interface ItemClickListener {
        void onItemClickListener(int itemId);
    }


  //  Inner Class For Creating ViewHolder
   class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
      //Variables for the task title and description
      TextView taskTitleView;
      TextView taskDescriptionView;

      // Constructor for the TaskViewHolder
      public TaskViewHolder(View itemView) {
          super(itemView);

          taskTitleView = itemView.findViewById(R.id.taskTitle);
          taskDescriptionView = itemView.findViewById(R.id.taskDescription);
          itemView.setOnClickListener(this);
      }


      @Override
      public void onClick(View v) {
          int elementId = mTaskEntries.get(getAdapterPosition()).getId();
          mItemClickListener.onItemClickListener(elementId);

      }
  }}