package com.example.splitmoney;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserHolder> {
    private Context context;
    private List<User> userList;

    public UserAdapter(Context context, List<User> users){
        this.context = context;
        userList = users;
    }
    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        return new UserHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {
        User user = userList.get(position);
        holder.useremailTxt.setText(user.getUseremailTxt().toString());
        holder.usernameTxt.setText(user.getUsernameTxt().toString());

        String userexpenseTxtVal = user.getUserExpenseTxt();
        ArrayList<Integer> selectedItemsArr = user.getSelectedItems();

        float val = 0;

        if(Float.parseFloat(userexpenseTxtVal) >= 0){
            holder.userexpenseTxt.setTextColor(Color.GREEN);
            val = Float.parseFloat(userexpenseTxtVal);
        }else{
            holder.userexpenseTxt.setTextColor(Color.RED);
            val = -(Float.parseFloat(userexpenseTxtVal));
        }
        holder.userexpenseTxt.setText(String.valueOf(val));

        holder.linearLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public boolean onLongClick(View view) {
                Log.d("HEYYY", "THEREEE");
                holder.linearLayout.setBackgroundColor(R.color.black);
                selectedItemsArr.add(user.getUserId());
                return true;
            }
        });

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AllUserExpense.class);
                intent.putExtra("userId", user.getUserId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class UserHolder extends RecyclerView.ViewHolder{
        TextView useremailTxt, usernameTxt, userexpenseTxt;
        LinearLayout linearLayout;

        public UserHolder(@NonNull View itemView) {
            super(itemView);

            useremailTxt = itemView.findViewById(R.id.useremailTxt);
            usernameTxt = itemView.findViewById(R.id.usernameTxt);
            userexpenseTxt = itemView.findViewById(R.id.userexpenseTxt);
            linearLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
