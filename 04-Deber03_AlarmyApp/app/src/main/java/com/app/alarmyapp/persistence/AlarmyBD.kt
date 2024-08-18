package com.app.alarmyapp.persistence

import android.os.Build
import androidx.annotation.RequiresApi
import com.app.alarmyapp.model.Alarma
import java.time.LocalTime

class AlarmyBD {
    companion object{
        @RequiresApi(Build.VERSION_CODES.O)
        val alarmasList = listOf<Alarma>(
            Alarma(LocalTime.of(9, 30), true, "Hora de levantarse", "Tono 1", true, 1, listOf("Lunes", "Martes", "Miércoles", "Jueves", "Viernes"), "Misión 1"),
            Alarma(LocalTime.of(10, 30), true, "Tomar el medicamento", "Tono 2", false, 1, listOf("Sábado", "Domingo"), "Misión 2"),
            Alarma(LocalTime.of(11, 30), true, "Pedir el gas", "Tono 3", true, 1, listOf("Miércoles"), "Misión 3"),
            Alarma(LocalTime.of(12, 30), true, "Comenzar con el emprendimiento", "Tono 4", true, 1, listOf( "Miércoles", "Jueves", "Viernes", "Sábado"), "Misión 4"),
            Alarma(LocalTime.of(13, 30), true, "Hora de comer", "Tono 5", false, 1, listOf("Domingo"), "Misión 5"),
        )

    }
}