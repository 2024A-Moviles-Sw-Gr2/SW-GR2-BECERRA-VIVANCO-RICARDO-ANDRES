package com.example.a03_deber_entidades

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class PlanetaFormulario: AppCompatActivity() {

    private var operacion: String = ""
    private var id: Int = -1
    private var sistema: String = ""

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_planeta)

        operacion = intent.getStringExtra("operacion") ?: ""
        id = intent.getIntExtra("id", -1)
        sistema = intent.getStringExtra("sistema") ?: ""

        val botonFormulario = findViewById<Button>(R.id.btn_formulario_planeta)
        botonFormulario.setOnClickListener {
            val titulo = findViewById<EditText>(R.id.ti_titulo_planeta).text.toString()
            val anillos = findViewById<EditText>(R.id.ti_anillos_planeta).text.toString()
            val periodo = findViewById<EditText>(R.id.ti_periodo_planeta).text.toString()
            val masa = findViewById<EditText>(R.id.ti_masa_planeta).text.toString()

            val masaDouble = masa.toDouble()
            val periodoInt = periodo.toInt()
            val anillosBoolean = when (anillos.trim().lowercase()) {
                "si" -> true
                "no" -> false
                else -> false
            }

            val respuesta = if (operacion == "crear") {
                Database.tablas!!.crearPlaneta(
                    titulo,
                    anillosBoolean,
                    masaDouble,
                    periodoInt,
                    sistema
                )
            } else {
                Database.tablas!!.actualizarPlaneta(
                    id,
                    titulo,
                    anillosBoolean,
                    masaDouble,
                    periodoInt
                )
            }
            if (respuesta) {
                devolverRespuesta(respuesta)
            }
        }

        if (operacion == "crear") {
            pantallaCrearPlaneta()
        } else if (operacion == "actualizar") {
            pantallaActualizarPlaneta(id)
        }
    }

    private fun devolverRespuesta(respuesta: Boolean) {
        val intentDevolverRespuesta = Intent()
        intentDevolverRespuesta.putExtra("respuesta", respuesta)
        setResult(RESULT_OK, intentDevolverRespuesta)
        finish()
    }

    private fun pantallaCrearPlaneta() {
        val titulo = findViewById<TextView>(R.id.tv_formulario_planeta)
        titulo.text = "Crear Planeta"
        val boton = findViewById<Button>(R.id.btn_formulario_planeta)
        boton.text = "Crear"
    }

    private fun pantallaActualizarPlaneta(id: Int) {
        val titulo = findViewById<TextView>(R.id.tv_formulario_planeta)
        titulo.text = "Actualizar Planeta"
        val boton = findViewById<Button>(R.id.btn_formulario_planeta)
        boton.text = "Guardar"
        val planeta = Database.tablas!!.consultarPlanetaPorID(id)
        val tituloPlaneta = findViewById<EditText>(R.id.ti_titulo_planeta)
        tituloPlaneta.setText(planeta!!.nombre)
        val anillos = findViewById<EditText>(R.id.ti_anillos_planeta)
        val respuesta = if (planeta!!.tieneAnillos) "si" else "no"
        anillos.setText(respuesta)
        val masa = findViewById<EditText>(R.id.ti_masa_planeta)
        masa.setText(planeta.masa.toString())
        val periodoOrbital = findViewById<EditText>(R.id.ti_periodo_planeta)
        periodoOrbital.setText(planeta.periodoOrbital.toString())
    }
}