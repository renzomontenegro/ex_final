package dev.renzo.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.FirebaseFirestore
import models.EquiposModel

class MainActivity : AppCompatActivity() {

    private lateinit var etNombre: EditText
    private lateinit var etURLescudo: EditText
    private lateinit var btnGuardarEquipo: Button
    private lateinit var btnRegistrarEnfrentamientos: Button
    private lateinit var btnListado: Button
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        etNombre = findViewById(R.id.etNombre)
        etURLescudo = findViewById(R.id.etURLescudo)
        btnGuardarEquipo = findViewById(R.id.btnGuardarEquipo)
        btnRegistrarEnfrentamientos = findViewById(R.id.btnRegistrarEnfrentamientos)
        btnListado = findViewById(R.id.btnListado)

        db = FirebaseFirestore.getInstance()

        btnGuardarEquipo.setOnClickListener { registrarEquipo() }
        btnRegistrarEnfrentamientos.setOnClickListener { irAregistroEnfrentamientos() }
        btnListado.setOnClickListener { irAListadoEnfrentamientos() }


        }

    private fun registrarEquipo() {
        val nombreEquipo = etNombre.text.toString()
        val urlEsc = etURLescudo.text.toString()

        val equipo = EquiposModel(nombreEquipo = nombreEquipo, urlEsc = urlEsc)

        db.collection("Equipos").add(equipo)
            .addOnSuccessListener {
                mostrarMensaje("Equipo registrado exitosamente")
                limpiarCampos()
            }
            .addOnFailureListener { e ->
                mostrarMensaje("Error al registrar el Equipo: ${e.message}")
            }
    }

    private fun mostrarMensaje(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }

    private fun limpiarCampos() {
        etNombre.text.clear()
        etURLescudo.text.clear()
    }

    private fun irAregistroEnfrentamientos() {
        val intent = Intent(this, RegistroEnfrentamientos::class.java)
        startActivity(intent)
        finish()
    }

    private fun irAListadoEnfrentamientos() {
        val intent = Intent(this, ListadoEnfrentamientos::class.java)
        startActivity(intent)
        finish()
    }


}
