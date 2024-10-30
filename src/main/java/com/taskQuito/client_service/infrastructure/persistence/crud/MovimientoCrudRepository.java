package com.taskQuito.client_service.infrastructure.persistence.crud;

import com.taskQuito.client_service.infrastructure.persistence.entity.MovimientoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface MovimientoCrudRepository extends JpaRepository<MovimientoEntity, Long>  {
    List<MovimientoEntity> findByCuentaIdAndFechaBetween(Long cuentaId, LocalDateTime fechaInicio, LocalDateTime fechaFin);
}
