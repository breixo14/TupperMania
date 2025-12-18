package segundo.dam.tuppermania.model;

import jakarta.persistence.*;

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

    // Relación inversa: Un plato puede estar en muchos registros de PlanPlato
    @OneToMany(mappedBy = "plato")
    private List<PlanPlato> planesDondeAparece;
}