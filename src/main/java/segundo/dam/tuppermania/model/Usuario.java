package segundo.dam.tuppermania.model;

import jakarta.persistence.*;
import segundo.dam.tuppermania.model.enums.Rol;

import java.util.List;

@Entity
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_usuario;
    private String nombre_usuario;
    private String contrasena;
    private String correo;

    @Enumerated(EnumType.STRING)
    private Rol rol; // Debes crear el Enum Rol

    @OneToOne(mappedBy = "usuario", cascade = CascadeType.ALL)
    private PerfilFisico perfilFisico;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    private List<PlanNutricional> planes;


    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public void setNombre_usuario(String nombre_usuario) {
        this.nombre_usuario = nombre_usuario;
    }

    public Long getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Long id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public PerfilFisico getPerfilFisico() {
        return perfilFisico;
    }

    public void setPerfilFisico(PerfilFisico perfilFisico) {
        this.perfilFisico = perfilFisico;
    }

    public List<PlanNutricional> getPlanes() {
        return planes;
    }

    public void setPlanes(List<PlanNutricional> planes) {
        this.planes = planes;
    }
}