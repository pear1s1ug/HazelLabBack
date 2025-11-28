package com.carrie.hazellabev2.repositories;

import com.carrie.hazellabev2.entities.Boleta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoletaRepository extends JpaRepository<Boleta, Long> {
    List<Boleta> findByUsuarioIdOrderByFechaEmisionDesc(Long usuarioId);
    Optional<Boleta> findByNumeroBoleta(String numeroBoleta);
    
    @Query("SELECT MAX(CAST(SUBSTRING(b.numeroBoleta, 2, 3) AS int)) FROM Boleta b WHERE YEAR(b.fechaEmision) = :year")
    Optional<Integer> findMaxNumeroByYear(@Param("year") int year);
}