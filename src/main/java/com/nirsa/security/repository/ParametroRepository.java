package com.nirsa.security.repository;

import com.nirsa.security.model.Parametro;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParametroRepository extends JpaRepository<Parametro, Long> {
  // Optional<Parametro> findByName(String name);
}
