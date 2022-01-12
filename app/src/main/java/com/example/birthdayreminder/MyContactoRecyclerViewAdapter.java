package com.example.birthdayreminder;

import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.birthdayreminder.placeholder.PlaceholderContent.PlaceholderItem;
import com.example.birthdayreminder.databinding.FragmentItemBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyContactoRecyclerViewAdapter extends RecyclerView.Adapter<MyContactoRecyclerViewAdapter.ViewHolder>  {

    private final ArrayList<Contacto> mValues;

    public MyContactoRecyclerViewAdapter(ArrayList<Contacto> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mNombre.setText(mValues.get(position).nombre);
        holder.mTelefono.setText(mValues.get(position).telefono);
     //   holder.aviso.setText(mValues.get(position).aviso);
        holder.foto.setImageBitmap(mValues.get(position).foto);
        holder.cumple.setText(mValues.get(position).cumple);

        holder.mNombre.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Toast.makeText(v.getContext(), "ID: " + MainActivity.idContacto, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext(), EditarContacto.class);
                v.getContext().startActivity(intent);
                mValues.clear();
                notifyDataSetChanged();
                return false;
            }
        });
        holder.foto.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Toast.makeText(v.getContext(), "ID: " + MainActivity.idContacto, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(v.getContext(), EditarContacto.class);
                v.getContext().startActivity(intent);
                mValues.clear();
                notifyDataSetChanged();

                return false;
            }
        });


    }

    public void editar(){

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }
    


    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mTelefono;
        public final TextView mNombre;
        public final ImageView foto;
        public final TextView cumple;
        public Contacto mItem;
        public final TextView aviso;

        public ViewHolder(View view) {
            super(view);
            mTelefono = (TextView) view.findViewById(R.id.textViewTlf);
            mNombre =(TextView)  view.findViewById(R.id.textViewNombre);
            foto = (ImageView) view.findViewById(R.id.imageViewFoto);
            cumple = (TextView) view.findViewById(R.id.txtViewCumple);
            aviso = (TextView)  view.findViewById(R.id.textViewAviso);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + mNombre.getText() + "'";
        }
    }
}