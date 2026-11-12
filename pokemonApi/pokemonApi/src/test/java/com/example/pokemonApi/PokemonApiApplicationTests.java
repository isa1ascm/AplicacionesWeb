package com.example.pokemonApi;

import com.example.pokemonApi.model.Pokemon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class PokemonApiApplicationTests {

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@BeforeEach
	void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	void contextLoads() {
	}

	@Test
	void crearPokemonInvalidoDaBadRequest() throws Exception {
		Pokemon pokemon = new Pokemon(null, "", "", 0, -1, -1, "Descripción", "not-a-url");

		mockMvc.perform(post("/api/pokemones")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(pokemon)))
				.andExpect(status().isBadRequest());
	}

	@Test
	void obtenerPokemonInexistenteDaNotFound() throws Exception {
		mockMvc.perform(get("/api/pokemones/9999"))
				.andExpect(status().isNotFound());
	}

}
