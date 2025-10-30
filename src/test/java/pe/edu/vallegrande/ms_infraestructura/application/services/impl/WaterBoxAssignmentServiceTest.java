package pe.edu.vallegrande.ms_infraestructura.application.services.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

import pe.edu.vallegrande.ms_infraestructura.infrastructure.repository.WaterBoxRepository;
import pe.edu.vallegrande.ms_infraestructura.infrastructure.repository.WaterBoxAssignmentRepository;
import pe.edu.vallegrande.ms_infraestructura.domain.models.WaterBox;
import pe.edu.vallegrande.ms_infraestructura.domain.enums.Status;
import pe.edu.vallegrande.ms_infraestructura.infrastructure.dto.request.WaterBoxAssignmentRequest;
import pe.edu.vallegrande.ms_infraestructura.infrastructure.exceptions.BadRequestException;

@ExtendWith(MockitoExtension.class)
public class WaterBoxAssignmentServiceTest {

    @Mock
    private WaterBoxRepository waterBoxRepository;

    @Mock
    private WaterBoxAssignmentRepository assignmentRepository;

    @Test
    void save_shouldThrowBadRequest_whenWaterBoxInactive() {
    // Console guide:
    // Ejecutar clase completa:
    // mvn -Dtest=WaterBoxAssignmentServiceTest test
    // Ejecutar método específico:
    // mvn -Dtest=WaterBoxAssignmentServiceTest#save_shouldThrowBadRequest_whenWaterBoxInactive test
    // Console expected: se lanza BadRequestException porque la WaterBox está INACTIVE

    WaterBoxAssignmentService service = new WaterBoxAssignmentService(assignmentRepository, waterBoxRepository);

    WaterBoxAssignmentRequest req = WaterBoxAssignmentRequest.builder()
        .waterBoxId(10L)
        .userId("user-1")
        .startDate(LocalDateTime.now())
        .monthlyFee(new BigDecimal("10.00"))
        .build();

    when(waterBoxRepository.findById(10L)).thenReturn(Optional.of(
        WaterBox.builder().id(10L).status(Status.INACTIVE).build()
    ));

    // Console: intentando guardar asignación sobre waterBox id=10 (INACTIVE)
    assertThrows(BadRequestException.class, () -> service.save(req));
    // Console: BadRequestException lanzada como se esperaba
    }
}
