package com.jmgarzo.infomovies.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by jmgarzo on 14/08/2016.
 */
public class InfoMoviesAuthenticatorService extends Service {

    // Instance field that stores the authenticator object
    private InfoMoviesAuthenticator mAuthenticator;
    @Override
    public void onCreate() {
        // Create a new authenticator object
        mAuthenticator = new InfoMoviesAuthenticator(this);
    }
    /*
     * When the system binds to this Service to make the RPC call
     * return the authenticator's IBinder.
     */
    @Override
    public IBinder onBind(Intent intent) {
        return mAuthenticator.getIBinder();
    }
}
