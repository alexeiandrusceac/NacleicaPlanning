package com.planning.nacleica;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class CheckNetworkConnection extends AsyncTask<String, Integer, Boolean> {
    private static final int CONNECTION_TIMEOUT = 10 * 1000; // Seconds timeout in milliseconds

    private response delegate;

    public CheckNetworkConnection(response callback) {
        delegate = callback;
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        Boolean isConnected;
        HttpURLConnection connection = null;

        try {
            URL url = new URL(/*"http://smartv.moldtelecom.md/Prime/index.m3u8"*/"");
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(CONNECTION_TIMEOUT);
            int state = connection.getResponseCode();
            isConnected = state == HttpURLConnection.HTTP_OK || state == HttpURLConnection.HTTP_FORBIDDEN;
        } catch (Exception e) {
            Log.d("CheckNetworkConnection", e.getMessage());
            isConnected = false;
        } finally {
            try {
                InputStream is = (InputStream) (connection != null ? connection.getContent() : null);
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (connection != null)
                connection.disconnect();
        }

        return isConnected;
    }

    @Override
    protected void onPostExecute(Boolean isConnected) {
        Log.d("CheckNetworkConnection", "Network is: " + isConnected.toString());

        delegate.processFinish(isConnected);
    }

    public interface response {
        void processFinish(Boolean isConnected);
    }
}