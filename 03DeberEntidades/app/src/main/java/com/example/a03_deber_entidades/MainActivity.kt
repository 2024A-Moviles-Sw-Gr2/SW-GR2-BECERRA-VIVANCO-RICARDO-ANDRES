package com.example.a03_deber_entidades


import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var adaptador: ArrayAdapter<Sistema>
    private val sistemas: MutableList<Sistema> = mutableListOf()

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
                        actualizarListaSistemas()
                        mostrarSnackbar("Sistemas actualizadas")
                    }else{
                        mostrarSnackbar("Sistemas NO actualizadas")
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializa la base de datos aquí
        Database.tablas = SQLiteHelper(this)


        val botonCrearSistema = findViewById<Button>(R.id.btn_crear_sistema)
        botonCrearSistema.setOnClickListener {
            val intentExplicito = Intent(
                this,
                SistemaFormulario::class.java
            )
            intentExplicito.putExtra("operacion", "crear")
            intentExplicito.putExtra("id", -1)
            callbackFormulario.launch(intentExplicito)
        }

        val listView = findViewById<ListView>(R.id.lv_sistemas)
        adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            sistemas
        )
        listView.adapter = adaptador
        registerForContextMenu(listView)

        // Load initial data
        actualizarListaSistemas()
    }

    private var posicionItemSeleccionado = -1

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ){
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_sistemas, menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        posicionItemSeleccionado = info.position
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val nombreSistemaSeleccionada = adaptador.getItem(posicionItemSeleccionado)!!.nombre
        return when (item.itemId) {
            R.id.mi_editar_sistema -> {
                val id = Database.tablas!!.obtenerIDSistema(nombreSistemaSeleccionada)
                val intentExplicito = Intent(
                    this,
                    SistemaFormulario::class.java
                )
                intentExplicito.putExtra("operacion", "actualizar")
                intentExplicito.putExtra("id", id)
                callbackFormulario.launch(intentExplicito)
                true
            }
            R.id.mi_eliminar_sistema -> {
                val id = Database.tablas!!.obtenerIDSistema(nombreSistemaSeleccionada)
                if (id != null) {
                    Database.tablas!!.eliminarSistema(id)
                    mostrarSnackbar("Se eliminó la sistema: $nombreSistemaSeleccionada")
                    actualizarListaSistemas()
                }
                true
            }
            R.id.mi_ver_sistema -> {
                val intentExplicito = Intent(
                    this,
                    PlanetasActivity::class.java
                )
                intentExplicito.putExtra("sistema", nombreSistemaSeleccionada)
                callbackFormulario.launch(intentExplicito)
                true
            }
            R.id.mi_ver_ubicación -> {
                val intentExplicito = Intent(
                    this,
                    MapView::class.java
                )
                val id = Database.tablas!!.obtenerIDSistema(nombreSistemaSeleccionada)
                intentExplicito.putExtra("id", id)
                callbackFormulario.launch(intentExplicito)
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    private fun actualizarListaSistemas() {
        val nuevasSistemas = Database.tablas?.consultarListaSistema() ?: emptyList()
        sistemas.clear()
        sistemas.addAll(nuevasSistemas)
        adaptador.notifyDataSetChanged()
    }

    private fun mostrarSnackbar(texto: String) {
        val snack = Snackbar.make(
            findViewById(R.id.cl_main_sistemas),
            texto,
            Snackbar.LENGTH_INDEFINITE
        )
        snack.show()
    }
}
