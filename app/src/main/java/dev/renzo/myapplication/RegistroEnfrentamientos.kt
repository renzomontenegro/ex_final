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
import models.EnfrentamientoModel
import models.EquiposModel

class RegistroEnfrentamientos : AppCompatActivity() {

    private lateinit var etLocal: EditText
    private lateinit var etVisita: EditText
    private lateinit var etCuotaLocal: EditText
    private lateinit var etCuotaEmpate: EditText
    private lateinit var etCuotaVisita: EditText
    private lateinit var btnRegistrarEnfrentamiento: Button
    private lateinit var btnRegistraEquipos: Button
    private lateinit var btnIrListado: Button
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registro_enfrentamientos)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        etLocal = findViewById(R.id.etLocal)
        etVisita = findViewById(R.id.etVisita)
        etCuotaLocal = findViewById(R.id.etCuotaLocal)
        etCuotaEmpate = findViewById(R.id.etCuotaEmpate)
        etCuotaVisita = findViewById(R.id.etCuotaVisita)

        db = FirebaseFirestore.getInstance()

        btnRegistrarEnfrentamiento = findViewById(R.id.btnRegistrarEnfrentamiento)
        btnRegistraEquipos = findViewById(R.id.btnRegistraEquipos)
        btnIrListado = findViewById(R.id.btnIrListado)

        db = FirebaseFirestore.getInstance()

        btnRegistrarEnfrentamiento.setOnClickListener { registrarEnfrentamiento() }
        btnRegistraEquipos.setOnClickListener { irAregistroEquipos() }
        btnIrListado.setOnClickListener { irAListadoEnfrentamientos() }

    }

    private fun registrarEnfrentamiento() {
        val eLocal = etLocal.text.toString()
        val eVisitante = etVisita.text.toString()
        val cuotaLocal = etCuotaLocal.text.toString()
        val cuotaEmpate = etCuotaEmpate.text.toString()
        val cuotaVisitante = etCuotaVisita.text.toString()

        val equipo = EnfrentamientoModel(eLocal = eLocal, eVisitante = eVisitante,cuotaLocal=cuotaLocal,cuotaEmpate=cuotaEmpate,cuotaVisitante=cuotaVisitante)

        db.collection("Enfrentamientos").add(equipo)
            .addOnSuccessListener {
                mostrarMensaje("Enfrentamiento registrado exitosamente")
                limpiarCampos()
            }
            .addOnFailureListener { e ->
                mostrarMensaje("Error al registrar el Enfrentamiento: ${e.message}")
            }
    }

    private fun mostrarMensaje(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show()
    }

    private fun limpiarCampos() {
        etLocal.text.clear()
        etVisita.text.clear()
        etCuotaLocal.text.clear()
        etCuotaEmpate.text.clear()
        etCuotaVisita.text.clear()
    }

    private fun irAregistroEquipos() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun irAListadoEnfrentamientos() {
        val intent = Intent(this, ListadoEnfrentamientos::class.java)
        startActivity(intent)
        finish()
    }




}