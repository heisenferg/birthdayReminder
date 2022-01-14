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

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class Alarma extends BroadcastReceiver {
    Calendar hoy= Calendar.getInstance();
    public int horaAlarma=hoy.get(Calendar.HOUR_OF_DAY);
    public int minutoAlarma=hoy.get(Calendar.MINUTE);
    public int diaDeHoy= hoy.get(Calendar.DAY_OF_MONTH);
    public int mesActual = hoy.get(Calendar.MONTH)+1;
    public String [] datos;
    String notificacion;

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
        Cursor c=MainActivity.db.rawQuery("SELECT Nombre, FechaNacimiento, Telefono, TipoNotif, Mensaje FROM MisCumples", null);

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("ddMM");

        if(c.getCount()==0)
            Log.d("FALLO", "No hay contactos guardados");
        else{
            while(c.moveToNext()) {
                if (c.getString(3).equals("S")) {
                    notificacion = "SMS";
                } else {
                    notificacion = "Notificación";
                }



                Log.d("Nombre: ", c.getString(0) + " Cumpleaños: " + c.getString(1)
                        + " Teléfono: " + c.getString(2) + " Tipo de notificación: " + notificacion
                        + " Mensaje: " + c.getString(4));

                String arrayFecha = c.getString(1);
                datos = arrayFecha.split("-");
                Log.d("CUMPLEAÑOS: ", "Dia " + Integer.parseInt(datos[2]) + " MesB " + datos[1]);

                // Elimino los ceros de delante para comparar fechas
                int numDIasincero = Integer.parseInt(datos[1]);
                Log.d("CUMPLEAÑOS: ", "DiaActual " + diaDeHoy + " MesACtual " + mesActual + " DATOS " + numDIasincero);



                if (Integer.parseInt(datos[1]) == mesActual && Integer.parseInt(datos[2])==diaDeHoy){
                    Log.d("Hay cumpleaños hoy", "Alarma de cumpleaños disparada correctamente.");

                }


            }
        }

        c.close();




        Log.d("FERNANDO", "Mes " +  " Día " + diaDeHoy);





    }

    public void listarGuardados(){
        ArrayAdapter<String> adaptador;
        List<String> lista = new ArrayList<String>();

    }






}
