package com.lakinduakash.smarthome;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {

    Button mainSwitch;
    EditText urlEdit;
    TextView textView;
    String curState = "";
    static final String[] STATES = {"/LED=ON", "/LED=OFF"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainSwitch = findViewById(R.id.button);
        urlEdit = findViewById(R.id.editText);
        textView = findViewById(R.id.textView);
        mainSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle();
            }
        });
    }

    public void toggle() {

        String url;
        if (curState.equals(STATES[0])) {
            url = urlEdit.getText().toString().trim() + STATES[1];
            curState = STATES[1];
        } else if (curState.equals(STATES[1])) {
            url = urlEdit.getText().toString().trim() + STATES[0];
            curState = STATES[0];
        } else {
            url = urlEdit.getText().toString().trim() + STATES[0];
            curState = STATES[0];
        }
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        textView.setText(curState+response);
                        //mainSwitch.setText(curState);
                    }
                },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    textView.setText("Error: " + error.networkResponse.statusCode);
                }
                catch (Exception e)
                {
                    textView.setText("Check Address ");
                }

            }
        });

        queue.add(stringRequest);
    }


}
