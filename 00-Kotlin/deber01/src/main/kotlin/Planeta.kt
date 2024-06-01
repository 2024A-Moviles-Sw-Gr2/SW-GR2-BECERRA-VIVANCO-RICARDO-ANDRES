class Planeta(
    var id: Int,
    var nombre: String,
    var tieneAnillos: Boolean,
    var masa: Double,
    var periodoOrbital: Int
){
    companion object{
        private val planetas: MutableList<Planeta> = ArrayList()

        fun addPlaneta(planeta: Planeta){
            planetas.add(planeta)
            println("Nuevo planeta: ${planeta.toString()}")
        }

        fun getPlaneta(id: Int): Planeta?{
            return planetas.find { it.id == id }
        }

        fun updatePlaneta(id:Int, planetaActualizado: Planeta){
            for(planeta in planetas){
                if(planeta.id == id){
                    planeta.nombre = planetaActualizado.nombre
                    planeta.tieneAnillos = planetaActualizado.tieneAnillos
                    planeta.masa = planetaActualizado.masa
                    planeta.periodoOrbital = planetaActualizado.periodoOrbital
                    println("Se actualizo el planeta: ${planeta.nombre}")
                }
            }
        }

        fun deletePlaneta(id: Int){
            planetas.forEach { planetaActual: Planeta ->
                if(planetaActual.id == id){
                    planetas.remove(planetaActual)
                    println("Se elimino el planeta: ${planetaActual.nombre}")
                }
            }
        }

        fun getPlanetas(): MutableList<Planeta>{
            return planetas
        }
    }
    override fun toString(): String {
        return "\nID: $id, Nombre: $nombre, Tiene anillos: $tieneAnillos, Masa: $masa, PeriodoOrbital: $periodoOrbital"
    }
}