package com.planning.nacleica.CheckNetworkConnection;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConnectionNetworkCheck extends AsyncTask<String, Integer, Boolean> {
    private static final int CONNECTION_TIMEOUT = 10 * 1000; // Seconds timeout in milliseconds
    response delegate;
    private String URL_SERVICE = "https://google.com";

    public ConnectionNetworkCheck(response callBack)
    {
        delegate = callBack;
    }

    @Override
    protected Boolean doInBackground(String... strings) {
        Boolean isConnected;
        HttpURLConnection connection = null;

        try {
            URL url = new URL (URL_SERVICE);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(CONNECTION_TIMEOUT);
            int state = connection.getResponseCode();
            isConnected = state == HttpURLConnection.HTTP_OK || state == HttpURLConnection.HTTP_FORBIDDEN;
        } catch (Exception ex) {
            Log.d("CheckNetworkConnection", ex.getMessage());
            isConnected = false;
        }
        finally {
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
