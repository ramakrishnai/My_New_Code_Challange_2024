package com.scrum.retro.repository;

import com.scrum.retro.entity.Retrospective;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ScrumCeremonyRetroRepository extends JpaRepository<Retrospective, Long> {
    Optional<Retrospective> findByDate(LocalDate date);
}
