package segundo.dam.tuppermania.model;

import segundo.dam.tuppermania.model.enums.NivelActividad;
import segundo.dam.tuppermania.model.enums.Objetivo;
import segundo.dam.tuppermania.model.enums.Sexo;

/**
 * Contiene la información antropométrica y de salud del usuario.
 * Estos datos son esenciales para que el algoritmo de IA (GeminiService)
 * pueda personalizar la dieta correctamente.
 */
public class PerfilFisico {
    private Double peso;
    private Double altura;
    private Integer edad;
    private String alergias;
    private String intolerancias;
    private Sexo sexo;
    private NivelActividad nivelActividad;
    private Objetivo objetivo;

    // Getters y Setters
    public Double getPeso() { return peso; }
    public void setPeso(Double peso) { this.peso = peso; }
    public Double getAltura() { return altura; }
    public void setAltura(Double altura) { this.altura = altura; }
    public Integer getEdad() { return edad; }
    public void setEdad(Integer edad) { this.edad = edad; }
    public String getAlergias() { return alergias; }
    public void setAlergias(String alergias) { this.alergias = alergias; }
    public String getIntolerancias() { return intolerancias; }
    public void setIntolerancias(String intolerancias) { this.intolerancias = intolerancias; }
    public Sexo getSexo() { return sexo; }
    public void setSexo(Sexo sexo) { this.sexo = sexo; }
    public NivelActividad getNivelActividad() { return nivelActividad; }
    public void setNivelActividad(NivelActividad nivelActividad) { this.nivelActividad = nivelActividad; }
    public Objetivo getObjetivo() { return objetivo; }
    public void setObjetivo(Objetivo objetivo) { this.objetivo = objetivo; }
}