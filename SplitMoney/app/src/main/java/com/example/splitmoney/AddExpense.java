package com.example.splitmoney;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AddExpense extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_expense);

        EditText amt = (EditText)findViewById(R.id.amount);
        EditText desc = (EditText)findViewById(R.id.desc);
        Button addExpenseBtn = (Button)findViewById(R.id.addExpenseBtn);

        SessionManagement sessionManagement = new SessionManagement(AddExpense.this);
        int userId = sessionManagement.getSession();

        Bundle extras = getIntent().getExtras();
        int intentUserId = extras.getInt("userId");
        Log.d("Uwndkw", String.valueOf(intentUserId));


        addExpenseBtn.setOnClickListener((View.OnClickListener) new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                apicall(intentUserId, userId, Float.valueOf(amt.getText().toString()), desc.getText().toString());
            }
        });
    }

    public void apicall(int oweId, int lendId, float amount, String description){
        String url = "https://1310-129-107-80-41.ngrok.io/expanse/add";
        JSONObject reqPara = new JSONObject();
        try {
            //input your API parameters
            Log.d("ID1", String.valueOf(oweId));
            Log.d("ID2", String.valueOf(lendId));
            reqPara.put("oweID",oweId);
            reqPara.put("lendID",lendId);
            reqPara.put("amount",amount);
            reqPara.put("description",description);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, reqPara,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("RESPONSE", response.toString());
                        try {
                            if(response.getString("status").equals("200")){
                                Toast.makeText(AddExpense.this, "Expense added successfully", Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(AddExpense.this, "Error while adding the expense", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(AddExpense.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        Volley.newRequestQueue(this).add(request);
    }
}
