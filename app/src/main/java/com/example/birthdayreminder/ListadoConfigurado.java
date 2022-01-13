package com.example.birthdayreminder;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
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

    String notificacion;

    public void listarGuardados(){
        ArrayAdapter<String> adaptador;
        List<String> lista = new ArrayList<String>();
        Cursor c=MainActivity.db.rawQuery("SELECT Nombre, FechaNacimiento, Telefono, TipoNotif FROM MisCumples", null);

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
                        + " Teléfono: " + c.getString(2) + " Tipo de notificación: " + notificacion);
            }
        }
        adaptador=new ArrayAdapter<String>
                (getApplicationContext(),android.R.layout.simple_list_item_1,lista);
        listaContactos.setAdapter(adaptador);
        c.close();
    }

   private static String dia[] = null;

    //Establecer alarma desde BD.
    public static void seleccionBdAlarma() {
        MainActivity alarma = new MainActivity();

        ArrayAdapter<String> adaptador;
        List<String> lista = new ArrayList<String>();
        Cursor c = MainActivity.db.rawQuery("SELECT FechaNacimiento FROM MisCumples", null);

        Log.d("AAA: ", "J");
        if (!c.moveToNext()) {
            Log.d("No hay guardado", "Fallo");
        } else {
            while (c.moveToNext()) {
                String arrayFecha = c.getString(0);
                dia = arrayFecha.split("-");
                Log.d("CUMPLEAÑOS: ", "Dia " + dia[2] + " Mes " + dia[1]);
                alarma.setAlarma(dia[2], dia[1]);
            }

        }
    }
}
