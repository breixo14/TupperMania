package segundo.dam.tuppermania.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import segundo.dam.tuppermania.model.SolicitudChat;
import segundo.dam.tuppermania.model.enums.EstadoSolicitud;
import segundo.dam.tuppermania.repository.SolicitudChatRepository;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/webhook")
@CrossOrigin(origins = "*")
public class WebhookController {

    @Autowired
    private SolicitudChatRepository solicitudRepository;

    /**
     * Endpoint que recibirá el POST desde n8n.
     */
    @PostMapping("/nueva-solicitud")
    public ResponseEntity<String> recibirSolicitudDeChat(@RequestBody SolicitudChat solicitud) {
        solicitud.setEstado(EstadoSolicitud.PENDIENTE);
        solicitud.setFechaCreacion(LocalDateTime.now());

        solicitudRepository.save(solicitud);

        System.out.println("🤖 [WEBHOOK] Nueva solicitud recibida de Discord de: " + solicitud.getUsuarioDiscord());

        return ResponseEntity.ok("Solicitud almacenada correctamente en MongoDB Atlas");
    }
}