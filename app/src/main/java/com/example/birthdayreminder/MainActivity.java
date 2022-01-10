package com.example.birthdayreminder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public ArrayList<Contacto> miLista = new ArrayList<Contacto>();
    public MyContactoRecyclerViewAdapter miAdaptador;
    ListView listaContactos;
    SQLiteDatabase db;
    EditText contactos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        miAdaptador = new MyContactoRecyclerViewAdapter(miLista);
        setContentView(R.layout.activity_main);
        solicitarPermisos();
        contactos = findViewById(R.id.etNombres);
        Button buscar = findViewById(R.id.buttonBuscar);
        buscar.setOnClickListener(this);

     //   Base de datos.

        db = openOrCreateDatabase("Cumpleaños", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS miscumples(\n" +
                " ID integer,\n" +
                " TipoNotif char(1),\n" +
                " Mensaje VARCHAR(160),\n" +
                " Telefono VARCHAR(15),\n" +
                " FechaNacimiento VARCHAR(15),\n" +
                " Nombre VARCHAR(128)\n" +
                ");");



    }


    private void solicitarPermisos(){
        boolean permisos_concedidos=false;

     //   while (permisos_concedidos==false) {
            if (checkSelfPermission(Manifest.permission.READ_CONTACTS) !=
                    PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, 1);
                requestPermissions(new String[]{Manifest.permission.SEND_SMS}, 1);
            } else if (checkSelfPermission(Manifest.permission.READ_CONTACTS) ==
                    PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED){
                permisos_concedidos=true;
          //  }
        }
    }

    @SuppressLint("Range")
    @Override
    public void onClick(View v) {

        //Buscamos Contactos
        String proyeccion[]={ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts.HAS_PHONE_NUMBER,
                ContactsContract.Contacts.PHOTO_ID};

        // FROM
        Uri contactUri = ContactsContract.Contacts.CONTENT_URI;

        // WHERE ORDER BY
        EditText busqueda = findViewById(R.id.etNombres);
        String filtro = ContactsContract.Contacts.DISPLAY_NAME + " like ?";
        String argumentos_filtro[]= {"%" + busqueda.getText().toString() +"%"};
        ContentResolver cr = getContentResolver();
        Cursor misContactos = cr.query(contactUri, proyeccion, filtro, argumentos_filtro, null);


        //Consulta para teléfonos
        Uri contactos = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String [] proyecc = new String[] {
                ContactsContract.CommonDataKinds.Nickname._ID,
                ContactsContract.CommonDataKinds.Nickname.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.HAS_PHONE_NUMBER,
                ContactsContract.Contacts.PHOTO_ID,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
        };

        Cursor phoneCursor = cr.query(contactos,proyecc, filtro, argumentos_filtro, null);
String phone = null;
        int idContacto=0;
        String nombrecontacto=null;

        if (misContactos!=null && phoneCursor!=null){
            while(misContactos.moveToNext() && phoneCursor.moveToNext()){
                idContacto = misContactos.getInt(misContactos.getColumnIndex(ContactsContract.Contacts._ID));
                nombrecontacto = misContactos.getString(misContactos.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
               phone = phoneCursor.getString(4);
                // String telefono = misContactos.getString(misContactos.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                if (Integer.parseInt(misContactos.getString(
                        misContactos.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)))>0) {
                    Contacto contacto = new Contacto(idContacto, nombrecontacto, phone);
                    abrirFoto(contacto);
                    miLista.add(contacto);
                }
            }
            miAdaptador.notifyDataSetChanged();
            Log.d("Contacto: ", idContacto + ": " + nombrecontacto + ": " + phone);


        }



    }

    //Para abrir foto
    public void abrirFoto(Contacto contacto){
        Uri contactUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contacto.id);
        InputStream inputStream = ContactsContract.Contacts.openContactPhotoInputStream(getContentResolver(), contactUri,false);
        contacto.foto = BitmapFactory.decodeStream(inputStream);
    }
}