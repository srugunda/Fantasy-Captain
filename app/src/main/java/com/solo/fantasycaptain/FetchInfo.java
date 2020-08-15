package com.solo.fantasycaptain;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class FetchInfo extends AsyncTask<String, Void, String> {
    private WeakReference<TextView> mResponse;

    FetchInfo(TextView response){
        this.mResponse = new WeakReference<>(response);
    }

    @Override
    protected String doInBackground(String... strings) {
        return NetworkUtils.getTeamInfo(strings[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        Log.d("Again the string is ", s);
    }
}
