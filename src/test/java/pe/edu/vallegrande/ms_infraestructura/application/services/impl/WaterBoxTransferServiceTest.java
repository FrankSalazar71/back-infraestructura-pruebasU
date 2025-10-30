package pe.edu.vallegrande.ms_infraestructura.application.services.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import pe.edu.vallegrande.ms_infraestructura.infrastructure.repository.WaterBoxTransferRepository;
import pe.edu.vallegrande.ms_infraestructura.infrastructure.repository.WaterBoxAssignmentRepository;
import pe.edu.vallegrande.ms_infraestructura.infrastructure.repository.WaterBoxRepository;
import pe.edu.vallegrande.ms_infraestructura.infrastructure.dto.request.WaterBoxTransferRequest;
import pe.edu.vallegrande.ms_infraestructura.domain.models.WaterBox;
import pe.edu.vallegrande.ms_infraestructura.domain.models.WaterBoxAssignment;
import pe.edu.vallegrande.ms_infraestructura.domain.models.WaterBoxTransfer;
import pe.edu.vallegrande.ms_infraestructura.domain.enums.Status;

@ExtendWith(MockitoExtension.class)
public class WaterBoxTransferServiceTest {

    @Mock
    private WaterBoxTransferRepository transferRepository;

    @Mock
    private WaterBoxAssignmentRepository assignmentRepository;

    @Mock
    private WaterBoxRepository waterBoxRepository;

    @Captor
    private ArgumentCaptor<WaterBoxAssignment> assignmentCaptor;

    @Captor
    private ArgumentCaptor<WaterBox> waterBoxCaptor;

    @Test
    void save_shouldCreateTransferAndUpdateOldAssignmentAndWaterBox() {
    // Console guide:
    // Ejecutar clase completa:
    // mvn -Dtest=WaterBoxTransferServiceTest test
    // Ejecutar método específico:
    // mvn -Dtest=WaterBoxTransferServiceTest#save_shouldCreateTransferAndUpdateOldAssignmentAndWaterBox test
    // Expected console-like results (manual check):
    // - Se devuelve un Transfer con id (ej. 100)
    // - La asignación antigua queda con status=INACTIVE y transferId=100
    // - La WaterBox se guarda con currentAssignmentId = nueva asignación

    WaterBoxTransferService service = new WaterBoxTransferService(transferRepository, assignmentRepository, waterBoxRepository);

    Long waterBoxId = 1L;
    Long oldId = 11L;
    Long newId = 12L;

    WaterBox wb = WaterBox.builder().id(waterBoxId).status(Status.ACTIVE).currentAssignmentId(oldId).build();
    WaterBoxAssignment oldAssignment = WaterBoxAssignment.builder().id(oldId).waterBoxId(waterBoxId).status(Status.ACTIVE).build();
    WaterBoxAssignment newAssignment = WaterBoxAssignment.builder().id(newId).waterBoxId(waterBoxId).status(Status.ACTIVE).build();

    when(waterBoxRepository.findById(waterBoxId)).thenReturn(Optional.of(wb));
    when(assignmentRepository.findById(oldId)).thenReturn(Optional.of(oldAssignment));
    when(assignmentRepository.findById(newId)).thenReturn(Optional.of(newAssignment));
    when(transferRepository.save(any(WaterBoxTransfer.class))).thenAnswer(inv -> {
        WaterBoxTransfer t = inv.getArgument(0);
        t.setId(100L);
        t.setCreatedAt(LocalDateTime.now());
        return t;
    });

    WaterBoxTransferRequest req = WaterBoxTransferRequest.builder()
        .waterBoxId(waterBoxId)
        .oldAssignmentId(oldId)
        .newAssignmentId(newId)
        .transferReason("Cambio de usuario")
        .documents(List.of("doc1", "doc2"))
        .build();

    var resp = service.save(req);

    // Console: Transfer creado -> id=%s, waterBoxId=%s
    assertNotNull(resp);
    assertEquals(100L, resp.getId());
    assertEquals(waterBoxId, resp.getWaterBoxId());
    assertEquals(oldId, resp.getOldAssignmentId());
    assertEquals(newId, resp.getNewAssignmentId());

    // Verificar que la asignación antigua fue actualizada a INACTIVE y se guardó
    verify(assignmentRepository, atLeastOnce()).save(assignmentCaptor.capture());
    WaterBoxAssignment savedOld = assignmentCaptor.getAllValues().stream()
        .filter(a -> a.getId().equals(oldId))
        .findFirst()
        .orElseThrow(() -> new AssertionError("Old assignment was not saved"));

    assertEquals(Status.INACTIVE, savedOld.getStatus());
    assertEquals(100L, savedOld.getTransferId());

    // Verificar que la WaterBox fue actualizada con la nueva asignación actual
    verify(waterBoxRepository).save(waterBoxCaptor.capture());
    WaterBox savedBox = waterBoxCaptor.getValue();
    assertEquals(newId, savedBox.getCurrentAssignmentId());
    // Console: Test finalizado correctamente
    }
}
