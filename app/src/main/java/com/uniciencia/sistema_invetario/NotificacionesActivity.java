package com.uniciencia.sistema_invetario;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class NotificacionesActivity extends AppCompatActivity {

    private DatabaseReference notificacionesRef;
    private ListView listViewNotificaciones;
    private ArrayList<Notificacion> notificacionesList;
    private NotificacionesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notificaciones);

        notificacionesRef = FirebaseDatabase.getInstance().getReference().child("notificaciones");
        listViewNotificaciones = findViewById(R.id.listViewNotificaciones);
        notificacionesList = new ArrayList<>();
        adapter = new NotificacionesAdapter(this, notificacionesList);
        listViewNotificaciones.setAdapter(adapter);

        // Leer las notificaciones de la base de datos
        notificacionesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                notificacionesList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    try {
                        Notificacion notificacion = snapshot.getValue(Notificacion.class);
                        notificacionesList.add(notificacion);
                    } catch (Exception e) {
                        Log.e("NotificacionesActivity", "Error al convertir datos en Notificacion: " + e.getMessage());
                    }
                }
                adapter.notifyDataSetChanged();
                Log.d("NotificacionesActivity", "Número de notificaciones: " + notificacionesList.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Manejar errores de base de datos, si es necesario
                Log.e("NotificacionesActivity", "Error de base de datos: " + databaseError.getMessage());
            }
        });
    }
}