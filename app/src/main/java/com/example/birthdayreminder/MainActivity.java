package com.example.birthdayreminder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.birthdayreminder.placeholder.PlaceholderContent;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static ArrayList<Contacto> miLista = new ArrayList<Contacto>();
    public static MyContactoRecyclerViewAdapter miAdaptador;
    public static SQLiteDatabase db;
    EditText contactos;
    public String phone = null;
    public static int idContacto;
    AsyncTasks asyncTasks = new AsyncTasks();
    public static ArrayList telefonos = new ArrayList();
    public static Contacto contacto;
    Button verContactos;
    public String nombrecontacto=null;
    public String cumple=null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        miAdaptador = new MyContactoRecyclerViewAdapter(miLista);

        setContentView(R.layout.activity_main);
        solicitarPermisos();
        contactos = findViewById(R.id.etNombres);

        Button buscar = findViewById(R.id.buttonBuscar);
        buscar.setOnClickListener(this);
        verContactos = findViewById(R.id.buttonBD);
        verContactos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ListadoConfigurado.class);
                v.getContext().startActivity(intent);
            }
        });

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

     //   ListadoConfigurado.seleccionBdAlarma();



        //PRUEBA IMPLEMENTACIÓN TIMEPICKER
        Button picker= findViewById(R.id.Pick);
        picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar ahora = Calendar.getInstance();
                int horaActual = ahora.get(Calendar.HOUR_OF_DAY);
                int minutoActual = ahora.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(v.getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        alarma.setHoraAlarma(hourOfDay);
                        alarma.setMinutoAlarma(minute);
                    }
                }, horaActual, minutoActual, true);
                timePickerDialog.show();
            }
        });

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
        //Borro los teléfonos anteriores guardados en el array
        telefonos.clear();
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




        if (misContactos!=null && phoneCursor!=null){
            while(misContactos.moveToNext() && phoneCursor.moveToNext()){

                // Cargo Id y nombre; mientras que la foto del contacto y el cumpleaños, lo hago con un AsyncTask
                if (Integer.parseInt(misContactos.getString(
                        misContactos.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)))>0) {

                    idContacto = misContactos.getInt(misContactos.getColumnIndex(ContactsContract.CommonDataKinds.Nickname._ID));
                    PlaceholderContent.idContacto = idContacto;

                    do{
                            nombrecontacto = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Nickname.DISPLAY_NAME));
                            //Lo guardo fuera para pasarlo al otro activity.
                            PlaceholderContent.nombre = nombrecontacto;
                            phone = phoneCursor.getString(4);


                            PlaceholderContent.telefono = phone;

                            // Async
                            cumple = asyncTasks.getCumpleaños(idContacto, this);

                            // Guardar contacto.
                            contacto = new Contacto(idContacto, nombrecontacto, phone, cumple);


                            //abrirFoto(contacto); Async
                            asyncTasks.abrirFoto(contacto, this);

                         //   String numeroT = phoneCursor.getString(phone2);
                          //  Log.d("PHONE2: ", numeroT);
                            Log.d("Contacto: ", idContacto + ": " + nombrecontacto + ": " + phone + " " + cumple);
                        telefonos.add(phone);
                            miLista.add(contacto);


miAdaptador.notifyDataSetChanged();

                    }while (phoneCursor.moveToNext());


                    //    Log.d("Cumpleaños async", cumple );
                }
            }



        }



    }

Alarma alarma = new Alarma();
    public void setAlarma(String diaAlarma, String mesAlarma){
        AlarmManager alarmManager;
        PendingIntent alarmIntent;

        Calendar calendario = Calendar.getInstance();
        calendario.setTimeInMillis(System.currentTimeMillis());
        calendario.set(Calendar.DAY_OF_MONTH, Integer.parseInt(diaAlarma));
        calendario.set(Calendar.MONTH, Integer.parseInt(mesAlarma));
        calendario.set(Calendar.HOUR_OF_DAY, alarma.getHoraAlarma());
        calendario.set(Calendar.MINUTE, alarma.getMinutoAlarma());
Log.d("ERROR: ", "Lee hsata aquí");
        Intent intent = new Intent(getApplicationContext(), Alarma.class);
        alarmIntent = PendingIntent.getBroadcast(getApplicationContext(),0,intent, 0);

        alarmManager = (AlarmManager) getApplicationContext().getSystemService(getApplicationContext().ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendario.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, alarmIntent);


    }



}