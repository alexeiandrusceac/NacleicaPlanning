package com.planning.nacleica;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.planning.nacleica.CheckNetworkConnection.ConnectionNetworkCheck;
import com.planning.nacleica.WebInteractionService.WebRequestException;

import org.w3c.dom.Text;

import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.view.WindowManager.LayoutParams.TYPE_APPLICATION_PANEL;
import static android.view.WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;

public class BroadcastReceiver extends android.content.BroadcastReceiver {
    private View layout;
    WindowManager mWindowManager;
    LayoutInflater layoutManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        Activity activity = Utils.getActivity();
        try {

            ConnectionNetworkCheck.response connectionNetworkCheck = new ConnectionNetworkCheck.response() {
                @SuppressLint({"NewApi", "WrongConstant", "ServiceCast"})
                @Override
                public void processFinish(Boolean isConnected) {
                    mWindowManager = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);

                    if (!isConnected) {

                        layoutManager = activity.getLayoutInflater();
                        layout = layoutManager.inflate(R.layout.check_network_layout, null);
                        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
                         params.gravity= Gravity.TOP;
                        params.height =  WindowManager.LayoutParams.WRAP_CONTENT;
                        params.width =  WindowManager.LayoutParams.MATCH_PARENT;
                        params.type =  WindowManager.LayoutParams.TYPE_APPLICATION_PANEL;

                        /*params.flags =  WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                                | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                                | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;*/

                      /*  WindowManager.LayoutParams params = new WindowManager.LayoutParams(

                                WindowManager.LayoutParams.TYPE_APPLICATION_PANEL,
                                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                                        | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                                        | WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH,
                                PixelFormat.TRANSLUCENT);
                        params.gravity = Gravity.TOP;
                        params.width = WindowManager.LayoutParams.MATCH_PARENT;
                        params.height = WindowManager.LayoutParams.MATCH_PARENT;
*/

                       /* final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                                TYPE_APPLICATION_PANEL |  ,
                                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
                                PixelFormat.TRANSLUCENT);*/
                        mWindowManager.addView(layout,params);

                    } else {
                        if (layout != null)
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
