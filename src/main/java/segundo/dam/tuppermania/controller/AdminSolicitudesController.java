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

        return "redirect:/admin/solicitudes";
    }
}