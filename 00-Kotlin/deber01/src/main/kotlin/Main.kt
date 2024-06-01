package org.example

import Planeta
import SistemaPlanetario
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter

//Gestion de archivos
fun eliminarArchivo(){
    val nombreArchivo = "src/main/resources/planetas.txt"
    val archivo = File(nombreArchivo)
    if(archivo.exists()) {
        archivo.delete()
    }
}
fun guardarDatosEnArchivo(encabezado: String) {
    val nombreArchivo = "src/main/resources/planetas.txt"
    BufferedWriter(FileWriter(nombreArchivo, true)).use { writer ->
        writer.write(encabezado)
        SistemaPlanetario.getSistemasPlanetarios().forEach { sistemaPlanetario ->
            writer.write(sistemaPlanetario.toString())
            writer.write("\n")
        }
    }
    println("\nDatos guardados en el archivo")
}

fun main() {
    eliminarArchivo() //Reiniciamos el archivo
    val nuevoSistemaPlanetario = SistemaPlanetario(
        nombre = "Sistema Solar",
        numeroPlanetas = 8,
        tieneEstrellaCentral = true,
        galaxia = "La Vía Láctea",
        edad = 4568000000
    )

    SistemaPlanetario.addSistema(nuevoSistemaPlanetario)
    val planeta1 = Planeta(
        id = 1,
        nombre = "Mercurio",
        tieneAnillos = false,
        masa = 3.285e23,
        periodoOrbital = 88
    )

    val planeta2 = Planeta(
        id = 2,
        nombre = "Venus",
        tieneAnillos = false,
        masa = 4.867e24,
        periodoOrbital = 225
    )

    val planeta3 = Planeta(
        id = 3,
        nombre = "Tierra",
        tieneAnillos = false,
        masa = 5.972e24,
        periodoOrbital = 365
    )

    val planeta4 = Planeta(
        id = 4,
        nombre = "Júpiter",
        tieneAnillos = true,
        masa = 1.898e27,
        periodoOrbital = 4333
    )
    println("-Inicio-")
    println("-Agregar planetas-")
    Planeta.addPlaneta(planeta1)
    Planeta.addPlaneta(planeta2)
    Planeta.addPlaneta(planeta3)
    Planeta.addPlaneta(planeta4)
    SistemaPlanetario.addPlanetasSistema("Sistema Solar", Planeta.getPlanetas())
    println("-Planetas-")
    SistemaPlanetario.getSistemasPlanetarios().forEach { println(it.toString())}
    println("-Obtener un planeta por ID-")
    println(Planeta.getPlaneta(2))
    val planetaActualizado= Planeta(
        id = 4,
        nombre = "Júpiter Actualizado",
        tieneAnillos = true,
        masa = 1.898e27,
        periodoOrbital = 4333
    )
    println("-Planetas despues de actualizar-")
    Planeta.updatePlaneta(4, planetaActualizado)
    SistemaPlanetario.getSistemasPlanetarios().forEach { println(it.toString())}
    println("-Eliminar-")
    guardarDatosEnArchivo("Antes")
    Planeta.deletePlaneta(3)
    SistemaPlanetario.getSistemasPlanetarios().forEach { println(it.toString())}
    guardarDatosEnArchivo("Despues")

    val sistemaPlanetarioEncontrado = SistemaPlanetario.getSistema("Sistema Solar")
    println("-SistemaPlanetario-")
    println(sistemaPlanetarioEncontrado.toString())
    val otroSistemaPlanetario = SistemaPlanetario(
        nombre = "Sistema Solar Actualizado",
        numeroPlanetas = 8,
        tieneEstrellaCentral = true,
        galaxia = "Andromeda",
        edad = 4568000000
    )
    guardarDatosEnArchivo("Antes Sistema")
    SistemaPlanetario.updateSistema("Sistema Solar",otroSistemaPlanetario)
    guardarDatosEnArchivo("Despues Sistema")
    SistemaPlanetario.deleteSistema("Sistema Solar Actualizado")
    guardarDatosEnArchivo("Eliminacion total")
    SistemaPlanetario.getSistemasPlanetarios().forEach { println(it.toString())}




}


