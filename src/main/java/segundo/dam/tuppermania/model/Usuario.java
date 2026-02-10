package segundo.dam.tuppermania.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import segundo.dam.tuppermania.model.enums.Rol;

import java.util.ArrayList;
import java.util.List;

/**
 * Entidad principal que representa al usuario del sistema.
 * Gestiona credenciales, roles y relaciones con perfiles y planes.
 */
@Document(collection = "usuarios")
public class Usuario {

    @Id
    private String idUsuario;
    private String nombreUsuario;
    private String contrasena;
    private String correo;
    private Rol rol;

    private PerfilFisico perfilFisico;

    @DBRef
    private List<Plato> platosFavoritos = new ArrayList<>();

    // --- GETTERS Y SETTERS ---

    public String getIdUsuario() { return idUsuario; }
    public void setIdUsuario(String idUsuario) { this.idUsuario = idUsuario; }

    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }

    public PerfilFisico getPerfilFisico() { return perfilFisico; }
    public void setPerfilFisico(PerfilFisico perfilFisico) { this.perfilFisico = perfilFisico; }

    public List<Plato> getPlatosFavoritos() { return platosFavoritos; }
    public void setPlatosFavoritos(List<Plato> platosFavoritos) { this.platosFavoritos = platosFavoritos; }
}