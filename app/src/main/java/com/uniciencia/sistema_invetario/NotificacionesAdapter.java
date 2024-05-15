package com.uniciencia.sistema_invetario;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.ArrayList;

public class NotificacionesAdapter extends ArrayAdapter<Notificacion> {

    private Context context;
    private ArrayList<Notificacion> notificacionesList;

    public NotificacionesAdapter(Context context, ArrayList<Notificacion> notificacionesList) {
        super(context, 0, notificacionesList);
        this.context = context;
        this.notificacionesList = notificacionesList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_notificacion, parent, false);
        }

        Notificacion notificacion = notificacionesList.get(position);

        TextView fechaTextView = convertView.findViewById(R.id.fechaTextView);
        TextView horaTextView = convertView.findViewById(R.id.horaTextView);
        TextView accionTextView = convertView.findViewById(R.id.accionTextView);
        TextView usuarioTextView = convertView.findViewById(R.id.usuarioTextView);
        TextView nombreTextView = convertView.findViewById(R.id.nombreTextView);

        fechaTextView.setText("Fecha: " + notificacion.getFecha());
        horaTextView.setText("Hora: " + notificacion.getHora());
        accionTextView.setText("Acción: " + notificacion.getAccion());
        usuarioTextView.setText("Usuario: " + notificacion.getUsuario());
        nombreTextView.setText("Producto Afectado: " + notificacion.getNombre());

        return convertView;
    }
}