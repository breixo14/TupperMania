package segundo.dam.tuppermania.repository;

import java.util.List;
import java.util.Map;

public interface ConsultasRepository {

    // Consulta Compleja 1: Top platos más usados en los planes
    List<Map> obtenerPlatosMasPopulares(int limite);

    // Consulta Compleja 2: Promedio de calorías agrupado por Objetivo
    List<Map> obtenerPromedioCaloriasPorObjetivo();

    // Consulta Compleja 3: Usuarios que no tienen ningún plan asignado (Left Join)
    List<Map> obtenerUsuariosSinPlanes();
}