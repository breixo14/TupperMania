package segundo.dam.tuppermania.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import segundo.dam.tuppermania.model.Plato;
import segundo.dam.tuppermania.model.Usuario;
import segundo.dam.tuppermania.model.enums.Rol;
import segundo.dam.tuppermania.repository.PlatoRepository;
import segundo.dam.tuppermania.repository.UsuarioRepository;

/**
 * Controlador de administración.
 * Gestiona el CRUD completo de los Platos (Biblioteca Global).
 * Protegido por configuración de seguridad (solo rol ADMIN).
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private PlatoRepository platoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // --- GESTIÓN DE PLATOS ---

    @GetMapping("/platos")
    public String listarPlatos(Model model) {
        model.addAttribute("platos", platoRepository.findAll());
        return "admin/platos-lista";
    }

    @GetMapping("/platos/nuevo")
    public String nuevoPlato(Model model) {
        model.addAttribute("plato", new Plato());
        return "admin/platos-form";
    }

    @GetMapping("/platos/editar/{id}")
    public String editarPlato(@PathVariable String id, Model model) {
        Plato plato = platoRepository.findById(id).orElseThrow();
        model.addAttribute("plato", plato);
        return "admin/platos-form";
    }

    @PostMapping("/platos/guardar")
    public String guardarPlato(@ModelAttribute Plato plato) {
        platoRepository.save(plato);
        return "redirect:/admin/platos";
    }

    @GetMapping("/platos/borrar/{id}")
    public String borrarPlato(@PathVariable String id, RedirectAttributes redirectAttributes) {
        try {
            platoRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("mensajeExito", "✅ Plato eliminado correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError", "⚠️ Error al eliminar el plato: " + e.getMessage());
        }
        return "redirect:/admin/platos";
    }

    @GetMapping("/usuarios")
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioRepository.findAll());
        return "admin/usuarios-lista";
    }

    @GetMapping("/usuarios/nuevo")
    public String nuevoUsuario(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("roles", Rol.values());
        return "admin/usuarios-form";
    }

    @PostMapping("/usuarios/guardar")
    public String guardarUsuario(@ModelAttribute Usuario usuario, RedirectAttributes redirectAttributes) {
        if (usuario.getIdUsuario() == null || usuario.getIdUsuario().isEmpty()) {
            usuario.setIdUsuario(null);
        }

        if (usuario.getContrasena() != null && !usuario.getContrasena().isEmpty()) {
            usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        }

        try {
            usuarioRepository.save(usuario);
            redirectAttributes.addFlashAttribute("mensajeExito", "✅ Usuario guardado correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError", "⚠️ Error al guardar usuario.");
        }
        return "redirect:/admin/usuarios";
    }

    @GetMapping("/usuarios/borrar/{id}")
    public String borrarUsuario(@PathVariable String id, RedirectAttributes redirectAttributes) {
        try {
            usuarioRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("mensajeExito", "✅ Usuario eliminado.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError", "⚠️ No se pudo eliminar al usuario.");
        }
        return "redirect:/admin/usuarios";
    }
}