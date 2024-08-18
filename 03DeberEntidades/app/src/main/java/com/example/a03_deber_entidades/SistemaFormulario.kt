package com.example.a03_deber_entidades

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat

class SistemaFormulario: AppCompatActivity(){
    private var operacion: String = ""
    private var id: Int = -1
    var longitud = 0.0
    var latitud = 0.0

    val callbackFormulario =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
                result ->
            if(result.resultCode == Activity.RESULT_OK){
                if(result.data != null){
                    val data = result.data
                    val respuesta = data?.getBooleanExtra("respuesta", false)
                    if(respuesta!!){
                        latitud = data!!.getDoubleExtra("latitud", 0.0)
                        longitud = data!!.getDoubleExtra("longitud", 0.0)

                        val lat_position = findViewById<EditText>(R.id.ti_latitud)
                        val long_position = findViewById<EditText>(R.id.ti_longitud)
                        lat_position.setText(latitud.toString())
                        long_position.setText(longitud.toString())
                    }else{
                        mostrarSnackbar("Ubicación NO encontrada")
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario_sistema)

        operacion = intent.getStringExtra("operacion") ?: ""
        id = intent.getIntExtra("id", -1)

        val botonSeleccionarUbicacion = findViewById<Button>(R.id.btn_buscar_ubicacion)
        botonSeleccionarUbicacion.setOnClickListener {
            val intent = Intent(this, MapSelect::class.java)
            callbackFormulario.launch(intent)
        }

        val botonFormulario = findViewById<Button>(R.id.btn_formulario_sistema)
        botonFormulario.setOnClickListener {
            val nombre = findViewById<EditText>(R.id.ti_nombre_sistema).text.toString()
            val numeroPlanetas = findViewById<EditText>(R.id.ti_planetas_sistema).text.toString()
            val tieneEstrella = findViewById<EditText>(R.id.ti_estrella_sistema).text.toString()
            val galaxia = findViewById<EditText>(R.id.ti_galaxia_sistema).text.toString()
            val edad = findViewById<EditText>(R.id.ti_edad_sistema).text.toString()
            val lat_positionStr = findViewById<EditText>(R.id.ti_latitud).text.toString()
            val long_positionStr = findViewById<EditText>(R.id.ti_longitud).text.toString()

            val numerolaneatasInt = numeroPlanetas.toInt()
            val edadLong = edad.toLong()
            val lat_position = lat_positionStr.toDouble()
            val long_position = long_positionStr.toDouble()
            val tieneEstrellaBoolean = when (tieneEstrella.trim().lowercase()) {
                "si" -> true
                "no" -> false
                else -> false
            }

            val respuesta = if (operacion == "crear") {
                Database.tablas!!.crearSistema(
                    nombre,
                    numerolaneatasInt,
                    tieneEstrellaBoolean,
                    galaxia,
                    edadLong,
                    lat_position,
                    long_position
                )
            } else {
                Database.tablas!!.actualizarSistema(
                    nombre,
                    numerolaneatasInt,
                    tieneEstrellaBoolean,
                    galaxia,
                    edadLong,
                    lat_position,
                    long_position
                )
            }
            devolverRespuesta(respuesta)
        }

        if (operacion == "crear") {
            pantallaCrearSistema()
        } else if (operacion == "actualizar") {
            pantallaActualizarSistema(id)
        }
    }

    private fun devolverRespuesta(respuesta: Boolean) {
        val intentDevolverRespuesta = Intent()
        intentDevolverRespuesta.putExtra("respuesta", respuesta)
        setResult(AppCompatActivity.RESULT_OK, intentDevolverRespuesta)
        finish()
    }

    private fun pantallaCrearSistema() {
        val titulo = findViewById<TextView>(R.id.tv_formulario_sistema)
        titulo.text = "Crear Sistema"
        val boton = findViewById<Button>(R.id.btn_formulario_sistema)
        boton.text = "Crear"
    }

    private fun pantallaActualizarSistema(id: Int) {
        val titulo = findViewById<TextView>(R.id.tv_formulario_sistema)
        titulo.text = "Actualizar Sistema"
        val boton = findViewById<Button>(R.id.btn_formulario_sistema)
        boton.text = "Guardar"
        val sistema = Database.tablas!!.consultarSistemaPorID(id)
        val nombre = findViewById<EditText>(R.id.ti_nombre_sistema)
        nombre.setText(sistema!!.nombre)
        val numeroPlanetas = findViewById<EditText>(R.id.ti_planetas_sistema)
        numeroPlanetas.setText(sistema!!.numeroPlanetas.toString())

        val tieneEstrella = findViewById<EditText>(R.id.ti_estrella_sistema)
        val respuesta = if (sistema!!.tieneEstrellaCentral) "si" else "no"
        tieneEstrella.setText(respuesta)

        val galaxia = findViewById<EditText>(R.id.ti_galaxia_sistema)
        galaxia.setText(sistema!!.galaxia)

        val edad = findViewById<EditText>(R.id.ti_edad_sistema)
        edad.setText(sistema!!.edad.toString())
    }

    fun mostrarSnackbar(texto:String){
        val snack = Snackbar.make(
            findViewById(R.id.cl_formulario_sistema),
            texto,
            Snackbar.LENGTH_INDEFINITE
        )
        snack.show()
    }


}