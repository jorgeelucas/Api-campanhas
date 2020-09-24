package com.desafio.campanhas.dto;

import java.io.Serializable;
import java.time.LocalDate;

import com.desafio.campanhas.domain.Campanha;
import com.fasterxml.jackson.annotation.JsonFormat;

public class CampanhaDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String nome;
	private Integer time;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy")
	private LocalDate vigencia;
	
	public CampanhaDTO() {
	}

	public CampanhaDTO(Campanha campanha) {
		this.id = campanha.getId();
		this.nome = campanha.getNome();
		this.time = campanha.getTime();
		this.vigencia = campanha.getVigencia();
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}
	
	public LocalDate getVigencia() {
		return vigencia;
	}
	
	public void setVigencia(LocalDate vigencia) {
		this.vigencia = vigencia;
	}
}
