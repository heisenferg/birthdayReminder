package com.example.birthdayreminder;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Binder;
import android.provider.ContactsContract;
import android.renderscript.ScriptGroup;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;

public class AsyncTasks extends AsyncTask {

    @Override
    protected Object doInBackground(Object[] objects) {
        return null;
    }

    //Para abrir foto
    public void abrirFoto(Contacto contacto, Context context){
        Uri contactUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contacto.id);
        InputStream inputStream = ContactsContract.Contacts.openContactPhotoInputStream(context.getContentResolver(), contactUri,false);
        contacto.foto = BitmapFactory.decodeStream(inputStream);
    }

    public String getTelefono(int id, Context context) {
        String telefono = new String();

        // FROM
        Uri contactUri = ContactsContract.Contacts.CONTENT_URI;

        EditText busqueda = findViewById(R.id.etNombres);
        String filtro = ContactsContract.Contacts.DISPLAY_NAME + " like ?";
        String argumentos_filtro[]= {"%" + busqueda.getText().toString() +"%"};
        ContentResolver cr = context.getContentResolver();



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
        return telefono;
    }



    //Fecha de nacimiento

    @SuppressLint("Range")
    public String getCumpleaños(int identificador, Context context){
        String fecha=new String();
        Uri uri = ContactsContract.Data.CONTENT_URI;
        String[] proyeccion = new String[] {
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Event.CONTACT_ID,
                ContactsContract.CommonDataKinds.Event.START_DATE
        };
        String filtro =
                ContactsContract.Data.MIMETYPE + "= ? AND " +
                        ContactsContract.CommonDataKinds.Event.TYPE + "=" +
                        ContactsContract.CommonDataKinds.Event.TYPE_BIRTHDAY + " AND " +
                        ContactsContract.CommonDataKinds.Event.CONTACT_ID + " = ? ";
        String[] argsFiltro = new String[] {
                ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE,
                String.valueOf(identificador)
        };
        Cursor c=context.getContentResolver().query(uri,proyeccion,filtro,argsFiltro,null);
        while(c.moveToNext())
            fecha= c.getString(c.getColumnIndex(ContactsContract.CommonDataKinds.Event.START_DATE));
        return fecha;
    }
}
