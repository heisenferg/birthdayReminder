package com.example.birthdayreminder;

import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.LENGTH_SHORT;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
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
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

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
    String telefono = "693438334";
    String mensaje = "HOla felicidades";
Notificar notificado = new Notificar();


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
                enviarNotificacion();

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


    }

    boolean permisos_concedidos=false;

    private void solicitarPermisos(){

        //   while (permisos_concedidos==false) {
        if (checkSelfPermission(Manifest.permission.READ_CONTACTS) !=
                PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, 1);
            requestPermissions(new String[]{Manifest.permission.SEND_SMS}, 1);
        } else if (checkSelfPermission(Manifest.permission.READ_CONTACTS) ==
                PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED){
            permisos_concedidos=true;
        }
        if (checkSelfPermission(Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.SEND_SMS}, 1);
        } else if (checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
            permisos_concedidos = true;
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

    public void setAlarma(int hora, int minuto){
        AlarmManager alarmManager;
        PendingIntent alarmIntent;

        Calendar calendario = Calendar.getInstance();
        calendario.setTimeInMillis(System.currentTimeMillis());
        calendario.set(Calendar.HOUR_OF_DAY, hora);
        calendario.set(Calendar.MINUTE, minuto);


        Intent intent2 = new Intent(getApplicationContext(), Alarma.class);
        alarmIntent = PendingIntent.getBroadcast(getApplicationContext(),0,intent2, 0);

        alarmManager = (AlarmManager) getApplicationContext().getSystemService(getApplicationContext().ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendario.getTimeInMillis(),
                     AlarmManager.INTERVAL_DAY, alarmIntent);
        Log.d("ERROR: ¿?", "Lee hasta aquí. Hora " + hora + " minuto: " + minuto);


    }

    // MENÚ
    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.mimenu, menu);
        return true;
    }

    // SELECCIONES DE MENÚ
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        switch (id){
            case R.id.miMenu:
                seleccionHora();
                Toast.makeText(getApplicationContext(), "Hora a la que sonará la alarma", LENGTH_LONG).show();
                Log.d("HORA ", alarma.getHoraAlarma() + " " + alarma.getMinutoAlarma());
                break;

        }

        return super.onOptionsItemSelected(item);
    }


        public void seleccionHora(){
            Calendar ahora = Calendar.getInstance();
            int horaActual = ahora.get(Calendar.HOUR_OF_DAY);
            int minutoActual = ahora.get(Calendar.MINUTE);
            TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    alarma.setHoraAlarma(hourOfDay);
                    alarma.setMinutoAlarma(minute);
                    setAlarma(alarma.getHoraAlarma(), alarma.getMinutoAlarma());
                    Toast.makeText(getApplicationContext(), "La alarma sonará a las " + alarma.getHoraAlarma() +
                            " y " + alarma.getMinutoAlarma() + " minutos.", LENGTH_SHORT).show();
                }
            }, horaActual, minutoActual, true);
            timePickerDialog.show();

        }


    public void enviarNotificacion(){
        Log.d("NOTIFICACIÓN", "Comienza");
        crearCanalNotificaciones();
        int id = 1;
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);
        NotificationManager notificationManager = (NotificationManager) getSystemService(this.NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"cumple").
                setSmallIcon(R.drawable.cumple)
                .setContentTitle("Cumpleaños: ")
                .setContentText("Felicita a ")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        Log.d("NOTIFICACIÓN", "después de builder");


        notificationManager.notify(id, builder.build());

    }


    private void crearCanalNotificaciones(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel canal = new NotificationChannel("cumple", "Título",
                    NotificationManager.IMPORTANCE_DEFAULT);
            NotificationChannel canal2 = new NotificationChannel("cumple", "nombre", NotificationManager.IMPORTANCE_DEFAULT);
            canal.setDescription("Felicitacion cumpleaños");
            NotificationManager notificationManager = (NotificationManager) getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(canal2);
        }

        Log.d("NOTIFICACIÓN", "FIN canal");

    }


}