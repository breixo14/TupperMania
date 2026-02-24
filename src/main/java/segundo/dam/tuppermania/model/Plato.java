package segundo.dam.tuppermania.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Representa una receta o alimento individual disponible en el sistema.
 * Actúa como la unidad básica para la construcción de dietas.
 * Contiene información nutricional y detalles de preparación/ingredientes.
 */
@Document(collection = "platos")
public class Plato {

    @Id
    private String idPlato;

    private String nombre;
    private String descripcion;
    private Integer calorias;
    private String ingredientes;

    // --- GETTERS Y SETTERS ---

    public String getIdPlato() { return idPlato; }
    public void setIdPlato(String idPlato) { this.idPlato = idPlato; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Integer getCalorias() { return calorias; }
    public void setCalorias(Integer calorias) { this.calorias = calorias; }

    public String getIngredientes() { return ingredientes; }
    public void setIngredientes(String ingredientes) { this.ingredientes = ingredientes; }
}