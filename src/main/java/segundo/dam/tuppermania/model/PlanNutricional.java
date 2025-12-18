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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Long getId_plan() {
        return id_plan;
    }

    public void setId_plan(Long id_plan) {
        this.id_plan = id_plan;
    }

    public LocalDate getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(LocalDate fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public LocalDate getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(LocalDate fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public Integer getCalorias_totales() {
        return calorias_totales;
    }

    public void setCalorias_totales(Integer calorias_totales) {
        this.calorias_totales = calorias_totales;
    }

    public List<PlanPlato> getPlatosAsignados() {
        return platosAsignados;
    }

    public void setPlatosAsignados(List<PlanPlato> platosAsignados) {
        this.platosAsignados = platosAsignados;
    }



}