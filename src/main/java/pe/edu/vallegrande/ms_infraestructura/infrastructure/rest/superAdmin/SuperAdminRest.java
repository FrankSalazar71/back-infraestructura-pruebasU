package pe.edu.vallegrande.ms_infraestructura.infrastructure.rest.superAdmin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.ms_infraestructura.application.services.IWaterBoxService;
import pe.edu.vallegrande.ms_infraestructura.application.services.IWaterBoxAssignmentService;
import pe.edu.vallegrande.ms_infraestructura.application.services.IWaterBoxTransferService;
import pe.edu.vallegrande.ms_infraestructura.infrastructure.dto.request.WaterBoxRequest;
import pe.edu.vallegrande.ms_infraestructura.infrastructure.dto.request.WaterBoxAssignmentRequest;
import pe.edu.vallegrande.ms_infraestructura.infrastructure.dto.request.WaterBoxTransferRequest;
import pe.edu.vallegrande.ms_infraestructura.infrastructure.dto.response.WaterBoxResponse;
import pe.edu.vallegrande.ms_infraestructura.infrastructure.dto.response.WaterBoxAssignmentResponse;
import pe.edu.vallegrande.ms_infraestructura.infrastructure.dto.response.WaterBoxTransferResponse;

@RestController
@RequestMapping("/api/management")
@RequiredArgsConstructor
public class SuperAdminRest {

    private final IWaterBoxService waterBoxService;
    private final IWaterBoxAssignmentService waterBoxAssignmentService;
    private final IWaterBoxTransferService waterBoxTransferService;

    // ===============================
    // GESTIÓN DE WATER BOXES
    // ===============================

    @PostMapping("/water-boxes")
    public ResponseEntity<WaterBoxResponse> createWaterBox(@Valid @RequestBody WaterBoxRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(waterBoxService.save(request));
    }

    @PutMapping("/water-boxes/{id}")
    public ResponseEntity<WaterBoxResponse> updateWaterBox(@PathVariable Long id, @Valid @RequestBody WaterBoxRequest request) {
        return ResponseEntity.ok(waterBoxService.update(id, request));
    }

    @DeleteMapping("/water-boxes/{id}")
    public ResponseEntity<Void> deleteWaterBox(@PathVariable Long id) {
        waterBoxService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // ===============================
    // GESTIÓN DE WATER BOX ASSIGNMENTS
    // ===============================

    @PostMapping("/water-box-assignments")
    public ResponseEntity<WaterBoxAssignmentResponse> createAssignment(@Valid @RequestBody WaterBoxAssignmentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(waterBoxAssignmentService.save(request));
    }

    @PutMapping("/water-box-assignments/{id}")
    public ResponseEntity<WaterBoxAssignmentResponse> updateAssignment(@PathVariable Long id, @Valid @RequestBody WaterBoxAssignmentRequest request) {
        return ResponseEntity.ok(waterBoxAssignmentService.update(id, request));
    }

    @DeleteMapping("/water-box-assignments/{id}")
    public ResponseEntity<Void> deleteAssignment(@PathVariable Long id) {
        waterBoxAssignmentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // ===============================
    // GESTIÓN DE WATER BOX TRANSFERS
    // ===============================

    @PostMapping("/water-box-transfers")
    public ResponseEntity<WaterBoxTransferResponse> createTransfer(@Valid @RequestBody WaterBoxTransferRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(waterBoxTransferService.save(request));
    }
}
