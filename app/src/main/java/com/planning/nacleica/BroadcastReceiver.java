package com.planning.nacleica;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.planning.nacleica.CheckNetworkConnection.ConnectionNetworkCheck;
import com.planning.nacleica.WebInteractionService.WebRequestException;

import org.w3c.dom.Text;

public class BroadcastReceiver extends android.content.BroadcastReceiver {
    private View layout;

    @Override
    public void onReceive(Context context, Intent intent) {
        Activity activity = Utils.getActivity();

        try {

            ConnectionNetworkCheck.response connectionNetworkCheck = new ConnectionNetworkCheck.response() {
                @SuppressLint("NewApi")
                @Override
                public void processFinish(Boolean isConnected) {
                    WindowManager mWindowManager = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
                    LayoutInflater layoutManager = activity.getLayoutInflater();
                    layout = layoutManager.inflate(R.layout.check_network_layout, null);
                    if (!isConnected) {


                        TextView networkView = layout.findViewById(R.id.networkView);
                        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
                        params.gravity = Gravity.TOP;
                        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                        params.width = WindowManager.LayoutParams.MATCH_PARENT;
                        params.type = WindowManager.LayoutParams.TYPE_APPLICATION_PANEL;
                        mWindowManager.addView(layout, params);

                    } else {
                        mWindowManager.removeView(layout);
                        layout = null;
                    }
                }
            };
            new ConnectionNetworkCheck(connectionNetworkCheck).execute();

        } catch (NullPointerException e) {
            e.printStackTrace();

        }
    }
}
