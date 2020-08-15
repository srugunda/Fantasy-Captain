package com.solo.fantasycaptain;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    EditText Team_name;
    TextView mResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Team_name = findViewById(R.id.team_name);
        mResponse = findViewById(R.id.response);
    }

    public void GetInfo(View view) {
        String queryString = Team_name.getText().toString();

        //this code hides the keyboard after user has clicked the button
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        if (inputManager != null ) {
            inputManager.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }

        //check if there is connectivity for the query
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connMgr != null) { //if there connectivity is working, set network Info
            networkInfo = connMgr.getActiveNetworkInfo();
        }

        if (networkInfo != null && networkInfo.isConnected()
                && queryString.length() != 0) {
            new FetchInfo(mResponse).execute(queryString);
            mResponse.setText(R.string.loading);
        } else {
            if (queryString.length() == 0) {
                mResponse.setText(R.string.no_search_term);
            } else {
                mResponse.setText(R.string.no_network);
            }
        }

    }
}
