package com.example.GameVault.service;

import com.example.GameVault.model.Juego;
import com.example.GameVault.repository.JuegoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
public class JuegoService {

    // Misma carpeta que sirve WebConfig en /uploads/**
    private static final String UPLOAD_DIR = "src/main/resources/static/uploads";

    @Autowired
    private JuegoRepository juegoRepository;

    public List<Juego> listarTodos() {
        return juegoRepository.findAll();
    }

    public Juego obtenerJuegoPorId(Long id){
        return juegoRepository.findById(id).orElse(null);

    }

    public void eliminarJuego(Long id){
        juegoRepository.deleteById(id);

    }

    public List<Juego> obtenerJuegosPorUsuario(Long usuarioId){
        return juegoRepository.findByUsuarioId(usuarioId);
    }

    public List<Juego> buscarPorTitulo(String palabra) {
        if (palabra == null || palabra.trim().isEmpty()) {
            return listarTodos();
        }
        return juegoRepository.findByTituloContainingIgnoreCase(palabra);
    }


    public Juego guardarJuego(Juego juego, MultipartFile portada) {
        String urlImage = juego.getPortada() != null ?
                juego.getPortada() : "default.png";
        if (portada != null && !portada.isEmpty()) {
            juego.setPortada(guardarImagen(portada));
        }
        return juegoRepository.save(juego);
    }




    private String guardarImagen(MultipartFile archivo) {
        try {
            Path carpeta = Paths.get(UPLOAD_DIR);
            Files.createDirectories(carpeta);

            String original = archivo.getOriginalFilename() == null ? "portada" : archivo.getOriginalFilename();

            String limpio = Paths.get(original).getFileName().toString().replaceAll("[^a-zA-Z0-9._-]", "_");
            String nombreFinal = UUID.randomUUID() + "_" + limpio;

            Files.copy(archivo.getInputStream(), carpeta.resolve(nombreFinal), StandardCopyOption.REPLACE_EXISTING);
            return "/uploads/" + nombreFinal;
        } catch (IOException e) {
            throw new RuntimeException("No se pudo guardar la imagen de portada", e);
        }
    }
}
