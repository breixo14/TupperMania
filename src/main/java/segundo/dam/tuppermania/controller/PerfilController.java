package segundo.dam.tuppermania.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import segundo.dam.tuppermania.model.PerfilFisico;

@Controller
@RequestMapping("/perfil")
public class PerfilController {

    @GetMapping("/nuevo")
    public String mostrarFormularioPerfil(Model model) {
        model.addAttribute("perfil", new PerfilFisico());
        return "perfil/formulario"; // Plantilla HTML
    }

    // Método placeholder para cuando implementemos la lógica de guardar
    @PostMapping("/guardar")
    public String guardarPerfil(PerfilFisico perfil) {
        System.out.println("Guardando perfil (simulado)... Peso: " + perfil.getPeso());
        return "redirect:/home";
    }
}