package com.example.birthdayreminder;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Alarma extends BroadcastReceiver {
    public int horaAlarma;
    public int minutoAlarma;

    public void setHoraAlarma(int horaAlarma) {
        this.horaAlarma = horaAlarma;
    }

    public void setMinutoAlarma(int minutoAlarma) {
        this.minutoAlarma = minutoAlarma;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("Cumpleaños", "Alarma de cumpleaños disparada correctamente.");
    }


/*
    public void setAlarma(String diaAlarma, String mesAlarma){
        AlarmManager alarmManager;
        PendingIntent alarmIntent;

        Calendar calendario = Calendar.getInstance();
        calendario.setTimeInMillis(System.currentTimeMillis());
        calendario.set(Calendar.DAY_OF_MONTH, Integer.parseInt(diaAlarma));
        calendario.set(Calendar.MONTH, Integer.parseInt(mesAlarma));
        calendario.set(Calendar.HOUR_OF_DAY, horaAlarma);
        calendario.set(Calendar.MINUTE, minutoAlarma);

        Intent intent = new Intent()


    }
*/



}
