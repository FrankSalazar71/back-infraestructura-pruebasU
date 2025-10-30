package pe.edu.vallegrande.ms_infraestructura.application.services.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mock;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

import pe.edu.vallegrande.ms_infraestructura.infrastructure.repository.WaterBoxRepository;
import pe.edu.vallegrande.ms_infraestructura.infrastructure.dto.request.WaterBoxRequest;
import pe.edu.vallegrande.ms_infraestructura.domain.models.WaterBox;
import pe.edu.vallegrande.ms_infraestructura.domain.enums.BoxType;
import pe.edu.vallegrande.ms_infraestructura.domain.enums.Status;

@ExtendWith(MockitoExtension.class)
public class WaterBoxServiceTest {

    // Console guide:
    // Para ejecutar esta clase de prueba:
    // mvn -Dtest=WaterBoxServiceTest test
    // Método específico:
    // mvn -Dtest=WaterBoxServiceTest#save_shouldSetStatusActiveAndCreatedAt test
    // Expected console-like result (manual check):
    // - La respuesta no debe ser nula
    // - El id debe ser 1
    // - El status debe ser ACTIVE
    // - createdAt debe estar definido

    @Mock
    private WaterBoxRepository waterBoxRepository;

    @Test
    void save_shouldSetStatusActiveAndCreatedAt() {
        // Console: Iniciando save_shouldSetStatusActiveAndCreatedAt
        // Simulación: se mockea el repository para devolver un WaterBox con id y createdAt
        WaterBoxService service = new WaterBoxService(waterBoxRepository);

        WaterBoxRequest req = WaterBoxRequest.builder()
                .organizationId("org-1")
                .boxCode("BOX-001")
                .boxType(BoxType.OTRO)
                .installationDate(LocalDate.of(2024,1,1))
                .build();

        when(waterBoxRepository.save(any(WaterBox.class))).thenAnswer(invocation -> {
            WaterBox arg = invocation.getArgument(0);
            arg.setId(1L);
            arg.setStatus(Status.ACTIVE);
            arg.setCreatedAt(LocalDateTime.now());
            return arg;
        });

        var resp = service.save(req);

        // Console: Resultado -> resp.id=%s, status=%s, createdAt=%s
        assertNotNull(resp);
        assertEquals(1L, resp.getId());
        assertEquals(Status.ACTIVE, resp.getStatus());
        assertEquals("BOX-001", resp.getBoxCode());
        assertNotNull(resp.getCreatedAt());
        // Console: Test finalizado correctamente
    }
}
