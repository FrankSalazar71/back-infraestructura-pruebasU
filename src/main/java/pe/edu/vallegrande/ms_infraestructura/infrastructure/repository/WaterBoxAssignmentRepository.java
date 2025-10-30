package pe.edu.vallegrande.ms_infraestructura.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.edu.vallegrande.ms_infraestructura.domain.enums.Status;
import pe.edu.vallegrande.ms_infraestructura.domain.models.WaterBoxAssignment;

import java.util.List;

@Repository
public interface WaterBoxAssignmentRepository extends JpaRepository<WaterBoxAssignment, Long> {
    List<WaterBoxAssignment> findByStatus(Status status);
    
    // Buscar la asignación activa más reciente de un usuario específico
    @Query("SELECT wba FROM WaterBoxAssignment wba WHERE wba.userId = :userId AND wba.status = :status ORDER BY wba.createdAt DESC")
    List<WaterBoxAssignment> findByUserIdAndStatusOrderByCreatedAtDesc(@Param("userId") String userId, @Param("status") Status status);
}