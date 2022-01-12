package com.example.birthdayreminder;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ListadoConfigurado extends AppCompatActivity {
    private static String size="JKJKJJ";
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
    //Establecer alarma desde BD.
    public static void seleccionBdAlarma(){

        Log.d("HOLA", size);
    /*/    ArrayAdapter<String> adaptador;
        List<String> lista= new ArrayList<String>();
        Cursor c = db.rawQuery("SELECT FechaNacimiento FROM MisCumples", null);
        String[] arrayFecha = String.valueOf(c).split("-");
        Log.d("AAA: ", arrayFecha[1] + " BBB " + arrayFecha[2]);
        if (!c.moveToNext()) {
            Log.d("No hay guardado", "Fallo");
        }else {
            while (c.moveToNext()) {
     */

        //alarma.setAlarma(mesAlarma,diaAlarma);
        //      }
        //  }
    }


}
