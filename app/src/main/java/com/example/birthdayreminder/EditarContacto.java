package com.example.birthdayreminder;

import static com.example.birthdayreminder.placeholder.PlaceholderContent.*;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.birthdayreminder.placeholder.PlaceholderContent;

import java.util.ArrayList;
import java.util.Collections;

public class EditarContacto extends AppCompatActivity {

    EditText nombre;
    EditText cumple;
    Spinner telefono;
    public static ArrayList tel = MainActivity.telefonos;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar_contacto);
        cargar();

    }



    public void cargar(){
        //Nombre contacto
        nombre = findViewById(R.id.edNombre);
        nombre.setText(PlaceholderContent.nombre);
        // Fecha de nacimiento
        cumple = findViewById(R.id.edNacimiento);
        cumple.setText(AsyncTasks.cumple);
        //Teléfono
        telefono = findViewById(R.id.spinnerTelefonos);
       // telefono.setAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, Collections.singletonList(PlaceholderContent.telefono)));
        telefono.setAdapter(new ArrayAdapter<String>(EditarContacto.this, android.R.layout.simple_spinner_dropdown_item, tel));



    }


}
