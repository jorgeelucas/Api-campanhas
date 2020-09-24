package com.desafio.campanhas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.desafio.campanhas.domain.CampanhaSocio;

@Repository
public interface CampanhaSocioRepository extends JpaRepository<CampanhaSocio, Integer>{

	List<CampanhaSocio> findBySocio(Integer socio);
}
