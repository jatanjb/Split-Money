package com.example.splitmoney;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemHolder> {
    private Context context;
    private List<Item> itemList;

    public ItemAdapter(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.expense_list, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        Item item = itemList.get(position);
        holder.itemDesc.setText(item.getItemDesc().toString());
        holder.itemAmount.setText(item.getItemAmount());

        holder.expenseLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, individualExpense.class);
                intent.putExtra("userId", item.getUserId());
                intent.putExtra("itemId", item.getItemId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    public class ItemHolder extends RecyclerView.ViewHolder{
        TextView itemDesc, itemAmount;
        LinearLayout expenseLayout;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);

            itemDesc = itemView.findViewById(R.id.itemDesc);
            itemAmount = itemView.findViewById(R.id.itemAmount);
            expenseLayout = itemView.findViewById(R.id.expenseLayout);
        }
    }
}
