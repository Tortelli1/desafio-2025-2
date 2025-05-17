package br.edu.unoesc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.unoesc.model.Exemplar;

@Repository
public interface ExemplarRepository extends JpaRepository<Exemplar, Integer> {

	List<Exemplar> findByFilmeId(Integer filmeId);
	
	List<Exemplar> findByAtivoTrue();
	
	List<Exemplar> findAll();
}
