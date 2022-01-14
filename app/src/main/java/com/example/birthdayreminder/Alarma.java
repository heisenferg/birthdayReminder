package com.example.birthdayreminder;


import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class Alarma extends BroadcastReceiver {
    Calendar hoy= Calendar.getInstance();
    public int horaAlarma=hoy.get(Calendar.HOUR_OF_DAY);
    public int minutoAlarma=hoy.get(Calendar.MINUTE);
    public int diaDeHoy= hoy.get(Calendar.DAY_OF_MONTH);
    public int mesActual = hoy.get(Calendar.MONTH);


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
        Log.d("FERNANDO", "Alarma de cumpleaños disparada correctamente");
        Log.d("FERNANDO", "LISTADOCOFIGURADO: " + ListadoConfigurado.dia[1]);
        Log.d("FERNANDO", "Mes " + mesActual);

        if (ListadoConfigurado.dia[1].equals(mesActual) && ListadoConfigurado.dia[2].equals(diaDeHoy)){
                Log.d("Cumpleaños", "Alarma de cumpleaños disparada correctamente.");

            }


    }






}
