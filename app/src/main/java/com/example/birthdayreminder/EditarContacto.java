package com.example.birthdayreminder;

import static com.example.birthdayreminder.placeholder.PlaceholderContent.*;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.birthdayreminder.placeholder.PlaceholderContent;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class EditarContacto extends AppCompatActivity implements View.OnClickListener {

    EditText nombre;
    EditText cumple;
    Spinner telefono;
    public static ArrayList tel = MainActivity.telefonos;
    ImageView foto;
    CheckBox notificacion;
    EditText mensaje;
    public final char SMS='S';
    public final char NOTIFICACION='N';
    Button guardar;
    int id;
    Button verContacto;
    public static final int SELECCIONADO=1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar_contacto);
        cargar();
        notificacion = findViewById(R.id.checkBoxNotificacion);
        verContacto = findViewById(R.id.ButtonverContacto);
        verContacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String proyeccion[]={ContactsContract.Contacts._ID,
                        ContactsContract.Contacts.DISPLAY_NAME,
                        ContactsContract.Contacts.LOOKUP_KEY,
                        ContactsContract.Contacts.HAS_PHONE_NUMBER,
                        ContactsContract.Contacts.PHOTO_ID};

                String filtro=ContactsContract.Contacts._ID + " like ?";

                String args_filtro[]={"%"+id+"%"};

                ArrayList<String> lista_contactos=new ArrayList<String>();
                ContentResolver cr = getContentResolver();
                Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                        proyeccion, filtro, args_filtro, null);

                if(cur.getCount()==SELECCIONADO) {
                    cur.moveToNext();

                    String lookupKey = cur.getString(2);

                    Uri contactUri = ContactsContract.Contacts.getLookupUri(id, lookupKey);
                    startActivity(new Intent(Intent.ACTION_VIEW, contactUri));
                }
            }

        });
        guardar = findViewById(R.id.buttonGuardar);
        guardar.setOnClickListener(this);
    }



    public void cargar(){
        //ID
        id = MainActivity.idContacto;
        //Nombre contacto
        nombre = findViewById(R.id.edNombre);
        nombre.setText(PlaceholderContent.nombre);
        // Fecha de nacimiento
        cumple = findViewById(R.id.edNacimiento);
        cumple.setText(MainActivity.cumple);
        //Teléfono
        telefono = findViewById(R.id.spinnerTelefonos);
        telefono.setAdapter(new ArrayAdapter<String>(EditarContacto.this, android.R.layout.simple_spinner_dropdown_item, tel));
        //Foto
        foto = findViewById(R.id.imageViewContacto);
        foto.setImageBitmap(MainActivity.contacto.foto);
        mensaje = findViewById(R.id.edMensaje);




        Log.d("ERROR ", id + " "+ SMS + " "+ mensaje.getText().toString() + " " + telefono.getSelectedItem().toString() +
                " " + cumple.getText().toString() + " " + nombre.getText().toString() );
    }

    @Override
    public void onClick(View v) {

        if (notificacion.isChecked()){
            addToDbSMS(v);
            Toast.makeText(getApplicationContext(), "Se enviará SMS.", Toast.LENGTH_SHORT).show();


        } else if (!notificacion.isChecked()){
            Toast.makeText(getApplicationContext(), "Se notificará en la barra de notificaciones.", Toast.LENGTH_SHORT).show();
            addToDbNotificacion(v);
        }



    }


    public void addToDbSMS(View v){
        MainActivity.db.execSQL("INSERT into miscumples VALUES(" + id + ", '"+SMS+"','" + mensaje.getText().toString()
                + "','" + telefono.getSelectedItem().toString() +
                "','" + cumple.getText().toString() + "','" + nombre.getText().toString() + "')");
        Toast.makeText(getApplicationContext(), "Se añadió " + nombre.getText().toString() + " correctamente.", Toast.LENGTH_SHORT).show();
    }

    public void addToDbNotificacion(View v){
        MainActivity.db.execSQL("INSERT into miscumples VALUES(" + id + ", '"+NOTIFICACION+"','" + mensaje.getText().toString()
                + "','" + telefono.getSelectedItem().toString() +
                "','" + cumple.getText().toString() + "','" + nombre.getText().toString() + "')");
        Toast.makeText(getApplicationContext(), "Se añadió " + nombre.getText().toString() + " correctamente.", Toast.LENGTH_SHORT).show();
    }
}