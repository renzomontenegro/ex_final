package dev.renzo.myapplication

import adapters.EquiposAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import models.EnfrentamientoModel

class ListadoEnfrentamientos : AppCompatActivity() {

    private lateinit var btnIrRegistrarEquipos: Button
    private lateinit var btnIrRegistrarEnfrentamientos: Button
    private lateinit var rvEnfrentamientos: RecyclerView
    private lateinit var db: FirebaseFirestore
    private lateinit var adapter: EquiposAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_listado_enfrentamientos)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnIrRegistrarEquipos.setOnClickListener { irAregistroEquipos() }
        btnIrRegistrarEnfrentamientos.setOnClickListener { irARegistrarEnfrentamientos() }

        rvEnfrentamientos = findViewById(R.id.rvEnfrentamientos)
        db = FirebaseFirestore.getInstance()
        adapter = EquiposAdapter(emptyList())

        rvEnfrentamientos.layoutManager = LinearLayoutManager(context)
        rvEnfrentamientos.adapter = adapter

        cargarEnfrentamientos()

    }

    private fun irAregistroEquipos() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun irARegistrarEnfrentamientos() {
        val intent = Intent(this, RegistroEnfrentamientos::class.java)
        startActivity(intent)
        finish()
    }

    private fun cargarEnfrentamientos() {
        db.collection("Enfrentamientos")
            .get()
            .addOnSuccessListener { result ->
                val productos = result.mapNotNull { document ->
                    document.toObject(EnfrentamientoModel::class.java)
                }
                adapter.actualizarProductos(productos)
            }
            .addOnFailureListener { exception ->
                Log.e("enfrentamientosFragment", "Error al cargar enfrentamientos", exception)
            }
    }
}