package com.example.ucdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    TextInputEditText email,password;
    TextView textView;
    SwitchCompat Switch;
    RadioButton r_cust,r_emp,r_admin;
    String url;
    boolean flag=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
      // Switch = findViewById(R.id.switch1);

        textView = findViewById(R.id.forgettext);
        email =  findViewById(R.id.email);
        password = findViewById(R.id.pass);
        r_cust = findViewById(R.id.radio_cust);
        r_emp = findViewById(R.id.radio_emp);
        r_admin = findViewById(R.id.radio_admin);
    }


    public void gotoforgetpass(View view)
    {
       startActivity(new Intent(getApplicationContext(),Forget_pass.class));
    }

    public void gotoregister(View view){
        startActivity(new Intent (getApplicationContext(),Register.class));
    }

    public void gotomain(View view) {

//        boolean state = Switch.isChecked();

//            SharedPreferences delete_pref = PreferenceManager.getDefaultSharedPreferences(this);

        String Email = email.getText().toString().trim();
        String Pass = password.getText().toString().trim();

        if (r_cust.isChecked() || r_emp.isChecked() || r_admin.isChecked()) {
            flag = true;
            
        } else{
            flag = false;
            Toast.makeText(this, "Please Select Login As:", Toast.LENGTH_SHORT).show();
        }
        if(flag){
            if (Email.equals("")) {
                Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show();
            } else if (Pass.equals("")) {
                Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
            } else {
//            Toast.makeText(getApplicationContext(),state+"",Toast.LENGTH_SHORT).show();

                if (true) {
                    url = "https://urbanclap1.000webhostapp.com/Customer/Login/verify_cred.php";
                } else {
                    url = "https://urbanclap1.000webhostapp.com/Employee/Login/verify_cred.php";
                }
                StringRequest request = new StringRequest(Request.Method.POST, url, response -> {

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("success");

                        if (success.equals("1")) {
                            Intent intent = new Intent(getApplicationContext(), nav_bar.class);// New activity
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();

                            Toast.makeText(this, "Login Successfully", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException je) {
                        je.printStackTrace();
                    }
                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();

                },
                        error -> Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show()
                ) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("password", Pass);
                        params.put("email", Email);
                        return params;
                    }
                };


                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(request);
            }
        }
    }
}