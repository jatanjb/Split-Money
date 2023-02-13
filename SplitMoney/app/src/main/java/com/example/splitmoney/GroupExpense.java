package com.example.splitmoney;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GroupExpense extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_expense);

        EditText amt = (EditText)findViewById(R.id.amount);
        EditText desc = (EditText)findViewById(R.id.desc);
        Button addExpenseBtn = (Button)findViewById(R.id.addExpenseBtn);

        SessionManagement sessionManagement = new SessionManagement(GroupExpense.this);
        int userId = sessionManagement.getSession();

        Bundle extras = getIntent().getExtras();
        ArrayList<Integer> groupList = extras.getIntegerArrayList("groupList");

        addExpenseBtn.setOnClickListener((View.OnClickListener) new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                apicall(groupList, userId, Float.valueOf(amt.getText().toString()), desc.getText().toString());
            }
        });
    }

    public void apicall(ArrayList<Integer> oweId, int lendId, float amount, String description){
        String url = "https://1310-129-107-80-41.ngrok.io/expanse/addGroupExpanse";
        JSONObject reqPara = new JSONObject();
        try {
            //input your API parameters
            reqPara.put("oweID",oweId);
            reqPara.put("lendID",lendId);
            reqPara.put("amount",Float.valueOf(amount));
            reqPara.put("description",description);
            Log.d("PARA", String.valueOf(reqPara));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, reqPara,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.getString("status").equals("200")){
                                Toast.makeText(GroupExpense.this, "Expense added successfully", Toast.LENGTH_LONG).show();
                            }else{
                                Toast.makeText(GroupExpense.this, "Error while adding the expense", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(GroupExpense.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        Volley.newRequestQueue(this).add(request);
    }
}
