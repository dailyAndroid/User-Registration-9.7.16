package com.example.hwhong.userregistration;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hwhong on 7/8/16.
 */
public class RegisterRequest extends StringRequest {
    //make a request to register.php and get response as a string

    private static final String regsiterRequestUrl = "http://www.dinner4four.comli.com/Register.php";
    private Map<String, String> params;

    public RegisterRequest(String name, String username, int age, String password, Response.Listener<String> listener) {
        super(Method.POST, regsiterRequestUrl, listener, null);
        params = new HashMap<>();
        params.put("name", name);
        params.put("username", username);
        params.put("password", password);
        params.put("age", age + "");
    }

    @Override
    public Map<String, String> getParams() {
         return params;
    }
}
