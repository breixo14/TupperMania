package segundo.dam.tuppermania.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import segundo.dam.tuppermania.model.SolicitudChat;
import segundo.dam.tuppermania.model.enums.EstadoSolicitud;
import segundo.dam.tuppermania.repository.SolicitudChatRepository;

@Controller
@RequestMapping("/admin/solicitudes")
public class AdminSolicitudesController {

    @Autowired
    private SolicitudChatRepository solicitudRepository;

    @GetMapping
    public String listarSolicitudes(Model model) {
        model.addAttribute("solicitudes", solicitudRepository.findAllByOrderByFechaCreacionDesc());
        return "admin/solicitudes-lista";
    }

    @PostMapping("/cambiar-estado/{id}")
    public String cambiarEstado(@PathVariable String id, @RequestParam EstadoSolicitud nuevoEstado) {
        SolicitudChat solicitud = solicitudRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Solicitud no encontrada"));

        solicitud.setEstado(nuevoEstado);
        solicitudRepository.save(solicitud);

        try {
            java.util.regex.Matcher matcher = java.util.regex.Pattern
                    .compile("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")
                    .matcher(solicitud.getMensajeOriginal());

            String correoExtraido = matcher.find() ? matcher.group() : "null";

            java.util.Map<String, String> payloadN8n = java.util.Map.of(
                    "chatId", solicitud.getUsuarioTelegram(),
                    "correo", correoExtraido
            );

            org.springframework.web.client.RestTemplate restTemplate = new org.springframework.web.client.RestTemplate();
            String urlN8n = "https://nainlopez.app.n8n.cloud/webhook-test/api/webhook/procesar-dieta";
            restTemplate.postForEntity(urlN8n, payloadN8n, String.class);
        } catch (Exception e) {
            System.out.println("❌ Error avisando a n8n: " + e.getMessage());
        }

        return "redirect:/admin/solicitudes";
    }
}