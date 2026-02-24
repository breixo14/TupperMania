package segundo.dam.tuppermania.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import segundo.dam.tuppermania.model.PlanNutricional;
import segundo.dam.tuppermania.model.PlanPlato;
import segundo.dam.tuppermania.model.SolicitudChat;
import segundo.dam.tuppermania.model.Usuario;
import segundo.dam.tuppermania.model.enums.DiaSemana;
import segundo.dam.tuppermania.model.enums.EstadoSolicitud;
import segundo.dam.tuppermania.repository.SolicitudChatRepository;
import segundo.dam.tuppermania.repository.UsuarioRepository;
import segundo.dam.tuppermania.service.PlanNutricionalService;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/webhook")
@CrossOrigin(origins = "*")
public class WebhookController {

    @Autowired
    private SolicitudChatRepository solicitudRepository;

    @Autowired
    private UsuarioRepository usuarioRepo;

    @Autowired
    private PlanNutricionalService planNutricionalService;

    @PostMapping("/nueva-solicitud")
    public ResponseEntity<?> recibirSolicitudDeChat(@RequestBody SolicitudChat solicitud) {
        solicitud.setEstado(EstadoSolicitud.PENDIENTE);
        solicitud.setFechaCreacion(LocalDateTime.now());
        solicitudRepository.save(solicitud);
        System.out.println("🤖 [WEBHOOK] Nueva solicitud recibida de Telegram de: " + solicitud.getUsuarioTelegram());
        return ResponseEntity.ok(Map.of("status", "success", "mensaje", "Solicitud almacenada en Mongo"));
    }

    @PostMapping("/generar-desde-bot")
    public ResponseEntity<?> generarPlanDesdeBot(@RequestBody Map<String, String> payload) {
        String correo = payload.get("correo");

        if (correo == null || correo.trim().isEmpty() || correo.equalsIgnoreCase("null")) {
            return ResponseEntity.ok(Map.of("mensajeBot", "⚠️ Por favor, dime tu correo electrónico para buscar tu perfil físico. Ej: 'Soy admin@tuppermania.com y quiero una dieta'"));
        }

        Optional<Usuario> usuarioOpt = usuarioRepo.findByCorreo(correo);

        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.ok(Map.of("mensajeBot", "❌ No he encontrado ninguna cuenta con el correo: " + correo + ". ¡Regístrate en la web primero!"));
        }

        Usuario usuario = usuarioOpt.get();

        if (usuario.getPerfilFisico() == null) {
            return ResponseEntity.ok(Map.of("mensajeBot", "❌ Hola " + usuario.getNombreUsuario() + ", aún no has completado tu Perfil Físico en la web. Entra a configurarlo para poder generarte la dieta."));
        }

        try {
            PlanNutricional planGuardado = planNutricionalService.generarYGuardarDieta(usuario);

            StringBuilder sb = new StringBuilder();
            sb.append("🔥 Calorías medias por día: ").append(planGuardado.getCaloriasTotales()).append(" kcal\n\n");
            sb.append("🥗 TU MENÚ SEMANAL:\n");

            if (planGuardado.getPlatosAsignados() != null) {
                DiaSemana diaActual = null;
                for (PlanPlato comida : planGuardado.getPlatosAsignados()) {
                    if (diaActual != comida.getDiaSemana()) {
                        diaActual = comida.getDiaSemana();
                        sb.append("\n📅 ").append(diaActual).append(":\n");
                    }
                    sb.append("🔸 ").append(comida.getTipoComida()).append(": ")
                            .append(comida.getNombrePlato()).append(" (")
                            .append(comida.getCalorias()).append(" kcal)\n");
                }
            }
            return ResponseEntity.ok(Map.of("mensajeBot", sb.toString()));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(Map.of("mensajeBot", "⚠️ Hubo un error al generar y guardar tu dieta. Revisa la consola."));
        }
    }
}