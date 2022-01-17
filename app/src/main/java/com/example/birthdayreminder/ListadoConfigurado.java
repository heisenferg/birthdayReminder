package com.example.birthdayreminder;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ListadoConfigurado extends AppCompatActivity  {
    ListView listaContactos;
    Button borrar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listado_configurado);
        listaContactos = findViewById(R.id.listviewContactos);
        listarGuardados();

        listaContactos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //borrarBD(position);
                return false;
            }
        });

        borrar = findViewById(R.id.buttonBorrar);
        borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                borrarBD();
            }
        });

    }

    String notificacion;
    List<String> lista = new ArrayList<String>();

    public void listarGuardados(){
        ArrayAdapter<String> adaptador;
        Cursor c=MainActivity.db.rawQuery("SELECT Nombre, FechaNacimiento, Telefono, TipoNotif, Mensaje, ID FROM MisCumples", null);

        if(c.getCount()==0)
            lista.add("No hay contactos guardados");
        else{
            while(c.moveToNext()) {
                if (c.getString(3).equals("S")) {
                    notificacion = "SMS";
                } else {
                    notificacion = "Notificación";
                }

                lista.add("Nombre: " + c.getString(0) + " Cumpleaños: " + c.getString(1)
                        + " Teléfono: " + c.getString(2) + " Tipo de notificación: " + notificacion
                        + " Mensaje: " + c.getString(4) + "ID: " + c.getString(5));
            }
        }
        adaptador=new ArrayAdapter<String>
                (getApplicationContext(),android.R.layout.simple_list_item_1,lista);
        listaContactos.setAdapter(adaptador);
        c.close();
    }

    public static String dia[] = null;


    //Establecer alarma desde BD.
    public static void seleccionBdAlarma() {
        Log.d("No hay guardado", "Fallo");

        Cursor c = MainActivity.db.rawQuery("SELECT FechaNacimiento FROM MisCumples", null);

        if (!c.moveToNext()) {
        } else {
            while (c.moveToNext()) {
                String arrayFecha = c.getString(0);
                dia = arrayFecha.split("-");
                Log.d("CUMPLEAÑOS: ", "Dia " + dia[2] + " MesA " + dia[1]);
            }

        }
    }

    public void borrarBD (){
        ArrayAdapter<String> adaptador;
        MainActivity.db.execSQL("DELETE FROM MisCumples");
        lista.clear();

        adaptador=new ArrayAdapter<String>
                (getApplicationContext(),android.R.layout.simple_list_item_1,lista);
        listaContactos.setAdapter(adaptador);
    }

}