package segundo.dam.tuppermania.model.dto;

public class ComidaDTO {
    private String tipoComida; // DESAYUNO, COMIDA, CENA...
    private String nombrePlato;
    private String descripcion;
    private Integer caloriasAprox;

    // Getters y Setters
    public String getTipoComida() { return tipoComida; }
    public void setTipoComida(String tipoComida) { this.tipoComida = tipoComida; }
    public String getNombrePlato() { return nombrePlato; }
    public void setNombrePlato(String nombrePlato) { this.nombrePlato = nombrePlato; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public Integer getCaloriasAprox() { return caloriasAprox; }
    public void setCaloriasAprox(Integer caloriasAprox) { this.caloriasAprox = caloriasAprox; }
}