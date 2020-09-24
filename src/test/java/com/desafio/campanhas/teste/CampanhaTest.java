package com.desafio.campanhas.teste;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.desafio.campanhas.ApiCampanhasApplication;
import com.desafio.campanhas.domain.Campanha;
import com.desafio.campanhas.domain.CampanhaSocio;
import com.desafio.campanhas.repository.CampanhaRepository;
import com.desafio.campanhas.repository.CampanhaSocioRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApiCampanhasApplication.class)
@TestPropertySource(locations = "classpath:test.properties")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class CampanhaTest {

	private MockMvc mockMvc;

	@MockBean
	private CampanhaRepository repository;

	@MockBean
	private CampanhaSocioRepository campanhaSocioRepository;

	@Autowired
	WebApplicationContext webAppCtx;

	@Autowired
	private ObjectMapper objectMapper;

	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webAppCtx).build();

		when(repository.findById(Mockito.anyInt()))
				.thenReturn(Optional.of(new Campanha("campanha", 1, LocalDate.now())));
		when(repository.save(Mockito.any(Campanha.class)))
				.thenReturn(new Campanha(1));
		when(campanhaSocioRepository.saveAll(Mockito.anyList()))
				.thenReturn(Arrays.asList(new CampanhaSocio(new Campanha(1), 2,3)));
		when(repository.findAllByTimeAndVigenciaGreaterThan(Mockito.anyInt(), Mockito.any(LocalDate.class)))
				.thenReturn(Arrays.asList(new Campanha(2)));
		doNothing().when(repository).deleteById(Mockito.anyInt());
	}

	@Test
	public void testaCriacaoDeNovCampanha() throws Exception {
		Campanha campanha = new Campanha("nova", 1, LocalDate.now());
		mockMvc.perform(post("/desafio/v1/campanhas")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(campanha)))
		.andExpect(status().isCreated());
	}

	@Test
	public void testaBuscarPorId() throws Exception {
		MvcResult result = mockMvc.perform(get("/desafio/v1/campanhas/1")
				.contentType("application/json"))
				.andExpect(status().isOk())
				.andExpect(content()
						.contentType("application/json"))
				.andReturn();
		JSONObject jsonObject = objectMapper.convertValue(result.getResponse().getContentAsString(), JSONObject.class);
		assertThat(jsonObject.get("nome")).isEqualTo("campanha");
	}

	@Test
	public void testaDeletar() throws Exception {
		mockMvc.perform(delete("/desafio/v1/campanhas/1")
				.contentType("application/json"))
		.andExpect(status().isOk());
	}

	@Test
	public void testaAssociar() throws Exception {
		List<Integer> listaDeCampanhas = Arrays.asList(1);
		mockMvc.perform(post("/desafio/v1/campanhas/associar")
				.param("socio", "1")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(listaDeCampanhas)))
				.andExpect(status().isNoContent());
	}
	
	@Test
	public void testaBuscarPorTimeVigentes() throws Exception {
		mockMvc.perform(get("/desafio/v1/campanhas/novas-por-time/1")
				.contentType("application/json"))
				.andExpect(status().is2xxSuccessful());
	}

}
