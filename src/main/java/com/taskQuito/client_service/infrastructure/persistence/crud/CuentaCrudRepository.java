package com.taskQuito.client_service.infrastructure.persistence.crud;

import com.taskQuito.client_service.infrastructure.persistence.entity.CuentaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CuentaCrudRepository extends JpaRepository<CuentaEntity, String> {
    Optional<CuentaEntity> findByNumeroCuenta(String numeroCuenta);
    Optional<CuentaEntity> findByNumeroCuentaAndClienteId(String numeroCuenta, Long clienteId);
    List<CuentaEntity> findByClienteId(Long cliente);
}
