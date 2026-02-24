package segundo.dam.tuppermania.repository;

import java.util.List;
import java.util.Map;

public interface ConsultasRepository {

    List<Map> obtenerPlatosMasPopulares(int limite);

    List<Map> obtenerPromedioCaloriasPorObjetivo();

    List<Map> obtenerUsuariosSinPlanes();
}