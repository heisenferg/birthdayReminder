package com.example.birthdayreminder;

import android.graphics.Bitmap;

public class Contacto {

    public int id;
    public String nombre;
    public Bitmap foto;
    public String telefono;
    public String cumple;
    public String aviso;

    public Contacto(int id, String nombre, String telefono,String cumple) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.cumple = cumple;
    }
}
