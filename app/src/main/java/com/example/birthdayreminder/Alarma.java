package com.example.birthdayreminder;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Alarma extends BroadcastReceiver {
    public int horaAlarma;
    public int minutoAlarma;

    public int getHoraAlarma() {
        return horaAlarma;
    }

    public int getMinutoAlarma() {
        return minutoAlarma;
    }

    public void setHoraAlarma(int horaAlarma) {
        this.horaAlarma = horaAlarma;
    }

    public void setMinutoAlarma(int minutoAlarma) {
        this.minutoAlarma = minutoAlarma;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {

            Intent service1 = new Intent(context, MainActivity.class);
            service1.setData((Uri.parse("custom://" + System.currentTimeMillis())));
            ContextCompat.startForegroundService(context, service1 );
            Log.d("Cumpleaños", "Alarma de cumpleaños disparada correctamente.");
        }
        Intent service1 = new Intent(context, MainActivity.class);
        service1.setData((Uri.parse("custom://" + System.currentTimeMillis())));
        ContextCompat.startForegroundService(context, service1 );
        Log.d("Cumpleaños", "Alarma de cumpleaños disparada correctamente.");


    }






}