package com.taskQuito.client_service.infrastructure.persistence.crud;

import com.taskQuito.client_service.infrastructure.persistence.entity.PersonaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonaCrudRepository extends JpaRepository<PersonaEntity, Long>{
}
