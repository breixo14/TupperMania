package segundo.dam.tuppermania.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import segundo.dam.tuppermania.model.PlanNutricional;
import segundo.dam.tuppermania.model.Plato;
import segundo.dam.tuppermania.model.Usuario;
import segundo.dam.tuppermania.model.enums.Rol;
import segundo.dam.tuppermania.repository.ConsultasRepositoryImpl;
import segundo.dam.tuppermania.repository.PlanNutricionalRepository;
import segundo.dam.tuppermania.repository.PlatoRepository;
import segundo.dam.tuppermania.repository.UsuarioRepository;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/consultas")
public class ConsultasController {

    @Autowired
    private ConsultasRepositoryImpl consultasRepository;

    @Autowired
    private UsuarioRepository usuarioRepo;
    @Autowired
    private PlatoRepository platoRepo;
    @Autowired
    private PlanNutricionalRepository planRepo;

    // CONSULTA 1: Usuarios por Rol
    @GetMapping("/usuarios")
    public List<Usuario> buscarUsuariosPorRol(@RequestParam(defaultValue = "ADMIN") Rol rol) {
        return usuarioRepo.findByRol(rol);
    }

    // CONSULTA 2: Platos por Nombre (Contiene)
    @GetMapping("/platos")
    public List<Plato> buscarPlatos(@RequestParam(defaultValue = "Pollo") String nombre) {
        return platoRepo.findByNombreContainingIgnoreCase(nombre);
    }

    // CONSULTA 3: Planes por Rango de Calorías
    @GetMapping("/planes")
    public List<PlanNutricional> buscarPlanesPorCalorias(@RequestParam(defaultValue = "0") Integer min,
                                                         @RequestParam(defaultValue = "5000") Integer max) {
        return planRepo.findByCaloriasTotalesBetween(min, max);
    }

    @GetMapping("/populares")
    public List<Map> getPlatosPopulares(@RequestParam(defaultValue = "5") int limite) {
        return consultasRepository.obtenerPlatosMasPopulares(limite);
    }

    @GetMapping("/objetivos")
    public List<Map> getPromedioObjetivos() {
        return consultasRepository.obtenerPromedioCaloriasPorObjetivo();
    }

    @GetMapping("/inactivos")
    public List<Map> getUsuariosInactivos() {
        return consultasRepository.obtenerUsuariosSinPlanes();
    }
}