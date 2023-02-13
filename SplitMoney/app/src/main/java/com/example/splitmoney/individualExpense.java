package com.example.splitmoney;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class individualExpense extends AppCompatActivity {

    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.individual_expense);

        Button individualExpenseBtn = (Button)findViewById(R.id.individualExpenseBtn);

        Bundle extras = getIntent().getExtras();
        int intentUserId = extras.getInt("userId");
        int itemId = extras.getInt("itemId");

        individualExpenseBtn.setOnClickListener((View.OnClickListener)(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                settleUp(itemId);
            }
        }));
    }

    public void settleUp(int itemId){
        SessionManagement sessionManagement = new SessionManagement(individualExpense.this);
        int userId = sessionManagement.getSession();

        String url = "https://1310-129-107-80-41.ngrok.io/expanse/singleSettleExpanse";

        JSONObject reqPara = new JSONObject();

        try {
            //input your API parameters
            reqPara.put("id",itemId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i("Val", String.valueOf(reqPara));
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, reqPara,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("heyyyy", "HERE");
                            if(response.getString("status").equals("200")){
                                Toast.makeText(individualExpense.this, "Settle Up Successfully", Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(individualExpense.this, "Error while settling up the expense", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            Log.d("ERROR", e.getMessage());
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERRROR", error.getMessage());
            }
        });
        Volley.newRequestQueue(this).add(jsonObjectRequest);
    }

}
