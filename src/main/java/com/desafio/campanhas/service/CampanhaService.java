package com.desafio.campanhas.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.desafio.campanhas.domain.Campanha;
import com.desafio.campanhas.domain.CampanhaSocio;
import com.desafio.campanhas.dto.CampanhaDTO;
import com.desafio.campanhas.repository.CampanhaRepository;
import com.desafio.campanhas.repository.CampanhaSocioRepository;
import com.desafio.campanhas.service.exception.ObjectNotFoundException;
import com.desafio.campanhas.utils.DateUtils;

@Service
public class CampanhaService {

	@Autowired
	private CampanhaRepository campanhaRepository;
	
	@Autowired
	private CampanhaSocioRepository campanhaSocioRepository;


	@Cacheable(value = "campanhaGetAll", unless = "#result==null")
	public List<CampanhaDTO> getAll() {
		List<Campanha> all = campanhaRepository.findAllByVigenciaGreaterThan(LocalDate.now().minusDays(1));
		List<CampanhaDTO> dto = all.stream().map(camp -> new CampanhaDTO(camp)).collect(Collectors.toList());
		return dto;
	}

	@CacheEvict(value = "campanhaGetAll", allEntries = true)
	public Campanha create(CampanhaDTO campanhaDTO) {
		validaVigencia(campanhaDTO.getVigencia());
		Campanha campanha = new Campanha(campanhaDTO);
		return campanhaRepository.save(campanha);
	}

	@Cacheable(value = "campanhaGetById", key = "#id", unless = "#result==null")
	public CampanhaDTO findById(Integer id) {
		Campanha campanha = campanhaRepository.findById(id).orElseThrow(
				() -> new ObjectNotFoundException(Campanha.class.getName() + " de id " + id + " não encontrado"));
		return new CampanhaDTO(campanha);
	}

	public Page<CampanhaDTO> getPagination(String nome, Integer idTime, String vigencia, PageRequest pageRequest) {
		LocalDate dataVigencia = DateUtils.parseDate(vigencia);

		Example<Campanha> exampleQuery = matcherCreator(nome, idTime, dataVigencia);
		Page<Campanha> page = campanhaRepository.findAll(exampleQuery, pageRequest);
		return page.map(p -> new CampanhaDTO(p));
	}

	@Caching(evict = { @CacheEvict(value = "campanhaGetById", allEntries = true),
			@CacheEvict(value = "campanhaGetAll", allEntries = true) })
	public CampanhaDTO update(Campanha campanha, Integer id) {
		campanha.setId(id);
		Campanha campanhaUpdated = campanhaRepository.save(campanha);
		return new CampanhaDTO(campanhaUpdated);
	}

	public List<CampanhaDTO> findByTime(Integer time) {
		LocalDate vigente = LocalDate.now().minusDays(1);
		List<Campanha> camp = campanhaRepository.findAllByTimeAndVigenciaGreaterThan(time, vigente);
		return camp.stream().map(campanhaEntity -> new CampanhaDTO(campanhaEntity)).collect(Collectors.toList());
	}

	@Caching(evict = { @CacheEvict(value = "campanhaGetById", allEntries = true),
			@CacheEvict(value = "campanhaGetAll", allEntries = true) })
	public void deleteById(Integer id) {
		findById(id);
		campanhaRepository.deleteById(id);
	}

	private void validaVigencia(LocalDate vigencia) {
		List<Campanha> all = campanhaRepository.findAll();
		Optional<Campanha> found = encontrarComMesmaData(all, vigencia);
		while (found.isPresent()) {
			Campanha campanha = found.get();
			campanha.setVigencia(campanha.getVigencia().plusDays(1));
			List<Campanha> novaLista = all.stream().filter(x -> !x.getId().equals(campanha.getId()))
					.collect(Collectors.toList());
			found = encontrarComMesmaData(novaLista, campanha.getVigencia());
		}
	}

	private Optional<Campanha> encontrarComMesmaData(List<Campanha> lista, LocalDate data) {
		return lista.stream().filter(cmp -> cmp.getVigencia().equals(data)).findFirst();
	}

	private Example<Campanha> matcherCreator(String nome, Integer idTime, LocalDate vigencia) {
		ExampleMatcher matcher = ExampleMatcher.matching()
				.withMatcher("nome", ExampleMatcher.GenericPropertyMatchers.contains()).withIgnoreCase("nome")
				.withIgnoreNullValues();
		Campanha campanhaSearch = new Campanha(nome, idTime, vigencia);
		return Example.of(campanhaSearch, matcher);
	}

	public void associar(List<Integer> campanhas, Integer socio, Integer time) {
		List<CampanhaSocio> lista = new ArrayList<>();
		campanhas.forEach(idC -> lista.add(new CampanhaSocio(new Campanha(idC), socio, time)));
		campanhaSocioRepository.saveAll(lista);
	}

	public List<Campanha> findBySocioId(Integer id) {
		List<CampanhaSocio> campanhasSocio = campanhaSocioRepository.findBySocio(id);
		List<Integer> ids = campanhasSocio.stream()
				.map(CampanhaSocio::getCampanha)
				.map(Campanha::getId)
				.collect(Collectors.toList());
		return campanhaRepository.findAllByIdInAndVigenciaGreaterThan(ids, LocalDate.now().minusDays(1));
	}

}
