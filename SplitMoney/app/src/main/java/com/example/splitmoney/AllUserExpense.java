package com.example.splitmoney;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AllUserExpense extends AppCompatActivity {

    private RequestQueue requestQueue;
    private RecyclerView recyclerView;
    private List<Item> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_user_expenses);

        recyclerView = (RecyclerView)findViewById(R.id.expenseRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Button expenseBtn = (Button)findViewById(R.id.expenseBtn);
        Button settleUpBtn = (Button)findViewById(R.id.settleUpBtn);
        requestQueue = VolleySingleton.getInstance(this).getRequestQueue();

        Bundle extras = getIntent().getExtras();
        int intentUserId = extras.getInt("userId");

        itemList = new ArrayList<>();

        fetchExpenses(intentUserId);

        expenseBtn.setOnClickListener((View.OnClickListener)(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AllUserExpense.this, AddExpense.class);
                intent.putExtra("userId", intentUserId);
                startActivity(intent);
            }
        }));

        settleUpBtn.setOnClickListener((View.OnClickListener)(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                settleUp(intentUserId);
            }
        }));
    }

    public void settleUp(int intentUserId){
        SessionManagement sessionManagement = new SessionManagement(AllUserExpense.this);
        int userId = sessionManagement.getSession();

        String url = "https://1310-129-107-80-41.ngrok.io/expanse/settleExpanse";

        JSONObject reqPara = new JSONObject();
        try {
            //input your API parameters
            reqPara.put("lendID",userId);
            reqPara.put("oweID",intentUserId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, reqPara,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.getString("status").equals("200")){
                                Toast.makeText(AllUserExpense.this, "Settle Up Successfully", Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(AllUserExpense.this, "Error while settling up the expense", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERRROR", error.getMessage());
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    public void fetchExpenses(int intentUserId){
        String url = "https://1310-129-107-80-41.ngrok.io/expanse/searchExpanseByLendId";

        SessionManagement sessionManagement = new SessionManagement(AllUserExpense.this);
        int userId = sessionManagement.getSession();

        JSONObject reqPara = new JSONObject();
        try {
            //input your API parameters
            reqPara.put("lendID",userId);
            reqPara.put("oweID",intentUserId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, reqPara,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("response");

                            for(int i = 0; i < jsonArray.length(); i++){
                                Log.d("REPONSE", String.valueOf(jsonArray.getJSONObject(i)));
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                try {
                                    JSONObject jsonObject1 = jsonObject.getJSONObject("oweID");
                                    int userId = jsonObject1.getInt("id");
                                    int itemId = jsonObject.getInt("id");
                                    String description = jsonObject.getString("description");
                                    String amount = jsonObject.getString("amount");

                                    Item item = new Item(userId, itemId, amount, description);
                                    itemList.add(item);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                ItemAdapter adapter = new ItemAdapter(AllUserExpense.this, itemList);
                                recyclerView.setAdapter(adapter);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERRROR", error.getMessage());
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

}
