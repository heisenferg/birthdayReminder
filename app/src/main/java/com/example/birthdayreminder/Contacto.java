package com.example.birthdayreminder;

import android.graphics.Bitmap;

public class Contacto {

    public int id;
    public String nombre;
    public Bitmap foto;
    public String telefono;
    public String aviso;

    public Contacto(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
}
