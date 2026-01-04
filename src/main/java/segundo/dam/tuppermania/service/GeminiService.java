package segundo.dam.tuppermania.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import segundo.dam.tuppermania.model.PerfilFisico;
import segundo.dam.tuppermania.model.dto.DietaGeneradaDTO;

@Service
public class GeminiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    private final ObjectMapper objectMapper;

    public GeminiService() {
        this.objectMapper = new ObjectMapper();
    }

    public DietaGeneradaDTO generarDieta(PerfilFisico perfil) {
        Client client = Client.builder()
                .apiKey(apiKey)
                .build();

        String prompt = construirPrompt(perfil);

        try {
            GenerateContentResponse response = client.models.generateContent(
                    "gemini-2.5-flash",
                    prompt,
                    null
            );

            String textoRespuesta = response.text();

            return parsearRespuestaGemini(textoRespuesta);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al conectar con la IA de Google: " + e.getMessage());
        }
    }

    private String construirPrompt(PerfilFisico p) {
        return """
            Actúa como un nutricionista experto. Crea un plan semanal JSON para:
            - Objetivo: %s
            - Peso: %s kg, Altura: %s cm, Edad: %s
            - Actividad: %s
            - Sexo: %s
            - Alergias: %s
            - Intolerancias: %s
            
            IMPORTANTE: Responde ÚNICAMENTE con un JSON válido siguiendo ESTRICTAMENTE esta estructura, sin texto adicional ni markdown (no uses ```json):
            {
                {
                "tituloPlan": "Nombre del plan",
                "explicacion": "Resumen",
                "listaCompraConsolidada": [
                                      "12 Huevos (Total semana)",
                                      "500g de Pechuga de Pollo",
                                      "1kg de Arroz Integral"
                                  ],
                "dias": [
                    {
                    "diaSemana": "LUNES",
                    "comidas": [
                        {\s
                            "tipoComida": "DESAYUNO",\s
                            "nombrePlato": "Tortitas de Avena",\s
                            "descripcion": "Receta rápida",\s
                            "caloriasAprox": 300,
                            "ingredientes": ["50g Avena", "1 Huevo", "Canela"]
                        }
                        // ... resto de comidas
                    ]
                    }
                // ... resto de días
                ]
                }
                ... Repetir hasta DOMINGO
                Asegúrate OBLIGATORIAMENTE de incluir objetos para los 7 días de la semana: LUNES, MARTES, MIERCOLES, JUEVES, VIERNES, SABADO, DOMINGO.
              ]
            }
            Genera al menos Desayuno, Comida y Cena para cada día.
            
            INSTRUCCIONES CLAVE PARA LA LISTA DE COMPRA:
            1. Recorre todas las recetas de la semana.
            2. Agrupa los ingredientes iguales.
            3. SUMA sus cantidades (Ej: si el lunes hay 2 huevos y el viernes 2 huevos, pon "4 Huevos" en la lista consolidada).
            4. Ordena la lista alfabéticamente.
            """.formatted(
                p.getObjetivo(), p.getPeso(), p.getAltura(), p.getEdad(),
                p.getNivelActividad(), p.getSexo(),
                p.getAlergias() != null ? p.getAlergias() : "Ninguna",
                p.getIntolerancias() != null ? p.getIntolerancias() : "Ninguna"
        );
    }

    private DietaGeneradaDTO parsearRespuestaGemini(String textoJson) {
        try {
            if (textoJson.contains("```json")) {
                textoJson = textoJson.replace("```json", "").replace("```", "");
            }
            return objectMapper.readValue(textoJson.trim(), DietaGeneradaDTO.class);

        } catch (Exception e) {
            throw new RuntimeException("Error al leer el JSON de la IA: " + e.getMessage() + " | Respuesta recibida: " + textoJson);
        }
    }
}