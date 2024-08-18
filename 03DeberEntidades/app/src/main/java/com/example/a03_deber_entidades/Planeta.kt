package com.example.a03_deber_entidades

class Planeta (
    var id: Int,
    var nombre: String,
    var tieneAnillos: Boolean,
    var masa: Double,
    var periodoOrbital: Int,
    var sistemaNombre: String
){
    // Métodos CRUD para Planeta
    companion object {
        val planetas = mutableListOf<Planeta>()

        // Método Create
        fun agregarPlaneta(planeta: Planeta) {
            planetas.add(planeta)
            println("\nSe ha creado el planeta: " + planeta.nombre)
        }

        // Método Read
        fun obtenerPlaneta(id: Int): Planeta? {
            return planetas.find { it.id == id }
        }

        // Método Update
        fun actualizarPlaneta(id: Int, planetaActualizado: Planeta) {
            val index = planetas.indexOfFirst { it.id == id }
            return if (index != -1) {
                planetas[index] = planetaActualizado
                println("\nSe ha actualizado el planeta: " + planetas[index].nombre)
            } else {
                println("\nNo se ha actualizado el planeta")
            }
        }

        // Método Delete
        fun eliminarPlaneta(id: Int) {
            planetas.forEach { planetaActual ->
                if (planetaActual.id == id) {
                    planetas.remove(planetaActual)
                    println("\nSe ha eliminado el planeta con id: " + id)
                    return
                }
            }
        }

        // Métodos extra
        fun listarPlanetas(): MutableList<Planeta> {
            return planetas
        }

        fun listarPlanetasPorBiblioteca(sistemaNombre: String): List<Planeta> {
            return planetas.filter { it.sistemaNombre.lowercase() == sistemaNombre.lowercase() }
        }

    }
    override fun toString(): String {
        return "\nID: $id, Nombre: $nombre, Tiene anillos: $tieneAnillos, Masa: $masa, PeriodoOrbital: $periodoOrbital"
    }

}