package com.example.a03_deber_entidades

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.util.Date

class SQLiteHelper(
    contexto: Context?
) : SQLiteOpenHelper(
    contexto,
    "sistemas_planetarios",
    null,
    1
) {
    override fun onCreate(database: SQLiteDatabase?) {
        val scriptSQLCrearTablaSistema = """
            CREATE TABLE SISTEMA(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre VARCHAR(50) ,
                numeroPlanetas INTEGER,
                tieneEstrellaCentral BOOLEAN,
                galaxia VARCHAR(50),
                edad INTEGER,
                latitud DECIMAL(15),
                longitud DECIMAL(15)
            )
        """.trimIndent()
        database?.execSQL(scriptSQLCrearTablaSistema)

        val scriptSQLCrearTablaPlaneta = """
            CREATE TABLE PLANETA(
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre VARCHAR(50),
                tieneAnillos BOOLEAN,
                masa DECIMAL(15, 2),
                periodoOrbital INTEGER,
                sistemaNombre VARCHAR(50),
                FOREIGN KEY (sistemaNombre) REFERENCES SISTEMA(nombre)
            )
        """.trimIndent()
        database?.execSQL(scriptSQLCrearTablaPlaneta)
    }

    override fun onUpgrade(database: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        database?.execSQL("DROP TABLE IF EXISTS PLANETA")
        database?.execSQL("DROP TABLE IF EXISTS SISTEMA")
        onCreate(database)
    }

    // Métodos para la tabla SISTEMA
    fun crearSistema(
        nombre: String,
        numeroPlanetas: Int,
        tieneEstrellaCentral: Boolean,
        galaxia: String,
        edad: Long,
        latitud: Double,
        longitud: Double
    ): Boolean {
        val database = writableDatabase
        val valoresAGuardar = ContentValues()

        valoresAGuardar.put("nombre", nombre)
        valoresAGuardar.put("numeroPlanetas", numeroPlanetas)
        valoresAGuardar.put("tieneEstrellaCentral", tieneEstrellaCentral)
        valoresAGuardar.put("galaxia", galaxia)
        valoresAGuardar.put("edad", edad)
        valoresAGuardar.put("latitud", latitud)
        valoresAGuardar.put("longitud", longitud)

        val resultadoGuardar = database.insert("SISTEMA", null, valoresAGuardar)
        database.close()
        return resultadoGuardar.toInt() != -1
    }

    fun eliminarSistema(id: Int): Boolean {
        val database = writableDatabase
        val parametrosConsultaDelete = arrayOf(id.toString())
        val resultadoEliminacion = database.delete(
            "SISTEMA",
            "id=?",
            parametrosConsultaDelete
        )
        database.close()
        return resultadoEliminacion.toInt() != -1
    }

    fun actualizarSistema(
        nombre: String,
        numeroPlanetas: Int,
        tieneEstrellaCentral: Boolean,
        galaxia: String,
        edad: Long,
        latitud: Double,
        longitud: Double
    ): Boolean {
        val database = writableDatabase
        val valoresAActualizar = ContentValues()

        valoresAActualizar.put("numeroPlanetas", numeroPlanetas)
        valoresAActualizar.put("tieneEstrellaCentral", tieneEstrellaCentral)
        valoresAActualizar.put("galaxia", galaxia)
        valoresAActualizar.put("edad", edad)
        valoresAActualizar.put("latitud", latitud)
        valoresAActualizar.put("longitud", longitud)

        val parametrosConsultaActualizar = arrayOf(nombre)
        val resultadoActualizacion = database.update(
            "SISTEMA",
            valoresAActualizar,
            "nombre=?",
            parametrosConsultaActualizar
        )
        database.close()
        return resultadoActualizacion.toInt() != -1
    }

    fun consultarSistemaPorNombre(nombre: String): Unit? {
        val database = readableDatabase
        val scriptConsultaLectura = """
            SELECT * FROM SISTEMA WHERE nombre = ?
        """.trimIndent()
        val parametrosConsulta = arrayOf(nombre)
        val resultadoConsulta = database.rawQuery(scriptConsultaLectura, parametrosConsulta)

        val existeAlMenosUno = resultadoConsulta.moveToFirst()
        val sistema = if (existeAlMenosUno) {
            val indiceNombre = resultadoConsulta.getColumnIndex("nombre")
            val indiceNumeroPlanetas = resultadoConsulta.getColumnIndex("numeroPlanetas")
            val indiceTieneEstrellaCentral = resultadoConsulta.getColumnIndex("tieneEstrellaCentral")
            val indiceGalaxia = resultadoConsulta.getColumnIndex("galaxia")
            val indiceEdad = resultadoConsulta.getColumnIndex("edad")
            val indiceLatitud = resultadoConsulta.getColumnIndex("latitud")
            val indiceLongitud = resultadoConsulta.getColumnIndex("longitud")
            val nombreSistema = resultadoConsulta.getString(indiceNombre)
            val numeroPlanetas = resultadoConsulta.getInt(indiceNumeroPlanetas)
            val tieneEstrellaCentral = resultadoConsulta.getInt(indiceTieneEstrellaCentral) == 1
            val galaxia = resultadoConsulta.getString(indiceGalaxia)
            val edad = resultadoConsulta.getLong(indiceEdad)
            val latitud = resultadoConsulta.getDouble(indiceLatitud)
            val longitud = resultadoConsulta.getDouble(indiceLongitud)
            val sistema = Sistema(nombreSistema, numeroPlanetas, tieneEstrellaCentral, galaxia, edad, latitud, longitud)
        } else {
            null
        }
        resultadoConsulta.close()
        database.close()
        return sistema
    }

    fun consultarListaSistema(): List<Sistema> {
        val database = readableDatabase
        val scriptConsultaLectura = """
            SELECT * FROM SISTEMA
        """.trimIndent()
        val resultadoConsulta = database.rawQuery(scriptConsultaLectura, null)

        val sistemas = mutableListOf<Sistema>()
        if (resultadoConsulta.moveToFirst()) {
            do {
                val indiceNombre = resultadoConsulta.getColumnIndex("nombre")
                val indiceNumeroPlanetas = resultadoConsulta.getColumnIndex("numeroPlanetas")
                val indiceTieneEstrellaCentral = resultadoConsulta.getColumnIndex("tieneEstrellaCentral")
                val indiceGalaxia = resultadoConsulta.getColumnIndex("galaxia")
                val indiceEdad = resultadoConsulta.getColumnIndex("edad")
                val indiceLatitud = resultadoConsulta.getColumnIndex("latitud")
                val indiceLongitud = resultadoConsulta.getColumnIndex("longitud")
                val nombreSistema = resultadoConsulta.getString(indiceNombre)
                val numeroPlanetas = resultadoConsulta.getInt(indiceNumeroPlanetas)
                val tieneEstrellaCentral = resultadoConsulta.getInt(indiceTieneEstrellaCentral) == 1
                val galaxia = resultadoConsulta.getString(indiceGalaxia)
                val edad = resultadoConsulta.getLong(indiceEdad)

                val latitud = resultadoConsulta.getDouble(indiceLatitud)
                val longitud = resultadoConsulta.getDouble(indiceLongitud)
                val sistema = Sistema(nombreSistema, numeroPlanetas, tieneEstrellaCentral, galaxia, edad, latitud,longitud)
                sistemas.add(sistema)
            } while (resultadoConsulta.moveToNext())
        }
        resultadoConsulta.close()
        database.close()
        return sistemas
    }

    // Métodos para la tabla PLANETA
    fun crearPlaneta(
        nombre: String,
        tieneAnillos: Boolean,
        masa: Double,
        periodoOrbital: Int,
        sistemaNombre: String
    ): Boolean {
        val database = writableDatabase
        val valoresAGuardar = ContentValues()

        valoresAGuardar.put("nombre", nombre)
        valoresAGuardar.put("tieneAnillos", tieneAnillos)
        valoresAGuardar.put("masa", masa)
        valoresAGuardar.put("periodoOrbital", periodoOrbital)
        valoresAGuardar.put("sistemaNombre", sistemaNombre)

        val resultadoGuardar = database.insert("PLANETA", null, valoresAGuardar)
        database.close()
        return resultadoGuardar.toInt() != -1
    }

    fun eliminarPlaneta(id: Int): Boolean {
        val database = writableDatabase
        val parametrosConsultaDelete = arrayOf(id.toString())
        val resultadoEliminacion = database.delete(
            "PLANETA",
            "id=?",
            parametrosConsultaDelete
        )
        database.close()
        return resultadoEliminacion.toInt() != -1
    }

    fun actualizarPlaneta(
        id: Int,
        nombre: String,
        tieneAnillos: Boolean,
        masa: Double,
        periodoOrbital: Int
    ): Boolean {
        val database = writableDatabase
        val valoresAActualizar = ContentValues()

        valoresAActualizar.put("nombre", nombre)
        valoresAActualizar.put("tieneAnillos", tieneAnillos)
        valoresAActualizar.put("masa", masa)
        valoresAActualizar.put("periodoOrbital", periodoOrbital)

        val parametrosConsultaActualizar = arrayOf(id.toString())
        val resultadoActualizacion = database.update(
            "PLANETA",
            valoresAActualizar,
            "id=?",
            parametrosConsultaActualizar
        )
        database.close()
        return resultadoActualizacion.toInt() != -1
    }

    fun consultarSistemaPorID(id: Int): Sistema? {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = """
            SELECT * FROM SISTEMA WHERE ID = ?
        """.trimIndent()
        val arregloParametrosConsultaLectura = arrayOf(id.toString())
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultaLectura,
            arregloParametrosConsultaLectura
        )

        val existeAlMenosUno = resultadoConsultaLectura.moveToFirst()
        val arregloRespuesta = arrayListOf<Sistema>()
        if (existeAlMenosUno) {
            do {
                val indiceNombre = resultadoConsultaLectura.getColumnIndex("nombre")
                val indiceNumeroPlanetas = resultadoConsultaLectura.getColumnIndex("numeroPlanetas")
                val indiceEstrellaCentral = resultadoConsultaLectura.getColumnIndex("tieneEstrellaCentral")
                val indiceGalaxia = resultadoConsultaLectura.getColumnIndex("galaxia")
                val indiceEdad = resultadoConsultaLectura.getColumnIndex("edad")
                val indiceLatitud = resultadoConsultaLectura.getColumnIndex("latitud")
                val indiceLongitud = resultadoConsultaLectura.getColumnIndex("longitud")

                val nombreSistema = resultadoConsultaLectura.getString(indiceNombre)
                val numeroPlanetas= resultadoConsultaLectura.getInt(indiceNumeroPlanetas)
                val estrellaCentral = resultadoConsultaLectura.getInt(indiceEstrellaCentral)
                val galaxia = resultadoConsultaLectura.getString(indiceGalaxia)
                val edad = resultadoConsultaLectura.getLong(indiceEdad)
                val latitud = resultadoConsultaLectura.getDouble(indiceLatitud)
                val longitud = resultadoConsultaLectura.getDouble(indiceLongitud)
                val tieneEstrellaBoolean = estrellaCentral == 1

                val sistema = Sistema(
                    nombreSistema,
                    numeroPlanetas,
                    tieneEstrellaBoolean,
                    galaxia,
                    edad,
                    latitud,
                    longitud
                )
                arregloRespuesta.add(sistema)
            } while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDatosLectura.close()
        return if (arregloRespuesta.size > 0) arregloRespuesta[0] else null
    }

    fun consultarPlanetaPorID(id: Int): Planeta? {
        val database = readableDatabase
        val scriptConsultaLectura = """
            SELECT * FROM PLANETA WHERE id = ?
        """.trimIndent()
        val parametrosConsulta = arrayOf(id.toString())
        val resultadoConsulta = database.rawQuery(scriptConsultaLectura, parametrosConsulta)

        val existeAlMenosUno = resultadoConsulta.moveToFirst()
        val planeta = if (existeAlMenosUno) {
            val indiceNombre = resultadoConsulta.getColumnIndex("nombre")
            val indiceTieneAnillos = resultadoConsulta.getColumnIndex("tieneAnillos")
            val indiceMasa = resultadoConsulta.getColumnIndex("masa")
            val indicePeriodoOrbital = resultadoConsulta.getColumnIndex("periodoOrbital")
            val indiceSistemaNombre = resultadoConsulta.getColumnIndex("sistemaNombre")

            val nombrePlaneta = resultadoConsulta.getString(indiceNombre)
            val tieneAnillos = resultadoConsulta.getInt(indiceTieneAnillos) == 1
            val masa = resultadoConsulta.getDouble(indiceMasa)
            val periodoOrbital = resultadoConsulta.getInt(indicePeriodoOrbital)
            val sistemaNombre = resultadoConsulta.getString(indiceSistemaNombre)

            Planeta(id, nombrePlaneta, tieneAnillos, masa, periodoOrbital, sistemaNombre)
        } else {
            null
        }
        resultadoConsulta
        resultadoConsulta.close()
        database.close()
        return planeta
    }

    fun consultarListaPlanetas(nombre: String): List<Planeta> {
        val database = readableDatabase
        val scriptConsultaLectura = """
            SELECT * FROM PLANETA
            WHERE sistemaNombre = ?
        """.trimIndent()
        val parametroConsulta = arrayOf(nombre)
        val resultadoConsulta = database.rawQuery(scriptConsultaLectura, parametroConsulta)

        val planetas = mutableListOf<Planeta>()
        if (resultadoConsulta.moveToFirst()) {
            do {
                val indiceId = resultadoConsulta.getColumnIndex("id")
                val indiceNombre = resultadoConsulta.getColumnIndex("nombre")
                val indiceTieneAnillos = resultadoConsulta.getColumnIndex("tieneAnillos")
                val indiceMasa = resultadoConsulta.getColumnIndex("masa")
                val indicePeriodoOrbital = resultadoConsulta.getColumnIndex("periodoOrbital")
                val indiceSistemaNombre = resultadoConsulta.getColumnIndex("sistemaNombre")

                val id = resultadoConsulta.getInt(indiceId)
                val nombrePlaneta = resultadoConsulta.getString(indiceNombre)
                val tieneAnillos = resultadoConsulta.getInt(indiceTieneAnillos) == 1
                val masa = resultadoConsulta.getDouble(indiceMasa)
                val periodoOrbital = resultadoConsulta.getInt(indicePeriodoOrbital)
                val sistemaNombre = resultadoConsulta.getString(indiceSistemaNombre)

                val planeta = Planeta(id, nombrePlaneta, tieneAnillos, masa, periodoOrbital, sistemaNombre)
                planetas.add(planeta)
            } while (resultadoConsulta.moveToNext())
        }
        resultadoConsulta.close()
        database.close()
        return planetas
    }
    fun obtenerIDPlaneta(nombre: String): Int? {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = """
        SELECT * FROM PLANETA WHERE nombre = ?
    """.trimIndent()
        val arregloParametrosConsultaLectura = arrayOf(nombre)
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultaLectura,
            arregloParametrosConsultaLectura
        )
        if (resultadoConsultaLectura.moveToFirst()) {
            val indiceID = resultadoConsultaLectura.getColumnIndex("id")
            val id = resultadoConsultaLectura.getInt(indiceID)
            resultadoConsultaLectura.close()
            baseDatosLectura.close()
            return id
        } else {
            resultadoConsultaLectura.close()
            baseDatosLectura.close()
            return -1
        }
    }

    fun obtenerIDSistema(nombre: String): Int? {
        val baseDatosLectura = readableDatabase
        val scriptConsultaLectura = """
        SELECT * FROM SISTEMA WHERE nombre = ?
    """.trimIndent()
        val arregloParametrosConsultaLectura = arrayOf(nombre)
        val resultadoConsultaLectura = baseDatosLectura.rawQuery(
            scriptConsultaLectura,
            arregloParametrosConsultaLectura
        )
        if (resultadoConsultaLectura.moveToFirst()) {
            val indiceID = resultadoConsultaLectura.getColumnIndex("id")
            val id = resultadoConsultaLectura.getInt(indiceID)
            resultadoConsultaLectura.close()
            baseDatosLectura.close()
            return id
        } else {
            resultadoConsultaLectura.close()
            baseDatosLectura.close()
            return -1
        }
    }



}
