package com.example.birthdayreminder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    ListView listaContactos;
    SQLiteDatabase db;
    EditText contactos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contactos = findViewById(R.id.eTNombres);
        listaContactos = findViewById(R.id.listViewLista);

        // Base de datos.

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
}