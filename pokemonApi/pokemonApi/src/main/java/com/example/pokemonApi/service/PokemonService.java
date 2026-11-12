package com.example.pokemonApi.service;

import com.example.pokemonApi.exception.ResourceNotFoundException;
import com.example.pokemonApi.model.Pokemon;
import com.example.pokemonApi.repository.PokemonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PokemonService {

    private final PokemonRepository pokemonRepository;

    @Autowired
    public PokemonService(PokemonRepository pokemonRepository) {
        this.pokemonRepository = pokemonRepository;
    }

    public List<Pokemon> obtenerTodos() {
        return pokemonRepository.findAll();
    }

    public Pokemon obtenerPorId(Long id) {
        return pokemonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el Pokémon con id: " + id));
    }

    public Pokemon crearPokemon(Pokemon pokemon) {
        return pokemonRepository.save(pokemon);
    }

    public Pokemon actualizarPokemonCompleto(Long id, Pokemon pokemonActualizado) {
        Pokemon pokemon = pokemonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el Pokémon con id: " + id));

        pokemon.setNombre(pokemonActualizado.getNombre());
        pokemon.setTipo(pokemonActualizado.getTipo());
        pokemon.setNivel(pokemonActualizado.getNivel());
        pokemon.setAtaque(pokemonActualizado.getAtaque());
        pokemon.setDefensa(pokemonActualizado.getDefensa());
        pokemon.setDescripcion(pokemonActualizado.getDescripcion());
        pokemon.setImagenUrl(pokemonActualizado.getImagenUrl());
        return pokemonRepository.save(pokemon);
    }

    public Pokemon actualizarPokemonParcial(Long id, Pokemon pokemonParcial) {
        Pokemon pokemon = pokemonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el Pokémon con id: " + id));

        if (pokemonParcial.getNombre() != null) {
            pokemon.setNombre(pokemonParcial.getNombre());
        }
        if (pokemonParcial.getTipo() != null) {
            pokemon.setTipo(pokemonParcial.getTipo());
        }
        if (pokemonParcial.getNivel() != null) {
            pokemon.setNivel(pokemonParcial.getNivel());
        }
        if (pokemonParcial.getAtaque() != null) {
            pokemon.setAtaque(pokemonParcial.getAtaque());
        }
        if (pokemonParcial.getDefensa() != null) {
            pokemon.setDefensa(pokemonParcial.getDefensa());
        }
        if (pokemonParcial.getDescripcion() != null) {
            pokemon.setDescripcion(pokemonParcial.getDescripcion());
        }
        if (pokemonParcial.getImagenUrl() != null) {
            pokemon.setImagenUrl(pokemonParcial.getImagenUrl());
        }

        return pokemonRepository.save(pokemon);
    }

    public void eliminarPokemon(Long id) {
        if (!pokemonRepository.existsById(id)) {
            throw new ResourceNotFoundException("No se encontró el Pokémon con id: " + id);
        }
        pokemonRepository.deleteById(id);
    }
}
