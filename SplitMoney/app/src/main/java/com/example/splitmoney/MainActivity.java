package com.example.splitmoney;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RequestQueue requestQueue;
    private RecyclerView recyclerView;
    private List<User> userList;
    ArrayList<Integer> selectedItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Button signoutBtn = (Button)findViewById(R.id.signout);
        Button grpexpense = (Button)findViewById(R.id.grpexpense);
        requestQueue = VolleySingleton.getInstance(this).getRequestQueue();
        LinearLayout mainLayout = findViewById(R.id.mainLayout);
        TextView userexpenseTxtTV = findViewById(R.id.userexpenseTxt);

        userList = new ArrayList<>();

        fetchUsers(userexpenseTxtTV);

        grpexpense.setOnClickListener((View.OnClickListener)(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GroupExpense.class);
                intent.putExtra("groupList", selectedItems);
                startActivity(intent);
            }
        }));

        signoutBtn.setOnClickListener((View.OnClickListener)(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                SessionManagement sessionManagement = new SessionManagement(MainActivity.this);
                sessionManagement.removeSession();

                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        }));
    }

    private void fetchUsers(TextView userexpenseTxtTV){
        String url = "https://1310-129-107-80-41.ngrok.io/users/allUserList";

        SessionManagement sessionManagement = new SessionManagement(MainActivity.this);
        int userId = sessionManagement.getSession();

        JSONObject reqPara = new JSONObject();
        try {
            //input your API parameters
            reqPara.put("lendID",userId);
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
                                    int userId = jsonObject.getInt("id");
                                    String useremailTxt = jsonObject.getString("username");
                                    String usernameTxt = jsonObject.getString("firstName");
                                    String userexpenseTxt = jsonObject.getString("amount");
                                    Log.d("AT MAIN", userexpenseTxt);
                                    User user = new User(userId, useremailTxt, usernameTxt, userexpenseTxt, selectedItems);
                                    userList.add(user);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                UserAdapter adapter = new UserAdapter(MainActivity.this, userList);
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