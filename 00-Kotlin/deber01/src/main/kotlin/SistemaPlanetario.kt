class SistemaPlanetario(
    var nombre: String,
    var numeroPlanetas: Int,
    var tieneEstrellaCentral: Boolean,
    var galaxia: String,
    var edad: Long
){
    companion object{
        private val sistemasPlanetarios = mutableListOf<SistemaPlanetario>()
        private var planetas: MutableList<Planeta> = mutableListOf()

        fun addSistema(sistemaSolar: SistemaPlanetario){
            sistemasPlanetarios.add(sistemaSolar)
            println("Nuevo sistema agregado: ${sistemaSolar.nombre}")
        }

        fun addPlanetasSistema(nombre: String, nuevosPlanetas: MutableList<Planeta>){
            sistemasPlanetarios.forEach {
                if (it.nombre == nombre){
                    it.addPlanetas(nuevosPlanetas)
                }
            }
        }

        fun getSistema(nombre: String): SistemaPlanetario?{
            return sistemasPlanetarios.find { it.nombre == nombre }
        }

        fun updateSistema(nombre: String, nuevaBiblioteca: SistemaPlanetario){
            val index = sistemasPlanetarios.indexOfFirst { it.nombre == nombre }
            return  if (index != 1){
                sistemasPlanetarios[index] = nuevaBiblioteca
                println("Sistema: ${sistemasPlanetarios[index].nombre} actualizado")
            } else {
                println("No se actualizo el sistema")
            }
        }

        fun deleteSistema(nombre: String) {
            val iterator = sistemasPlanetarios.iterator()
            while (iterator.hasNext()) {
                val sistemaActual = iterator.next()
                if (sistemaActual.nombre == nombre) {
                    iterator.remove()
                    println("Sistema eliminado: ${sistemaActual.nombre}")
                }
            }
        }

        fun getSistemasPlanetarios(): List<SistemaPlanetario>{
            return sistemasPlanetarios
        }
    }

    fun addPlanetas(nuevosPlanetas: MutableList<Planeta>){
        planetas = nuevosPlanetas
    }

    override fun toString(): String {
        var sistemasToString = "\nSistema: ${nombre}, Numero de planetas ${numeroPlanetas}, Tiene estrella central: ${tieneEstrellaCentral}, Edad: ${edad}, Galaxia: ${galaxia}"
        planetas.forEach { sistemasToString += it.toString() }
        return sistemasToString
    }

}