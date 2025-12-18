package segundo.dam.tuppermania.model;


import jakarta.persistence.*;
import segundo.dam.tuppermania.model.enums.DiaSemana;
import segundo.dam.tuppermania.model.enums.TipoComida;

@Entity
@Table(name = "plan_plato")
public class PlanPlato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_plan_plato;

    @ManyToOne
    @JoinColumn(name = "id_plan")
    private PlanNutricional plan;

    @ManyToOne
    @JoinColumn(name = "id_plato")
    private Plato plato;

    @Enumerated(EnumType.STRING)
    private DiaSemana dia_semana;

    @Enumerated(EnumType.STRING)
    private TipoComida tipo_comida;
}