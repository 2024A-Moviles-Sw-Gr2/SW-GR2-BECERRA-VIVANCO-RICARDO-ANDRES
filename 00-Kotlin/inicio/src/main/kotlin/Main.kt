package org.example

import java.util.Date

fun main() {
    println("Hola mundo");
    val inmutable: String = "Adrian"
    //inmutable = "Vicente"; ERROR!
    var mutable: String = "Adrian"
    mutable = "Vicente"

    //Duck Typing
    var ejemploVariable = "Ricardo Becerra"
    val edadEjemplo: Int = 12
    ejemploVariable.trim()
    //ejemploVariable = edadEjemplo; ERROR!
    //Variables primitivas
    val nombre: String = "Ricardo"
    val sueldo: Double = 1.2
    val estadoCivil: Char = 'C'
    val mayorEdad: Boolean = true

    // JAVA
    val fechaNacimiento: Date = Date()

    val estadoCivilWhen = "S"
    when (estadoCivilWhen){
        ("C")->{
            println("Casado")
        }
        "S" -> {
            println("Soltero")
        } else ->{
            println("No se sabe")
        }
    }

    val esSoltero = (estadoCivilWhen == "S")
    val coqueteo = (estadoCivilWhen == "C")

    calcularSueldo(10.00)
    calcularSueldo(10.00, 15.00, 20.00)
    calcularSueldo(10.00, bonoEspecial = 20.00)

    //Uso de las clases
    val sumaUno = Suma(1,1)
    val sumaDos = Suma(null,1)
    val sumaTres = Suma(1,null)
    val sumaCuatro = Suma(null,null)

    sumaUno.sumar()
    sumaDos.sumar()
    sumaTres.sumar()
    sumaCuatro.sumar()

    println(Suma.pi)
    println(Suma.elevarCuadrado(2))
    println(Suma.historialSumas)

    //ARREGLOS
    //Estático
    val arregloEstatico: Array<Int> = arrayOf(1,2,3)
    println(arregloEstatico)
    //Dinámico
    val arregloDinamico: ArrayList<Int> = arrayListOf(1,2,3)
    println(arregloDinamico)
    arregloDinamico.add(4)
    arregloDinamico.add(5)
    println(arregloDinamico)

    //For each -> Unit
    val respuestaForEach: Unit = arregloDinamico
        .forEach{valorActual: Int ->
            println("Valor actual: ${valorActual}")
        }
    arregloDinamico.forEach{println("Valor actual (it): ${it}")}


    //Map  Modifica o cambia el arreglo
    //1. Se envìa el nuevo valor en cada iteracion
    //2. nos devuelve un nuevo arreglo con NUEVOS valores

    val respuestaMap: List<Double> = arregloDinamico
        .map { valorActual: Int ->
            return@map valorActual.toDouble() + 10.0
        }
    println(respuestaMap)
    val respuestaMapDos = arregloDinamico.map { it + 15 }
    println(respuestaMapDos)


    //Filtrar el arreglo
    // Devolver una expresion de verdadero o falso
    // Nuevo arreglo Filtrado
    val respuestaFilter: List<Int> = arregloDinamico
        .filter { valorActual: Int ->
            //Condición
            val mayoresACinco: Boolean = valorActual>2
            return@filter mayoresACinco
        }
    val respuestaFilterDos = arregloDinamico.filter { it <= 2 }
    println(respuestaFilter)
    println(respuestaFilterDos)

    //Or-ANY Alguno cumple?
    //And-ALL Todos cumplen?

    val respuestaAny: Boolean = arregloDinamico
        .any{valorActual: Int ->
            return@any valorActual > 2
        }
    println(respuestaAny)

    val respuestaAll = arregloDinamico
        .all { valorActual: Int ->
            return@all valorActual > 2
        }
    println(respuestaAll)







}






abstract class Numeros (
    protected val numeroUno: Int, // Instancia
    protected val numeroDos: Int) {
    init { //Constructor primario (opcional)
        this.numeroDos
        this.numeroUno
        println("Inicializando")
    }
}

class Suma (uno: Int, dos: Int): Numeros (uno, dos) {
    val soyPublicoExplicito: String = "Explicito"
    val soyPublicoImplicito: String = "Implicito"
    init {
        uno
        dos
        this.soyPublicoExplicito
        soyPublicoImplicito
    }
    //Constructor secundario
    constructor(uno: Int?, dos: Int): this(
        if (uno == null) 0 else uno, dos)

    //Constructor tercero
    constructor(uno: Int, dos: Int?) : this(
        uno,if (dos == null) 0 else dos)

    //Constructor cuarto
    constructor(uno: Int?, dos: Int?) : this(
        if (uno == null) 0 else uno, if (dos == null) 0 else dos)

    fun sumar(): Int {
        val total = numeroUno+numeroDos
        agregarHistorial(total)
        return total
    }

    companion object{
        val pi = 3.14159
        fun elevarCuadrado(num: Int): Int{
            return num * num
        }
        val historialSumas = arrayListOf<Int>()
        fun agregarHistorial(valorTotalSuma: Int){
            historialSumas.add(valorTotalSuma)
        }
    }
}

fun calcularSueldo(
    sueldo: Double, // Requerido
    tasa: Double = 12.00, // Opcional pero ya viene por defecto
    bonoEspecial: Double? = null // Opcional pero puede ser NULL (?)
): Double{
    if (bonoEspecial == null){
        return sueldo * (100/tasa)
    } else {
        return sueldo * (100/tasa) * bonoEspecial
    }
}