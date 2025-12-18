package segundo.dam.tuppermania.model;


import jakarta.persistence.*;

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
    private DiaSemana dia_semana; // Enum: LUNES, MARTES, etc.

    @Enumerated(EnumType.STRING)
    private TipoComida tipo_comida; // Enum: DESAYUNO, ALMUERZO, CENA, etc.
}