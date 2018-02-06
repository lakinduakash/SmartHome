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

        mainSwitch = findViewById(R.id.button);//Toggle button of the light
        urlEdit = findViewById(R.id.editText);//Url field
        textView = findViewById(R.id.textView);//Status area
        //set onClick listener to button with toggle the states of bulb
        mainSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle();
            }
        });
    }


    public void toggle() {

        String url; //url

        //switch the bulb according to current state.
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
        //Request a queue to send HTTP request
        RequestQueue queue = Volley.newRequestQueue(this);

        //Handle the request
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        textView.setText(curState+response);
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

        //add the request to queue.
        queue.add(stringRequest);
    }


}
