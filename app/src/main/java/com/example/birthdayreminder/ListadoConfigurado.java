package com.example.birthdayreminder;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ListadoConfigurado extends AppCompatActivity {
    ListView listaContactos;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listado_configurado);
        listaContactos = findViewById(R.id.listviewContactos);
        listarGuardados();
    }

    public void listarGuardados(){
        ArrayAdapter<String> adaptador;
        List<String> lista = new ArrayList<String>();
        Cursor c=MainActivity.db.rawQuery("SELECT Nombre, FechaNacimiento, Telefono, TipoNotif FROM MisCumples", null);
        if(c.getCount()==0)
            lista.add("No hay contactos guardados");
        else{
            while(c.moveToNext())
                lista.add("Nombre: "+c.getString(0)+" Cumpleaños: "+c.getString(1)
                + " Teléfono: "+ c.getString(2) + " Tipo de notificación: " + (c.getString(3)));
        }
        adaptador=new ArrayAdapter<String>
                (getApplicationContext(),android.R.layout.simple_list_item_1,lista);
        listaContactos.setAdapter(adaptador);
        c.close();
    }

}
