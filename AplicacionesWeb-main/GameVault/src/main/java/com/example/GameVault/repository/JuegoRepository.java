package com.example.GameVault.repository;

import com.example.GameVault.model.Juego;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JuegoRepository extends JpaRepository<Juego, Long> {

    List<Juego> findByTituloContainingIgnoreCase(String titulo);

    List<Juego> findByUsuarioId(Long usuarioId);

    @Query("SELECT j FROM Juego j WHERE LOWER(j.descripcion) LIKE LOWER(CONCAT('%', :PALABRA, '%'))")
    List<Juego> buscarPorDescripcion(@Param("palabra")String palabra);

    @Query("SELECT j FROM Juego j ORDER BY j.id DESC")
    List<Juego> obtenerJuegosRecientes();


}
