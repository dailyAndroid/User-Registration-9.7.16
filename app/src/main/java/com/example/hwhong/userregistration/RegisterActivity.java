package com.example.hwhong.userregistration;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText etUsername = (EditText) findViewById(R.id.username);
        final EditText etAge = (EditText) findViewById(R.id.age);
        final EditText etName = (EditText) findViewById(R.id.name);
        final EditText etPassword = (EditText) findViewById(R.id.password);

        final Button register = (Button) findViewById(R.id.registerButton);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = etName.getText().toString();
                final String username = etUsername.getText().toString();
                final String password = etPassword.getText().toString();
                final int age = Integer.parseInt(etAge.getText().toString());

                //everything here will only be executed if the the response is executed properly by
                //volley
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //converting the response into an JSON object as it was encoded as
                            //a JSON object in the php script
                            JSONObject jsonObject = new JSONObject(response);
                            //get the value of success from the php script
                            boolean success = jsonObject.getBoolean("success");
                            //only if the registration is successful will the user be taken to the
                            //login page
                            if(success) {
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                RegisterActivity.this.startActivity(intent);
                            } else {
                                AlertDialog.Builder dialog = new AlertDialog.Builder(RegisterActivity.this);
                                dialog.setMessage("Register Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show(); 
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                            builder.setMessage("Register Failed").setNegativeButton("Retry", null).create().show();
                        }
                    }
                };

                RegisterRequest registerRequest = new RegisterRequest(name, username, age, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);
            }
        });
    }
}
