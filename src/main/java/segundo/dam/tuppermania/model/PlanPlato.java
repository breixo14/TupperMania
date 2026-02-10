package segundo.dam.tuppermania.model;

import segundo.dam.tuppermania.model.enums.DiaSemana;
import segundo.dam.tuppermania.model.enums.TipoComida;


public class PlanPlato {

    // Datos de la relación (Cuándo se come)
    private DiaSemana diaSemana;
    private TipoComida tipoComida;

    // Datos del Plato (Snapshot + Referencia)
    private String platoId;       // ID del plato original en Mongo
    private String nombrePlato;   // Copia del nombre por si cambia el original
    private Integer calorias;     // Copia de calorias

    // --- CONSTRUCTORES ---
    public PlanPlato() {}

    public PlanPlato(DiaSemana dia, TipoComida tipo, Plato platoOriginal) {
        this.diaSemana = dia;
        this.tipoComida = tipo;
        if (platoOriginal != null) {
            this.platoId = platoOriginal.getIdPlato();
            this.nombrePlato = platoOriginal.getNombre();
            this.calorias = platoOriginal.getCalorias();
        }
    }

    // --- GETTERS Y SETTERS ---
    public DiaSemana getDiaSemana() { return diaSemana; }
    public void setDiaSemana(DiaSemana diaSemana) { this.diaSemana = diaSemana; }

    public TipoComida getTipoComida() { return tipoComida; }
    public void setTipoComida(TipoComida tipoComida) { this.tipoComida = tipoComida; }

    public String getPlatoId() { return platoId; }
    public void setPlatoId(String platoId) { this.platoId = platoId; }

    public String getNombrePlato() { return nombrePlato; }
    public void setNombrePlato(String nombrePlato) { this.nombrePlato = nombrePlato; }

    public Integer getCalorias() { return calorias; }
    public void setCalorias(Integer calorias) { this.calorias = calorias; }
}