package com.planning.nacleica;

import com.planning.nacleica.WebInteractionService.WebInteractionService;

public class Singleton {
    public static WebInteractionService WebApi = new WebInteractionService();

    public static void Initialize() {
        WebApi = new WebInteractionService();
        //Singleton.WebApi.setAuthKey(new Encryption().encrypt(mask, getDeviceInformationString()));
        Session.executeCheckNetworkConnection();
    }

    public static class Session {

        public static void executeCheckNetworkConnection() {
            TimerSingleton.initialize();
            TimerSingleton.start();
        }
    }
}
