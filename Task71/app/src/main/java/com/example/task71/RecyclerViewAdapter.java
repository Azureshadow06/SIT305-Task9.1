package com.example.task71;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.task71.data.DatabaseHelper;
import com.example.task71.model.User;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>
{//Declare a RecyclerViewAdapter to use RecyclerViewAdapter class  and ViewHolder in following code.
    private ArrayList<User> userList;
    private Context context;

    RecyclerViewAdapter(ArrayList<User> userList, Context context) {//Class constructor
        this.userList = userList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(context).inflate(R.layout.user_row, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position)
    {
        holder.rowNameTextView.setText("   "+userList.get(position).getUsername());
    }

    @Override
    public int getItemCount(){
        return userList.size();
    }//Get array size of database

    public class ViewHolder extends RecyclerView.ViewHolder{//Declare ViewHolder class to access each item in the list
        TextView rowNameTextView;

        Button deleteButton;
        public ViewHolder(@NonNull View itemView){

            super(itemView);
            rowNameTextView = itemView.findViewById(R.id.usernameTextView);

            deleteButton = itemView.findViewById(R.id.deleteButton);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {//Press button to share the position of user-selected item to ItemActivity
                    int position = getAdapterPosition();
                    Intent intent = new Intent(itemView.getContext(),ItemActivity.class);
                    intent.putExtra("position", position);
                    itemView.getContext().startActivity(intent);


                }
            });

        }
    }

}
