package com.example.birthdayreminder;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.birthdayreminder.placeholder.PlaceholderContent.PlaceholderItem;
import com.example.birthdayreminder.databinding.FragmentItemBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link PlaceholderItem}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyContactoRecyclerViewAdapter extends RecyclerView.Adapter<MyContactoRecyclerViewAdapter.ViewHolder> {

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
      //  holder.cumple.setText(mValues.get(position.cumple));
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
      //  public final TextView aviso;

        public ViewHolder(View view) {
            super(view);
            mTelefono = (TextView) view.findViewById(R.id.textViewTlf);
            mNombre =(TextView)  view.findViewById(R.id.textViewNombre);
            foto = (ImageView) view.findViewById(R.id.imageViewFoto);
            cumple = (TextView) view.findViewById(R.id.txtViewCumple);
        //   aviso = (TextView)  view.findViewById(R.id.textViewAviso);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + mNombre.getText() + "'";
        }
    }
}