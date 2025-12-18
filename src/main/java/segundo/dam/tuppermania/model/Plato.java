package segundo.dam.tuppermania.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "plato")
public class Plato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_plato;
    private String nombre;


    @Column(columnDefinition = "TEXT")
    private String descripcion;

    private Integer calorias;

    @OneToMany(mappedBy = "plato")
    private List<PlanPlato> planesDondeAparece;


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getId_plato() {
        return id_plato;
    }

    public void setId_plato(Long id_plato) {
        this.id_plato = id_plato;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getCalorias() {
        return calorias;
    }

    public void setCalorias(Integer calorias) {
        this.calorias = calorias;
    }
}
