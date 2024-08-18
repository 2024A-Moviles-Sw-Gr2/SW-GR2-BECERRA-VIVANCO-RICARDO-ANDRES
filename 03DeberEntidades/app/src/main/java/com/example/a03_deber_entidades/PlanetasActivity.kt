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

class PlanetasActivity: AppCompatActivity()  {

    private lateinit var adaptador: ArrayAdapter<Planeta>
    private val planetas: MutableList<Planeta> = mutableListOf()
    private var sistema: String = ""

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
                        actualizarListaLibros()
                        mostrarSnackbar("Libros actualizados")
                    }else{
                        mostrarSnackbar("Libros no actualizados")
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_planeta)

        Database.tablas = SQLiteHelper(this)

        sistema = intent.getStringExtra("sistema") ?: ""

        val botonCrearLibro = findViewById<Button>(R.id.btn_crear_planetas)
        botonCrearLibro.setOnClickListener {
            val intentExplicito = Intent(this, PlanetaFormulario::class.java)
            intentExplicito.putExtra("operacion", "crear")
            intentExplicito.putExtra("id", -1)
            intentExplicito.putExtra("sistema", sistema)
            callbackFormulario.launch(intentExplicito)
        }

        // Manejo List view
        val listView = findViewById<ListView>(R.id.lv_planetas)
        adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            planetas)
        listView.adapter = adaptador
        registerForContextMenu(listView)

        // Cargar datos iniciales
        actualizarListaLibros()
    }

    override fun onResume() {
        super.onResume()
        actualizarListaLibros()
    }

    private fun actualizarListaLibros() {
        val nuevasBibliotecas = Database.tablas?.consultarListaPlanetas(sistema) ?: emptyList()
        planetas.clear()
        planetas.addAll(nuevasBibliotecas)
        adaptador.notifyDataSetChanged()
    }

    private var posicionItemSeleccionado = -1

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.menu_planetas, menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        posicionItemSeleccionado = info.position
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val nombreLibroSeleccionada = adaptador.getItem(posicionItemSeleccionado)!!.nombre
        return when (item.itemId) {
            R.id.mi_editar_planeta -> {
                val id = Database.tablas!!.obtenerIDPlaneta(nombreLibroSeleccionada)
                val intentExplicito = Intent(this, PlanetaFormulario::class.java)
                intentExplicito.putExtra("operacion", "actualizar")
                intentExplicito.putExtra("id", id)
                intentExplicito.putExtra("sistema", sistema)
                callbackFormulario.launch(intentExplicito)
                true
            }
            R.id.mi_eliminar_planeta -> {
                val id = Database.tablas!!.obtenerIDPlaneta(nombreLibroSeleccionada)
                if (id != null) {
                    Database.tablas!!.eliminarPlaneta(id)
                    mostrarSnackbar("Se eliminó el planeta: $nombreLibroSeleccionada")
                    actualizarListaLibros()
                }
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    private fun mostrarSnackbar(texto: String) {
        val snack = Snackbar.make(
            findViewById(R.id.cl_planetas),
            texto,
            Snackbar.LENGTH_INDEFINITE
        )
        snack.show()
    }
}