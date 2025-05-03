package br.edu.unoesc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.unoesc.model.Filme;

@Repository
public interface FilmeRepository extends JpaRepository<Filme, Integer> {

	boolean existsByTitulo(String titulo);
}
