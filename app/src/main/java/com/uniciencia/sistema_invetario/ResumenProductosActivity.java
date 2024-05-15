package com.uniciencia.sistema_invetario;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class ResumenProductosActivity extends AppCompatActivity {

    private DatabaseReference productosRef;
    private TextView tvTotalInventorySummary, tvProductsBySectionSummary,
            tvMostExpensiveProductsSummary, tvCheapestProductsSummary, tvInventoryStatusSummary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resumen_productos);

        tvTotalInventorySummary = findViewById(R.id.tvTotalInventorySummary);
        tvProductsBySectionSummary = findViewById(R.id.tvProductsBySectionSummary);
        tvMostExpensiveProductsSummary = findViewById(R.id.tvMostExpensiveProductsSummary);
        tvCheapestProductsSummary = findViewById(R.id.tvCheapestProductsSummary);
        tvInventoryStatusSummary = findViewById(R.id.tvInventoryStatusSummary);

        // Obtener la referencia a la base de datos
        productosRef = FirebaseDatabase.getInstance().getReference().child("productos");

        // Calcular y mostrar los resúmenes
        calcularTotalInventario();
        calcularProductosPorSeccion();
        calcularProductosMasCaros();
        calcularProductosMasEconomicos();
        calcularEstadoDelInventario();
    }

    private void calcularTotalInventario() {
        productosRef.orderByChild("productState").equalTo("Disponible").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                double totalInventoryValue = 0;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Producto producto = snapshot.getValue(Producto.class);
                    totalInventoryValue += producto.getProductPrice() * producto.getProductCant();
                }
                tvTotalInventorySummary.setText("Resumen Total del Inventario: $" + totalInventoryValue);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Maneja errores
            }
        });
    }

    private void calcularProductosPorSeccion() {
        productosRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                StringBuilder productsBySectionSummary = new StringBuilder("Productos disponibles por sección:\n");
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Producto producto = snapshot.getValue(Producto.class);
                    if (producto.getProductState().equals("Disponible")) {
                        String section = producto.getProductLocation();
                        String productName = producto.getProductName();
                        productsBySectionSummary.append("Ubicación: ").append(section).append(", Producto: ").append(productName).append("\n");
                    }
                }
                tvProductsBySectionSummary.setText(productsBySectionSummary.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Maneja errores
            }
        });
    }

    private void calcularProductosMasCaros() {
        productosRef.orderByChild("productPrice").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Producto> productos = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Producto producto = snapshot.getValue(Producto.class);
                    if (producto.getProductState().equals("Disponible")) {
                        productos.add(producto);
                    }
                }

                Collections.reverse(productos); // Ordenar de mayor a menor precio

                StringBuilder mostExpensiveProductsSummary = new StringBuilder("Productos más caros:\n");
                for (int i = 0; i < Math.min(5, productos.size()); i++) {
                    Producto producto = productos.get(i);
                    mostExpensiveProductsSummary.append(producto.getProductName()).append(": $").append(producto.getProductPrice()).append("\n");
                }
                tvMostExpensiveProductsSummary.setText(mostExpensiveProductsSummary.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Maneja errores
            }
        });
    }

    private void calcularProductosMasEconomicos() {
        productosRef.orderByChild("productPrice").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Producto> productos = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Producto producto = snapshot.getValue(Producto.class);
                    if (producto.getProductState().equals("Disponible")) {
                        productos.add(producto);
                    }
                }

                StringBuilder cheapestProductsSummary = new StringBuilder("Productos más económicos:\n");
                for (int i = 0; i < Math.min(5, productos.size()); i++) {
                    Producto producto = productos.get(i);
                    cheapestProductsSummary.append(producto.getProductName()).append(": $").append(producto.getProductPrice()).append("\n");
                }
                tvCheapestProductsSummary.setText(cheapestProductsSummary.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Maneja errores
            }
        });
    }

    private void calcularEstadoDelInventario() {
        productosRef.orderByChild("productState").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                StringBuilder inventoryStatusSummary = new StringBuilder("Estado del Inventario:\n");

                // Variables para almacenar el total global de productos y precio
                int totalAvailableProductsCount = 0;
                double totalAvailableProductsPrice = 0;

                // Mapa para almacenar totales por estado
                Map<String, Integer> stateCounts = new HashMap<>();
                Map<String, Double> statePrices = new HashMap<>();

                // Itera sobre cada producto en la base de datos
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Producto producto = snapshot.getValue(Producto.class);

                    // Si el producto no está agotado, procesarlo
                    if (!producto.getProductState().equals("Agotado")) {
                        // Suma la cantidad y el precio de este producto al total global
                        totalAvailableProductsCount += producto.getProductCant();
                        totalAvailableProductsPrice += producto.getProductCant() * producto.getProductPrice();

                        // Actualiza los totales por estado
                        String estado = producto.getProductState();
                        int cantidad = producto.getProductCant();
                        double precioTotal = producto.getProductCant() * producto.getProductPrice();

                        // Utiliza containsKey para verificar si la clave ya está en el mapa
                        // Si la clave está presente, obtén su valor; de lo contrario, usa el valor predeterminado 0
                        int currentCount = stateCounts.containsKey(estado) ? stateCounts.get(estado) : 0;
                        double currentPrice = statePrices.containsKey(estado) ? statePrices.get(estado) : 0.0;

                        // Luego, actualiza los totales por estado sumando el valor actual con el valor del producto actual
                        stateCounts.put(estado, currentCount + cantidad);
                        statePrices.put(estado, currentPrice + precioTotal);
                    }
                }

                // Agrega la información de cada estado al resumen del estado del inventario
                for (Map.Entry<String, Integer> entry : stateCounts.entrySet()) {
                    String estado = entry.getKey();
                    int cantidad = entry.getValue();
                    double precioTotal = statePrices.get(estado);

                    inventoryStatusSummary.append("- Estado: ").append(estado)
                            .append(", Cantidad: ").append(cantidad)
                            .append(", Precio total: $").append(precioTotal)
                            .append("\n");
                }

                // Agrega el resumen global del inventario al TextView
                inventoryStatusSummary.append("\nTotal: Cantidad: ")
                        .append(totalAvailableProductsCount)
                        .append(", Precio total: $")
                        .append(totalAvailableProductsPrice);
                tvInventoryStatusSummary.setText(inventoryStatusSummary.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Maneja errores
            }
        });
    }

}