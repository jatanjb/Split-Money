package com.example.splitmoney;

import static java.lang.Boolean.getBoolean;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.AbstractCollection;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onStart() {
        super.onStart();
        SessionManagement sessionManagement = new SessionManagement(LoginActivity.this);
        int userId = sessionManagement.getSession();

        if (userId != -1){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        EditText emailid = (EditText)findViewById(R.id.emailid);
        EditText pssword = (EditText)findViewById(R.id.password);
        Button lgnbtn = (Button)findViewById(R.id.lgnbtn);
        TextView signupredirect = (TextView)findViewById(R.id.signupredirect);

        signupredirect.setOnClickListener((View.OnClickListener)(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        }));

        lgnbtn.setOnClickListener((View.OnClickListener)(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String username = emailid.getText().toString();
                String password = pssword.getText().toString();
                apicall(username, password);
            }
        }));
    }

    public void apicall(String username, String password){
        String url = "https://1310-129-107-80-41.ngrok.io/users/login";

        JSONObject reqPara = new JSONObject();

        try {
            //input your API parameters
            reqPara.put("username",username);
            reqPara.put("password",password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, reqPara,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.getJSONObject("response").getBoolean("loggedIn")){
                                SessionManagement sessionManagement = new SessionManagement(LoginActivity.this);
                                sessionManagement.saveSession(response.getJSONObject("response"));
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(LoginActivity.this, "Please Check your credentials", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        Volley.newRequestQueue(this).add(request);
    }
}
