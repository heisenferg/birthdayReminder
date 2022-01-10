package com.example.birthdayreminder;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.birthdayreminder.placeholder.PlaceholderContent;

public class EditarContacto extends AppCompatActivity {
    public static final int SELECCION =1;
public MyContactoRecyclerViewAdapter miAdaptador = new MyContactoRecyclerViewAdapter(MainActivity.miLista);
EditText nombre;
MainActivity mainActivity = new MainActivity();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar_contacto);
cargar();

    }



    public void cargar(){
        nombre = findViewById(R.id.edNombre);
        nombre.setText(PlaceholderContent.nombre);

    }


}
