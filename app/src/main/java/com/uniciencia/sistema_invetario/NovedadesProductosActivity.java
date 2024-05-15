package com.uniciencia.sistema_invetario;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class NovedadesProductosActivity extends AppCompatActivity {

    private EditText etCodigoProducto;
    private TextView tvNombreProducto;
    private TextView tvUbicacionProducto;
    private TextView tvPrecioProducto;
    private Spinner spinnerEstado;
    private EditText etCantidad;
    private Button btnActualizarEstado;

    private DatabaseReference productosRef;
    private DatabaseReference notificacionesRef;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.novedades_productos);

        mAuth = FirebaseAuth.getInstance();

        etCodigoProducto = findViewById(R.id.etCodigoProducto);
        tvNombreProducto = findViewById(R.id.tvNombreProducto);
        tvUbicacionProducto = findViewById(R.id.tvUbicacionProducto);
        tvPrecioProducto = findViewById(R.id.tvPrecioProducto);
        spinnerEstado = findViewById(R.id.spinnerEstado);
        etCantidad = findViewById(R.id.etCantidad);
        btnActualizarEstado = findViewById(R.id.btnActualizarEstado);

        productosRef = FirebaseDatabase.getInstance().getReference().child("productos");
        notificacionesRef = FirebaseDatabase.getInstance().getReference().child("notificaciones");

        btnActualizarEstado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarEstadoProducto();
            }
        });
    }

    public void buscarProducto(View view) {
        final String codigoProducto = etCodigoProducto.getText().toString();

        Query query = productosRef.orderByChild("productCode").equalTo(codigoProducto);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Producto producto = snapshot.getValue(Producto.class);
                        if (producto != null) {
                            tvNombreProducto.setText(producto.getProductName());
                            tvUbicacionProducto.setText(producto.getProductLocation());
                            tvPrecioProducto.setText(String.valueOf(producto.getProductPrice()));
                            // No es necesario seguir buscando si encontramos el producto
                            break;
                        }
                    }
                } else {
                    Toast.makeText(NovedadesProductosActivity.this, "Producto no encontrado", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(NovedadesProductosActivity.this, "Error al buscar el producto: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void actualizarEstadoProducto() {
        final String codigoProducto = etCodigoProducto.getText().toString();
        final String nuevoEstado = spinnerEstado.getSelectedItem().toString();
        final int nuevaCantidad = Integer.parseInt(etCantidad.getText().toString());

        Query query = productosRef.orderByChild("productCode").equalTo(codigoProducto);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Producto producto = snapshot.getValue(Producto.class);
                        if (producto != null) {
                            int cantidadExistente = producto.getProductCant();
                            if (nuevaCantidad <= cantidadExistente) {
                                // Actualizar los datos del producto
                                snapshot.getRef().child("productState").setValue(nuevoEstado);
                                snapshot.getRef().child("productCant").setValue(nuevaCantidad);

                                Toast.makeText(NovedadesProductosActivity.this, "Producto actualizado exitosamente", Toast.LENGTH_SHORT).show();

                                // Registrar la novedad en la base de datos de notificaciones
                                registrarNovedad(codigoProducto, nuevoEstado);
                            } else {
                                Toast.makeText(NovedadesProductosActivity.this, "La cantidad editada supera la cantidad existente", Toast.LENGTH_SHORT).show();
                            }
                            // Limpiar los campos después de actualizar
                            etCodigoProducto.setText("");
                            tvNombreProducto.setText("");
                            tvUbicacionProducto.setText("");
                            tvPrecioProducto.setText("");
                            etCantidad.setText("");
                            // No es necesario seguir buscando si encontramos el producto
                            break;
                        }
                    }
                } else {
                    Toast.makeText(NovedadesProductosActivity.this, "Producto no encontrado", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(NovedadesProductosActivity.this, "Error al buscar el producto: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void registrarNovedad(String codigoProducto, String nuevoEstado) {
        DatabaseReference nuevaNotificacionRef = notificacionesRef.push();

        // Obtener la fecha y hora actuales
        String fechaActual = obtenerFechaActual();
        String horaActual = obtenerHoraActual();

        // Obtener el usuario actual
        FirebaseUser currentUser = mAuth.getCurrentUser();
        String usuarioActual = currentUser != null ? currentUser.getEmail() : "Usuario no identificado";

        // Construir la notificación
        Notificacion notificacion = new Notificacion();
        notificacion.setHora(horaActual);
        notificacion.setAccion("Se registró novedad del producto, actualizando estado a " + nuevoEstado);
        notificacion.setFecha(fechaActual);
        notificacion.setNombre(tvNombreProducto.getText().toString());
        notificacion.setProductoAfectado(codigoProducto);
        notificacion.setUsuario(usuarioActual);

        // Establecer la notificación en la base de datos
        nuevaNotificacionRef.setValue(notificacion);
    }

    private String obtenerFechaActual() {
        // Obtener la fecha actual en el formato deseado
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    private String obtenerHoraActual() {
        // Obtener la hora actual en el formato deseado
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return timeFormat.format(date);
    }
}