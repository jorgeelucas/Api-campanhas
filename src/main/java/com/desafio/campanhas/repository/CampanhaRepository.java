package com.desafio.campanhas.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.desafio.campanhas.domain.Campanha;

@Repository
public interface CampanhaRepository extends JpaRepository<Campanha, Integer>{
	List<Campanha> findAllByVigenciaGreaterThan(LocalDate vigencia);

	List<Campanha> findAllByTimeAndVigenciaGreaterThan(Integer time, LocalDate vigencia);

	List<Campanha> findAllByIdInAndVigenciaGreaterThan(List<Integer> ids, LocalDate vigencia);
}
