package com.example.birthdayreminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

public class Util {

    Alarma alarma = new Alarma();

    public void setAlarma(int i, Long timestamp, Context ctx){
        AlarmManager alarmManager;
        PendingIntent alarmIntent;

        Calendar calendario = Calendar.getInstance();
        calendario.setTimeInMillis(System.currentTimeMillis());
        calendario.set(Calendar.DAY_OF_MONTH, Integer.parseInt(ListadoConfigurado.dia[2]));
        calendario.set(Calendar.MONTH, Integer.parseInt(ListadoConfigurado.dia[1]));
        calendario.set(Calendar.HOUR_OF_DAY, alarma.getHoraAlarma());
        calendario.set(Calendar.MINUTE, alarma.getMinutoAlarma());

        Log.d("ERROR: ", "Lee hasta aquí. Día " + Integer.parseInt(ListadoConfigurado.dia[2]) + " mes: " + Integer.parseInt(ListadoConfigurado.dia[1]) + " hora y minuto: " + alarma.horaAlarma + " " + alarma.minutoAlarma);

        Intent intent2 = new Intent(ctx, Alarma.class);
        alarmIntent = PendingIntent.getBroadcast(ctx,i,intent2, PendingIntent.FLAG_NO_CREATE);

        alarmManager = (AlarmManager) ctx.getSystemService(ctx.ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, timestamp,
                AlarmManager.INTERVAL_DAY, alarmIntent);


    }
}
