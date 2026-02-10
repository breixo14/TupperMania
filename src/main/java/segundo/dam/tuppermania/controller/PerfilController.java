package segundo.dam.tuppermania.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import segundo.dam.tuppermania.model.PerfilFisico;
import segundo.dam.tuppermania.model.Usuario;
import segundo.dam.tuppermania.model.enums.NivelActividad;
import segundo.dam.tuppermania.model.enums.Objetivo;
import segundo.dam.tuppermania.model.enums.Sexo;
import segundo.dam.tuppermania.repository.UsuarioRepository;

/**
 * Gestiona la creación y edición del perfil físico del usuario.
 * Garantiza que cada usuario tenga vinculado un único perfil.
 */
@Controller
@RequestMapping("/perfil")
public class PerfilController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // ELIMINADO: PerfilFisicoRepository (ya no se usa, el perfil va dentro del usuario)

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/nuevo")
    public String mostrarFormularioPerfil(Model model, Authentication auth) {
        Usuario usuario = usuarioRepository.findByCorreo(auth.getName()).orElseThrow();

        // Pasamos al modelo el perfil existente o uno nuevo vacío
        if (usuario.getPerfilFisico() != null) {
            model.addAttribute("perfil", usuario.getPerfilFisico());
        } else {
            model.addAttribute("perfil", new PerfilFisico());
        }

        // Añadimos los Enums al modelo por si la vista los necesita para los selects
        model.addAttribute("sexos", Sexo.values());
        model.addAttribute("nivelesActividad", NivelActividad.values());
        model.addAttribute("objetivos", Objetivo.values());

        return "perfil/formulario";
    }

    @PostMapping("/guardar")
    public String guardarPerfil(@ModelAttribute PerfilFisico perfil, Authentication auth) {
        Usuario usuario = usuarioRepository.findByCorreo(auth.getName()).orElseThrow();

        // LÓGICA MONGO:
        // 1. No gestionamos IDs de perfil (es un objeto embebido).
        // 2. No gestionamos relación inversa (perfil.setUsuario).
        // 3. Simplemente actualizamos el campo en el documento Usuario.

        usuario.setPerfilFisico(perfil);
        usuarioRepository.save(usuario); // Esto actualiza todo el documento en Mongo

        System.out.println("✅ Perfil físico actualizado para: " + usuario.getNombreUsuario());

        return "redirect:/planes";
    }

    @GetMapping("/contrasena")
    public String formContrasena() {
        return "perfil/contrasena";
    }

    @PostMapping("/contrasena")
    public String cambiarContrasena(@RequestParam String actual,
                                    @RequestParam String nueva,
                                    @RequestParam String confirmacion,
                                    Authentication auth,
                                    RedirectAttributes redirectAttributes) {

        Usuario usuario = usuarioRepository.findByCorreo(auth.getName()).orElseThrow();

        // Verificar que la contraseña actual es correcta
        if (!passwordEncoder.matches(actual, usuario.getContrasena())) {
            redirectAttributes.addFlashAttribute("mensajeError", "⛔ La contraseña actual no es correcta.");
            return "redirect:/perfil/contrasena";
        }

        // Verificar que la nueva y la confirmación coinciden
        if (!nueva.equals(confirmacion)) {
            redirectAttributes.addFlashAttribute("mensajeError", "⚠️ Las nuevas contraseñas no coinciden.");
            return "redirect:/perfil/contrasena";
        }

        // Guardar la nueva contraseña encriptada
        usuario.setContrasena(passwordEncoder.encode(nueva));
        usuarioRepository.save(usuario);

        redirectAttributes.addFlashAttribute("mensajeExito", "✅ Contraseña actualizada correctamente.");
        return "redirect:/perfil/contrasena";
    }
}