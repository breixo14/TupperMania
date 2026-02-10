package segundo.dam.tuppermania.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad central que agrupa la planificación semanal de un usuario.
 * Almacena el resumen del plan (fechas, objetivo, calorías medias) y
 * mantiene la lista de recetas asignadas a través de la relación con PlanPlato.
 */
@Document(collection = "planes_nutricionales")
public class PlanNutricional {

    @Id
    private String idPlan; // String para Mongo

    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String objetivo;
    private Integer caloriasTotales;
    private String listaCompraResumida;

    // Relación con Usuario (Referencia por ID para evitar anidamiento infinito)
    private String usuarioId;

    // LISTA EMBEBIDA: Aquí viven las comidas del plan
    private List<PlanPlato> platosAsignados = new ArrayList<>();

    // --- GETTERS Y SETTERS ---

    public String getIdPlan() { return idPlan; }
    public void setIdPlan(String idPlan) { this.idPlan = idPlan; }

    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }

    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }

    public String getObjetivo() { return objetivo; }
    public void setObjetivo(String objetivo) { this.objetivo = objetivo; }

    public Integer getCaloriasTotales() { return caloriasTotales; }
    public void setCaloriasTotales(Integer caloriasTotales) { this.caloriasTotales = caloriasTotales; }

    public String getUsuarioId() { return usuarioId; }
    public void setUsuarioId(String usuarioId) { this.usuarioId = usuarioId; }

    public List<PlanPlato> getPlatosAsignados() { return platosAsignados; }
    public void setPlatosAsignados(List<PlanPlato> platosAsignados) { this.platosAsignados = platosAsignados; }

    public String getListaCompraResumida() { return listaCompraResumida; }
    public void setListaCompraResumida(String listaCompraResumida) { this.listaCompraResumida = listaCompraResumida; }
}