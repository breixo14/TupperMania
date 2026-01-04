package segundo.dam.tuppermania.model.dto;

import java.util.List;

public class DietaGeneradaDTO {
    private String tituloPlan;
    private String explicacion;
    private List<DiaDietaDTO> dias;
    private List<String> listaCompraConsolidada;

    // Getters y Setters
    public String getTituloPlan() { return tituloPlan; }
    public void setTituloPlan(String tituloPlan) { this.tituloPlan = tituloPlan; }
    public String getExplicacion() { return explicacion; }
    public void setExplicacion(String explicacion) { this.explicacion = explicacion; }
    public List<DiaDietaDTO> getDias() { return dias; }
    public void setDias(List<DiaDietaDTO> dias) { this.dias = dias; }
    public List<String> getListaCompraConsolidada() { return listaCompraConsolidada; }
    public void setListaCompraConsolidada(List<String> listaCompraConsolidada) { this.listaCompraConsolidada = listaCompraConsolidada; }
}