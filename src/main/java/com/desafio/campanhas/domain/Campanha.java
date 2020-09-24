package com.desafio.campanhas.domain;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.desafio.campanhas.dto.CampanhaDTO;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class Campanha implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	
	@Column(name = "NOME")
	private String nome;
	
	private Integer time;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	@Column(name = "VIGENCIA")
	private LocalDate vigencia;
	
	public Campanha() {
	}

	public Campanha(String nome, Integer time, LocalDate vigencia) {
		this.nome = nome;
		this.time = time;
		this.vigencia = vigencia;
	}

	public Campanha(CampanhaDTO dto) {
		this.id = dto.getId();
		this.nome = dto.getNome();
		this.time = dto.getTime();
		this.vigencia = dto.getVigencia();
	}
	
	public Campanha(Integer id) {
		this.id = id;
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
	
//	public Set<SocioTorcedor> getSociosTorcedores() {
//		return sociosTorcedores;
//	}
//	
//	public void setSociosTorcedores(Set<SocioTorcedor> sociosTorcedores) {
//		this.sociosTorcedores = sociosTorcedores;
//	}
}
