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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_activity);
        Boolean flag = false;

        EditText emailid = (EditText)findViewById(R.id.emailid);
        EditText fname = (EditText)findViewById(R.id.fname);
        EditText lname = (EditText)findViewById(R.id.lname);
        EditText password = (EditText)findViewById(R.id.password);
        EditText reenterpassword = (EditText)findViewById(R.id.reenterpassword);
        Button signupbtn = (Button)findViewById(R.id.signupbtn);
        TextView signupredirect = (TextView)findViewById(R.id.signupredirect);

        if(password.getText().toString().equals(reenterpassword.getText().toString())){
            flag = true;
        }
        Boolean finalFlag = flag;
        signupbtn.setOnClickListener((View.OnClickListener)(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Log.d("FLAG", emailid.getText().toString());
                if(finalFlag){
                    apicall(emailid.getText().toString(), fname.getText().toString(), lname.getText().toString(), password.getText().toString());
                }
            }
        }));
    }

    public void apicall(String username, String firstname, String lastname, String password){
        String url = "https://1310-129-107-80-41.ngrok.io/users/register";
        JSONObject reqPara = new JSONObject();

        try {
            //input your API parameters
            reqPara.put("username",username);
            reqPara.put("firstname",firstname);
            reqPara.put("lastname",lastname);
            reqPara.put("password",password);

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
                                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(SignUpActivity.this, "Not able to sign up this time", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(SignUpActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        Volley.newRequestQueue(this).add(request);
    }

}
