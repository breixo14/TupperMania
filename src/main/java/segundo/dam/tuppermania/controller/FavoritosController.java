package segundo.dam.tuppermania.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import segundo.dam.tuppermania.model.Plato;
import segundo.dam.tuppermania.model.Usuario;
import segundo.dam.tuppermania.repository.PlatoRepository;
import segundo.dam.tuppermania.repository.UsuarioRepository;
import java.util.Optional;

/**
 * Controlador encargado de la gestión de preferencias del usuario (Lista de deseos/Favoritos).
 * Permite marcar recetas para su acceso rápido o futura inclusión en planes.
 */
@Controller
@RequestMapping("/favoritos")
public class FavoritosController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PlatoRepository platoRepository;

    /**
     * Alterna (Toggle) el estado de favorito de un plato para el usuario autenticado.
     * Si ya es favorito, lo elimina; si no, lo añade.
     *
     * @param idPlato Identificador del plato a modificar.
     * @param auth Contexto de seguridad para identificar al usuario actual.
     * @param referer Header HTTP que indica desde qué página se hizo la petición (mejora UX al devolver al usuario a su origen).
     * @return Redirección a la página anterior o a la lista de planes por defecto.
     */
    @GetMapping("/toggle/{idPlato}")
    public String toggleFavorito(@PathVariable String idPlato, Authentication auth, @RequestHeader(value = "referer", required = false) String referer) {
        Usuario usuario = usuarioRepository.findByCorreo(auth.getName()).orElseThrow();
        Plato plato = platoRepository.findById(idPlato).orElseThrow();

        // En Mongo, comparamos mejor por ID explícito para evitar problemas de instancias
        Optional<Plato> existente = usuario.getPlatosFavoritos().stream()
                .filter(p -> p.getIdPlato().equals(idPlato))
                .findFirst();

        if (existente.isPresent()) {
            usuario.getPlatosFavoritos().remove(existente.get()); // Quitar
        } else {
            usuario.getPlatosFavoritos().add(plato); // Añadir
        }

        usuarioRepository.save(usuario);
        return "redirect:" + (referer != null ? referer : "/planes");
    }

    /**
     * Muestra la vista con la galería de platos favoritos del usuario.
     */
    @GetMapping
    public String verFavoritos(Model model, Authentication auth) {
        Usuario usuario = usuarioRepository.findByCorreo(auth.getName()).orElseThrow();
        model.addAttribute("favoritos", usuario.getPlatosFavoritos());
        return "perfil/favoritos";
    }
}