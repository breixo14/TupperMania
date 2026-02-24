package segundo.dam.tuppermania.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import segundo.dam.tuppermania.model.*;
import segundo.dam.tuppermania.model.dto.*;
import segundo.dam.tuppermania.model.enums.DiaSemana;
import segundo.dam.tuppermania.model.enums.TipoComida;
import segundo.dam.tuppermania.repository.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PlanNutricionalService {

    @Autowired
    private GeminiService geminiService;

    @Autowired
    private PlanNutricionalRepository planRepository;

    @Autowired
    private PlatoRepository platoRepository;

    public PlanNutricional generarYGuardarDieta(Usuario usuario) {
        PerfilFisico perfil = usuario.getPerfilFisico();
        if (perfil == null) {
            throw new RuntimeException("El usuario no tiene perfil físico completado");
        }

        DietaGeneradaDTO dietaDTO = geminiService.generarDieta(perfil);

        PlanNutricional plan = new PlanNutricional();
        plan.setUsuarioId(usuario.getIdUsuario());
        plan.setFechaInicio(LocalDate.now());
        plan.setFechaFin(LocalDate.now().plusDays(7));
        plan.setObjetivo(perfil.getObjetivo().name());
        plan.setCaloriasTotales(calcularCaloriasTotales(dietaDTO));

        if (dietaDTO.getListaCompraConsolidada() != null) {
            plan.setListaCompraResumida(String.join(";", dietaDTO.getListaCompraConsolidada()));
        }

        List<PlanPlato> listaComidas = new ArrayList<>();

        for (DiaDietaDTO diaDTO : dietaDTO.getDias()) {
            DiaSemana diaEnum;
            try {
                diaEnum = DiaSemana.valueOf(diaDTO.getDiaSemana().toUpperCase());
            } catch (IllegalArgumentException e) { continue; }

            for (ComidaDTO comidaDTO : diaDTO.getComidas()) {
                Plato plato = new Plato();
                plato.setNombre(comidaDTO.getNombrePlato());
                plato.setDescripcion(comidaDTO.getDescripcion());
                plato.setCalorias(comidaDTO.getCaloriasAprox());
                plato.setIngredientes(comidaDTO.getIngredientes() != null ?
                        String.join(", ", comidaDTO.getIngredientes()) : "Consultar receta");

                plato = platoRepository.save(plato);

                TipoComida tipoEnum;
                try {
                    tipoEnum = TipoComida.valueOf(comidaDTO.getTipoComida().toUpperCase());
                } catch (IllegalArgumentException e) { tipoEnum = TipoComida.COMIDA; }

                PlanPlato planPlato = new PlanPlato(diaEnum, tipoEnum, plato);
                listaComidas.add(planPlato);
            }
        }

        plan.setPlatosAsignados(listaComidas);
        return planRepository.save(plan);
    }

    public void asignarPlatoManual(String idPlan, String idPlato, String dia, String comida) {
        PlanNutricional plan = obtenerPlanPorId(idPlan);
        Plato plato = platoRepository.findById(idPlato)
                .orElseThrow(() -> new RuntimeException("Plato no encontrado"));

        DiaSemana diaEnum = DiaSemana.valueOf(dia);
        TipoComida comidaEnum = TipoComida.valueOf(comida);

        Optional<PlanPlato> existente = plan.getPlatosAsignados().stream()
                .filter(p -> p.getDiaSemana() == diaEnum && p.getTipoComida() == comidaEnum)
                .findFirst();

        if (existente.isPresent()) {
            PlanPlato pp = existente.get();
            pp.setPlatoId(plato.getIdPlato());
            pp.setNombrePlato(plato.getNombre());
            pp.setCalorias(plato.getCalorias());
        } else {
            PlanPlato nuevo = new PlanPlato(diaEnum, comidaEnum, plato);
            plan.getPlatosAsignados().add(nuevo);
        }

        planRepository.save(plan);
    }

    public List<PlanNutricional> obtenerPlanesPorUsuario(Usuario usuario) {
        return planRepository.findByUsuarioId(usuario.getIdUsuario());
    }

    public PlanNutricional obtenerPlanPorId(String id) {
        return planRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Plan no encontrado: " + id));
    }

    private Integer calcularCaloriasTotales(DietaGeneradaDTO dto) {
        int totalSemana = dto.getDias().stream()
                .flatMap(d -> d.getComidas().stream())
                .mapToInt(c -> c.getCaloriasAprox() != null ? c.getCaloriasAprox() : 0)
                .sum();
        return totalSemana / 7;
    }
}