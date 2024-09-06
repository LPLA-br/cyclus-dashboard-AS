package com.example.cyclusdashboard.ederdoski.simpleble.utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

public class Permissions {

    public static boolean checkPermisionStatus(Activity act, String permission) {
        // return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && act.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                checkSelfPermission(Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.BLUETOOTH_CONNECT
                }, 1);
            }
        }
    }
}
