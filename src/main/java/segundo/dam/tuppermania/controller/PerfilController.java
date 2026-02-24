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

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/nuevo")
    public String mostrarFormularioPerfil(Model model, Authentication auth) {
        Usuario usuario = usuarioRepository.findByCorreo(auth.getName()).orElseThrow();

        if (usuario.getPerfilFisico() != null) {
            model.addAttribute("perfil", usuario.getPerfilFisico());
        } else {
            model.addAttribute("perfil", new PerfilFisico());
        }

        model.addAttribute("sexos", Sexo.values());
        model.addAttribute("nivelesActividad", NivelActividad.values());
        model.addAttribute("objetivos", Objetivo.values());

        return "perfil/formulario";
    }

    @PostMapping("/guardar")
    public String guardarPerfil(@ModelAttribute PerfilFisico perfil, Authentication auth) {
        Usuario usuario = usuarioRepository.findByCorreo(auth.getName()).orElseThrow();

        usuario.setPerfilFisico(perfil);
        usuarioRepository.save(usuario);

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

        if (!passwordEncoder.matches(actual, usuario.getContrasena())) {
            redirectAttributes.addFlashAttribute("mensajeError", "⛔ La contraseña actual no es correcta.");
            return "redirect:/perfil/contrasena";
        }

        if (!nueva.equals(confirmacion)) {
            redirectAttributes.addFlashAttribute("mensajeError", "⚠️ Las nuevas contraseñas no coinciden.");
            return "redirect:/perfil/contrasena";
        }

        usuario.setContrasena(passwordEncoder.encode(nueva));
        usuarioRepository.save(usuario);

        redirectAttributes.addFlashAttribute("mensajeExito", "✅ Contraseña actualizada correctamente.");
        return "redirect:/perfil/contrasena";
    }
}