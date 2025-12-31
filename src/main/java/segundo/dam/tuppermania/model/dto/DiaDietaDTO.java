package segundo.dam.tuppermania.model.dto;

import java.util.List;

public class DiaDietaDTO {
    private String diaSemana;
    private List<ComidaDTO> comidas;

    // Getters y Setters
    public String getDiaSemana() { return diaSemana; }
    public void setDiaSemana(String diaSemana) { this.diaSemana = diaSemana; }
    public List<ComidaDTO> getComidas() { return comidas; }
    public void setComidas(List<ComidaDTO> comidas) { this.comidas = comidas; }
}