package com.example.a03_deber_entidades

class Sistema (
    var nombre: String,
    var numeroPlanetas: Int,
    var tieneEstrellaCentral: Boolean,
    var galaxia: String,
    var edad: Long,
    var latitud: Double,
    var longitud: Double

) {
    fun agregarPlanetas(nuevosPlanetas: MutableList<Planeta>) {
        Companion.planetas = nuevosPlanetas
    }

    companion object {
        val sistemas = mutableListOf<Sistema>()
        private var planetas: MutableList<Planeta> = mutableListOf()


        // Método Create
        fun agregarSistema(sistema: Sistema) {
            sistemas.add(sistema)
            println("\nSe ha agregado el sistema: " + sistema.nombre)
        }

        fun agregarPlanetaASistema(nombre: String, nuevosPlanetas: MutableList<Planeta>) {
            val sistema = sistemas.find { it.nombre == nombre }
            sistema?.agregarPlanetas(nuevosPlanetas)
        }

        // Método Read
        fun obtenerSistema(nombre: String): Sistema? {
            return sistemas.find { it.nombre.lowercase() == nombre.lowercase() }
        }

        // Método Update
        fun actualizarSistema(nombre: String, sistemaActualizado: Sistema) {
            val index = sistemas.indexOfFirst { it.nombre == nombre }
            return if (index != -1) {
                sistemas[index] = sistemaActualizado
                println("\nSe ha actualizado el sistema: " + sistemas[index].nombre)
            } else {
                println("\nNo se ha actualizado el sistema")
            }
        }

        // Método Delete
        fun eliminarBiblioteca(nombre: String) {
            val sistemaAEliminar = sistemas.find { it.nombre == nombre }
            if (sistemaAEliminar != null) {
                // Eliminar los planetas asociados a esta sistema
                val planetasAEliminar = Planeta.listarPlanetasPorBiblioteca(nombre)
                planetasAEliminar.forEach { planeta ->
                    Planeta.eliminarPlaneta(planeta.id)
                }
                sistemas.remove(sistemaAEliminar)
                println("\nSe ha eliminado la sistema: $nombre")
            } else {
                println("\nNo se ha encontrado la sistema: $nombre")
            }
        }


        // Métodos extra
        fun listarSistemas(): List<Sistema> {
            return sistemas
        }
    }

    override fun toString(): String {
        return "\nSistema: ${nombre}, Numero de planetas ${numeroPlanetas}, Tiene estrella central: ${tieneEstrellaCentral}, Edad: ${edad}, Galaxia: ${galaxia}"
    }
}