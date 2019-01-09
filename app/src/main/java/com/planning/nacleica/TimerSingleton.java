package com.planning.nacleica;

import android.app.Activity;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

//import md.bass.androidtv.moldtelecomtv.APIInteraction.IEventActivityCustom;


public class TimerSingleton {

    private static final String JOB_NAME = "Timer task";
    private static final long JOB_START_DELAY = 0L;
    private static final long JOB_FREQUENCY = 10000L;

    private volatile static TimerSingleton timerSingleton;
    private volatile static boolean isRunning;
    private volatile static TimerTaskJob task;
    private volatile static TimerTaskJob timerTask;
    private volatile static Timer timer;

    private TimerSingleton() {
    }

    /**
     * Operate as a lazy singleton with synchronized locking.
     *
     * @return returns the singleton instance
     */
    public static TimerSingleton initialize() {

        if (timerSingleton == null) {

            synchronized (TimerSingleton.class) {

                if (timerSingleton == null) {

                    timerSingleton = new TimerSingleton();
                    task = timerSingleton.new TimerTaskJob();
                    isRunning = false;
                }
            }
        }

        return timerSingleton;
    }

    public static void start() {

        if (!isRunning) {

            isRunning = true;
            task.start();

        }
    }

    public static void stop() {

        if (isRunning) {

            isRunning = false;
            task.stop();

        }
    }

    public static boolean isRunning() {

        return isRunning;
    }


    public class TimerTaskJob extends TimerTask {


        public TimerTaskJob() {

        }

        @Override
        public void run() {


            doTask();
        }

        void start() {

            timerTask = new TimerTaskJob();
            timer = new Timer(true);
            timer.scheduleAtFixedRate(timerTask, JOB_START_DELAY, JOB_FREQUENCY);
        }

        void stop() {

            timerTask.cancel();
            timer.cancel();
        }

        private void doTask() {
            Activity act = Utils.getActivity();
            if(act != null) {
                Singleton.WebApi.CheckInternetConnection(internet -> {
                    act.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("@ TimerSingleton", act.getLocalClassName());
                            ((IEventActivityCustom) act).NetworkConnectionEvent(internet);
                        }
                    });
                });
            }

        }
    }

}