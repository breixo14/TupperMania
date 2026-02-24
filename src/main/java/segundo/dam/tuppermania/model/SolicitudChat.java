package segundo.dam.tuppermania.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import segundo.dam.tuppermania.model.enums.EstadoSolicitud;
import java.time.LocalDateTime;

@Document(collection = "solicitudes_chat")
public class SolicitudChat {

    @Id
    private String id;
    private String nombreCliente;
    private String usuarioTelegram;

    private String intencion; // Ej: "Crear plan nutricional"
    private String datosExtraidos; // Ej: "1500 calorias, vegano"
    private String mensajeOriginal;

    private EstadoSolicitud estado = EstadoSolicitud.PENDIENTE;
    private LocalDateTime fechaCreacion = LocalDateTime.now();

    // --- GETTERS Y SETTERS ---
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getNombreCliente() { return nombreCliente; }
    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }
    public String getUsuarioTelegram() { return usuarioTelegram; }
    public void setUsuarioTelegram(String usuarioTelegram) { this.usuarioTelegram = usuarioTelegram; }
    public String getIntencion() { return intencion; }
    public void setIntencion(String intencion) { this.intencion = intencion; }
    public String getDatosExtraidos() { return datosExtraidos; }
    public void setDatosExtraidos(String datosExtraidos) { this.datosExtraidos = datosExtraidos; }
    public String getMensajeOriginal() { return mensajeOriginal; }
    public void setMensajeOriginal(String mensajeOriginal) { this.mensajeOriginal = mensajeOriginal; }
    public EstadoSolicitud getEstado() { return estado; }
    public void setEstado(EstadoSolicitud estado) { this.estado = estado; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
}