package com.sarunas.Visitation.display.board.to.a.specialist.repository;

import com.sarunas.Visitation.display.board.to.a.specialist.entities.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, Long> {
}
