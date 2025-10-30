package pe.edu.vallegrande.ms_infraestructura.infrastructure.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WaterBoxTransferResponse {
    private Long id;
    private Long waterBoxId;
    private Long oldAssignmentId;
    private Long newAssignmentId;
    private String transferReason;
    private List<String> documents;
    private LocalDateTime createdAt;
}