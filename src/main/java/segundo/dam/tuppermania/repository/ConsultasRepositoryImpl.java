package segundo.dam.tuppermania.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Repository
public class ConsultasRepositoryImpl implements ConsultasRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<Map> obtenerPlatosMasPopulares(int limite) {
        /*
         * PIPELINE:
         * 1. $unwind: Desglosa el array de platosAsignados (un documento por plato).
         * 2. $group: Agrupa por nombre del plato y cuenta ocurrencias.
         * 3. $sort: Ordena descendente por cantidad.
         * 4. $limit: Se queda con los top N.
         * 5. $project: Formatea la salida.
         */
        Aggregation agg = newAggregation(
                unwind("platosAsignados"),
                group("platosAsignados.nombrePlato").count().as("totalUsos"),
                sort(Sort.Direction.DESC, "totalUsos"),
                limit(limite),
                project("totalUsos").and("_id").as("nombrePlato")
        );

        AggregationResults<Map> results = mongoTemplate.aggregate(agg, "planes_nutricionales", Map.class);
        return results.getMappedResults();
    }

    @Override
    public List<Map> obtenerPromedioCaloriasPorObjetivo() {
        /*
         * PIPELINE:
         * 1. $match: Filtra planes que tengan calorías > 0.
         * 2. $group: Agrupa por el campo 'objetivo'.
         * 3. $project: Calcula el promedio y formatea.
         * 4. $sort: Ordena por calorías promedio descendente.
         */
        Aggregation agg = newAggregation(
                match(org.springframework.data.mongodb.core.query.Criteria.where("caloriasTotales").gt(0)),
                group("objetivo")
                        .avg("caloriasTotales").as("caloriasPromedio")
                        .count().as("cantidadPlanes"),
                project("caloriasPromedio", "cantidadPlanes").and("_id").as("objetivo"),
                sort(Sort.Direction.DESC, "caloriasPromedio")
        );

        AggregationResults<Map> results = mongoTemplate.aggregate(agg, "planes_nutricionales", Map.class);
        return results.getMappedResults();
    }

    @Override
    public List<Map> obtenerUsuariosSinPlanes() {
        /*
         * PIPELINE:
         * 1. $addFields: Crea un campo temporal convirtiendo el _id (ObjectId) a String.
         * 2. $lookup: Une usando el nuevo campo de texto con el usuarioId del plan.
         * 3. $match: Filtra aquellos donde el array de planes esté vacío (size 0).
         * 4. $project: Muestra solo nombre y correo.
         * 5. $limit: Limitamos por seguridad.
         */

        org.springframework.data.mongodb.core.aggregation.AggregationOperation convertirIdAString =
                context -> new org.bson.Document("$addFields",
                        new org.bson.Document("idString", new org.bson.Document("$toString", "$_id")));

        Aggregation agg = newAggregation(
                convertirIdAString,
                lookup("planes_nutricionales", "idString", "usuarioId", "planes_usuario"),
                match(org.springframework.data.mongodb.core.query.Criteria.where("planes_usuario").size(0)),
                project("nombreUsuario", "correo"),
                limit(50)
        );

        AggregationResults<Map> results = mongoTemplate.aggregate(agg, "usuarios", Map.class);
        return results.getMappedResults();
    }
}