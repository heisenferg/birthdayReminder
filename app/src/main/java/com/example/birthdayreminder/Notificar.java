package com.example.birthdayreminder;

import android.content.Context;
import android.telephony.SmsManager;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Notificar extends AppCompatActivity {

    public String telefono;
    public String mensaje;

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public void enviarSms(String telefono, String mensaje){
        Log.d("SMS", "Llamada al método");


        try{
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(telefono,null,mensaje,null,null);
            Toast.makeText(getApplicationContext(), "Mensaje enviado", Toast.LENGTH_SHORT).show();
        } catch (Exception e){
            Toast.makeText(getApplicationContext(), "SMS no enviado, inténtelo de nuevo", Toast.LENGTH_SHORT).show();
            e.getStackTrace();
        }
    }
}
