package segundo.dam.tuppermania.model;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "plan_nutricional")
public class PlanNutricional {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_plan;

    private LocalDate fecha_inicio;
    private LocalDate fecha_fin;
    private String objetivo;
    private Integer calorias_totales;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @OneToMany(mappedBy = "plan", cascade = CascadeType.ALL)
    private List<PlanPlato> platosAsignados;
}