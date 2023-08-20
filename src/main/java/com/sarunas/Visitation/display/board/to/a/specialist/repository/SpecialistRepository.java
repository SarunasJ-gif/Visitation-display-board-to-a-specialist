package com.sarunas.Visitation.display.board.to.a.specialist.repository;

import com.sarunas.Visitation.display.board.to.a.specialist.entities.SpecialistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpecialistRepository extends JpaRepository<SpecialistEntity, Long> {
    Optional<SpecialistEntity> findByUsername(String name);
    Optional<SpecialistEntity> findByEmail(String email);
}
