package com.desafio.campanhas.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.desafio.campanhas.domain.Campanha;
import com.desafio.campanhas.dto.CampanhaDTO;
import com.desafio.campanhas.service.CampanhaService;
import com.desafio.campanhas.utils.StringUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "Campanhas")
@RestController
@RequestMapping("desafio/v1/campanhas")
public class CampanhaRestController {

	@Autowired
	private CampanhaService campanhaService;

	@ApiOperation(value = "Obter todos", notes = "Busca todas as campanhas vigentes.")
	@GetMapping
	public ResponseEntity<List<CampanhaDTO>> getAll() {
		List<CampanhaDTO> dto = campanhaService.getAll();
		return ResponseEntity.ok().body(dto);
	}

	@ApiOperation(value = "Obter todas disponiveis por time", notes = "Busca todas as campanhas vigentes por time.")
	@GetMapping("/novas-por-time/{id}")
	public ResponseEntity<List<CampanhaDTO>> getNovasPorTime(@PathVariable("id") Integer id) {
		return ResponseEntity.ok().body(campanhaService.findByTime(id));
	}

	@ApiOperation(value = "Cadastrar uma nova campanha", notes = "Cria uma nova campanha.")
	@PostMapping
	public ResponseEntity<Campanha> create(@RequestBody CampanhaDTO campanha) {
		Campanha created = campanhaService.create(campanha);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(created.getId())
				.toUri();
		return ResponseEntity.created(uri).body(created);
	}

	@ApiOperation(value = "Buscar uma campanha pelo id", notes = "Busca uma nova campanha existente por id")
	@GetMapping("/{id}")
	public ResponseEntity<CampanhaDTO> findById(@PathVariable("id") Integer id) {
		return ResponseEntity.ok().body(campanhaService.findById(id));
	}

	@ApiOperation(value = "Alterar nova campanha existente pelo id", notes = "Altera uma campanha existente por id.")
	@PutMapping("/{id}")
	public ResponseEntity<CampanhaDTO> update(@RequestBody Campanha campanha, @PathVariable("id") Integer id) {
		return ResponseEntity.ok().body(campanhaService.update(campanha, id));
	}

	@ApiOperation(value = "Deletar uma campanha pelo id", notes = "Deleta uma campanha pelo id.")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
		campanhaService.deleteById(id);
		return ResponseEntity.ok().build();
	}

	@ApiOperation(value = "Associa uma ou mais campanhas", notes = "Associa uma ou mais campanhas existentes a um sócio existente.")
	@PostMapping("/associar")
	public ResponseEntity<Void> associar(@RequestParam(value = "socio", required = true) Integer idSocio,
			@RequestParam(value = "time", required = true) Integer idTime,
			@RequestBody List<Integer> campanhas) {
		campanhaService.associar(campanhas, idSocio, idTime);
		return ResponseEntity.noContent().build();
	}
	
	@ApiOperation(value = "Associa uma ou mais campanhas", notes = "Associa uma ou mais campanhas existentes a um sócio existente.")
	@GetMapping("/por-socio")
	public ResponseEntity<List<Campanha>> buscarPorSocio(@RequestParam(value="socio", required = true) Integer id) {
		return ResponseEntity.ok().body(campanhaService.findBySocioId(id));
	}
	
	@ApiOperation(value = "Obter todos paginado", notes = "Busca todas as campanhas salvas e vigentes paginado.")
	@GetMapping("/page")
	public ResponseEntity<Page<CampanhaDTO>> page(@RequestParam(value = "nome", defaultValue = "") String nome,
			@RequestParam(value = "time", required = false) Integer time,
			@RequestParam(value = "vigencia", defaultValue = "") String vigencia,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
			@RequestParam(value = "directions", defaultValue = "ASC") String directions) {

		nome = StringUtils.decodeName(nome);
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(directions), orderBy);
		Page<CampanhaDTO> pagination = campanhaService.getPagination(nome, time, vigencia, pageRequest);
		return ResponseEntity.ok().body(pagination);
	}

}
