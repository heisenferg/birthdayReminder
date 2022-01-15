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
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class Alarma extends BroadcastReceiver {
    Calendar hoy = Calendar.getInstance();
    public int horaAlarma = hoy.get(Calendar.HOUR_OF_DAY);
    public int minutoAlarma = hoy.get(Calendar.MINUTE);
    public int diaDeHoy = hoy.get(Calendar.DAY_OF_MONTH);
    public int mesActual = hoy.get(Calendar.MONTH) + 1;
    public String[] datos;
    String notificacion;
    MainActivity mainActivity;

    // Para probar si se envía correctamente con mis datos controlados.
    public static String telefono="693438334", mensaje="Felicidades desde ALARMA";


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

        Log.d("PROBANDO", "Funciona sin cerrar");
        alarma();
        mainActivity.enviarNotificacion(telefono);

        Log.d("PROBANDO", "Se cierra alarma.");
    }


    String arrayFecha;
    int numDiaSinCero;
    int numMesSinCero;

    public void alarma() {

        Cursor c = MainActivity.db.rawQuery("SELECT Nombre, FechaNacimiento, Telefono, TipoNotif, Mensaje FROM MisCumples", null);

        if (c.getCount() == 0)
            Log.d("FALLO", "No hay contactos guardados");
        else {
            while (c.moveToNext()) {
                if (c.getString(3).equals("S")) {
                    notificacion = "SMS";
                } else {
                    notificacion = "Notificación";
                }


                Log.d("Nombre: ", c.getString(0) + " Cumpleaños: " + c.getString(1)
                        + " Teléfono: " + c.getString(2) + " Tipo de notificación: " + notificacion
                        + " Mensaje: " + c.getString(4));

                arrayFecha = c.getString(1);
                datos = arrayFecha.split("-");
                Log.d("CUMPLEAÑOS: ", "Dia " + Integer.parseInt(datos[2]) + " MesB " + datos[1]);

                // Elimino los ceros de delante para comparar fechas

                 numDiaSinCero = Integer.parseInt(datos[2]);
                 numMesSinCero = Integer.parseInt(datos[1]);
                Log.d("CUMPLEAÑOS: ", "DiaActual " + diaDeHoy + " MesACtual " + mesActual + " DATOS dia / mes " + numDiaSinCero + "/" + numMesSinCero);


                if (numMesSinCero == mesActual && numDiaSinCero == diaDeHoy) {
                    Log.d("Hay cumpleaños hoy", "Alarma de cumpleaños disparada correctamente. Hace los años " + c.getString(0));
                    if (c.getString(3).equals("S")){
                        String telefono2 = c.getString(2).replace(" ", "");
                        String mensaje2 = c.getString(4);
                        Log.d("EnvioSMS", "ENviar...a " + telefono);
                        try{
                            enviarSms(telefono2, mensaje2);
                        } catch (Exception e){
                            e.printStackTrace();
                        }
                    } else {
                        mainActivity.enviarNotificacion(telefono);
                    }
                    Log.d("FALLANDO", "Entra aquí");
                    break;
                }


            }

        }

      //  c.close();
    }



    public void enviarSms(String telefono, String mensaje){
        Log.d("SMS", "Llamada al método");
        try{
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(telefono,null,mensaje,null,null);
        } catch (Exception e){
            e.getStackTrace();
        }
    }


}