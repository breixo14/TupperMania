package segundo.dam.tuppermania.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import segundo.dam.tuppermania.model.*;
import segundo.dam.tuppermania.model.enums.DiaSemana;
import segundo.dam.tuppermania.model.enums.Rol;
import segundo.dam.tuppermania.model.enums.TipoComida;
import segundo.dam.tuppermania.repository.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Componente de arranque de la aplicación.
 * Se ejecuta automáticamente al iniciar el contexto de Spring.
 * Su responsabilidad principal es garantizar la existencia de datos esenciales,
 * como la cuenta de administrador, evitando configuraciones manuales en base de datos.
 */
@Configuration
public class DataInitializer {

    /**
     * Verifica la existencia del usuario administrador por defecto y lo crea si es necesario.
     * Utiliza el PasswordEncoder para asegurar que las credenciales no se guarden en texto plano.
     *
     * @param encoder Componente de encriptación inyectado (BCrypt).
     * @return CommandLineRunner que ejecuta la lógica de inicialización.
     */
    @Bean
    CommandLineRunner initDatabase(UsuarioRepository usuarioRepo,
                                   PlatoRepository platoRepo,
                                   PlanNutricionalRepository planRepo,
                                   PasswordEncoder encoder) {
        return args -> {

            System.out.println("[INICIO] Verificando datos base...");

            if (usuarioRepo.findByCorreo("admin@tuppermania.com").isEmpty()) {
                Usuario admin = new Usuario();
                admin.setNombreUsuario("Administrador");
                admin.setCorreo("admin@tuppermania.com");
                admin.setContrasena(encoder.encode("admin123"));
                admin.setRol(Rol.ADMIN);
                usuarioRepo.save(admin);
                System.out.println("   -> Usuario Admin listo.");
            }

            if (platoRepo.count() == 0) {
                platoRepo.save(crearPlato("Pollo con Arroz", 400, "Pollo, Arroz"));
                platoRepo.save(crearPlato("Ensalada Mixta", 200, "Lechuga, Tomate, Atún"));
                System.out.println("   -> Catálogo base inicializado.");
            }

            System.out.println("\n--- INICIANDO CICLO DE DEMOSTRACIÓN CRUD ---");

            String emailDemo = "bot_demo@crud.com";

            usuarioRepo.findByCorreo(emailDemo).ifPresent(u -> {
                planRepo.findByUsuarioId(u.getIdUsuario()).forEach(p -> planRepo.delete(p));
                usuarioRepo.delete(u);
                System.out.println("[CLEANUP] Restos de ejecución anterior eliminados.");
            });

            try {
                // --- 1. CREATE USUARIO ---
                Usuario usuarioDemo = new Usuario();
                usuarioDemo.setNombreUsuario("Bot Demo CRUD");
                usuarioDemo.setCorreo(emailDemo);
                usuarioDemo.setContrasena(encoder.encode("1234"));
                usuarioDemo.setRol(Rol.USUARIO);

                usuarioDemo = usuarioRepo.save(usuarioDemo);
                System.out.println("✅ [CREATE] Usuario creado: " + usuarioDemo.getNombreUsuario() + " (Rol: USER)");

                // --- 2. CREATE PLATO ESPECÍFICO ---
                Plato platoDemo = crearPlato("Pizza Demo CRUD", 800, "Masa, Tomate, Queso");
                platoDemo = platoRepo.save(platoDemo);
                System.out.println("✅ [CREATE] Plato creado: " + platoDemo.getNombre());

                // --- 3. CREATE PLAN ---
                PlanNutricional plan = new PlanNutricional();
                plan.setUsuarioId(usuarioDemo.getIdUsuario());
                plan.setObjetivo("GANAR_PESO_TEST");
                plan.setFechaInicio(LocalDate.now());
                plan.setFechaFin(LocalDate.now().plusDays(7));
                plan.setCaloriasTotales(3000);
                plan.setListaCompraResumida("Harina; Tomate; Queso");

                List<PlanPlato> comidas = new ArrayList<>();
                comidas.add(new PlanPlato(DiaSemana.VIERNES, TipoComida.CENA, platoDemo));
                plan.setPlatosAsignados(comidas);

                plan = planRepo.save(plan);
                System.out.println("✅ [CREATE] Plan creado con ID: " + plan.getIdPlan() + " para el usuario.");

                // --- 4. READ ---
                System.out.println("👀 [READ] Verificando datos en DB...");
                boolean existePlan = planRepo.existsById(plan.getIdPlan());
                System.out.println("   -> ¿El plan existe en Mongo?: " + (existePlan ? "SÍ" : "NO"));

                // --- 5. UPDATE ---
                plan.setObjetivo("DEFINICION_EXTREMA");
                plan.setCaloriasTotales(1500);
                plan = planRepo.save(plan);
                System.out.println("✅ [UPDATE] Plan modificado: Objetivo cambiado a 'DEFINICION_EXTREMA' y Calorías a 1500.");

                // --- 6. DELETE ---
                System.out.println("🗑️ [DELETE] Iniciando borrado de datos de prueba...");

                planRepo.delete(plan);
                System.out.println("   -> Plan eliminado.");

                usuarioRepo.delete(usuarioDemo);
                System.out.println("   -> Usuario eliminado.");

                platoRepo.delete(platoDemo);
                System.out.println("   -> Plato eliminado.");

                System.out.println("🏁 --- CICLO CRUD COMPLETADO EXITOSAMENTE ---");

            } catch (Exception e) {
                System.err.println("❌ ERROR EN LA DEMO: " + e.getMessage());
                e.printStackTrace();
            }
        };
    }

    private Plato crearPlato(String nombre, Integer cal, String ingr) {
        Plato p = new Plato();
        p.setNombre(nombre);
        p.setCalorias(cal);
        p.setIngredientes(ingr);
        p.setDescripcion("Plato de prueba: " + nombre);
        return p;
    }
}