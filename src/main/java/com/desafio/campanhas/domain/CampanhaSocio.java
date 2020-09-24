package com.desafio.campanhas.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class CampanhaSocio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;

	@ManyToOne
	private Campanha campanha;

	@Column(name = "ID_SOCIO")
	private Integer socio;

	@Column(name = "ID_TIME")
	private Integer time;

	public CampanhaSocio(Campanha campanha, Integer socio, Integer time) {
		this.campanha = campanha;
		this.socio = socio;
		this.time = time;
	}
	
	public CampanhaSocio() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Campanha getCampanha() {
		return campanha;
	}

	public void setCampanha(Campanha campanha) {
		this.campanha = campanha;
	}

	public Integer getSocio() {
		return socio;
	}

	public void setSocio(Integer socio) {
		this.socio = socio;
	}

	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}

}
