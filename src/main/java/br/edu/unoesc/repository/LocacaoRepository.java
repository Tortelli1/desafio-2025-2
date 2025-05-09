package br.edu.unoesc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.unoesc.model.Locacao;

@Repository
public interface LocacaoRepository extends JpaRepository<Locacao, Integer> {

    List<Locacao> findByCpf(String cpf);

    boolean existsByExemplares_IdAndDataDevolvidoIsNull(Integer exemplarId);

    List<Locacao> findByDataDevolvidoIsNull();

    List<Locacao> findByDataDevolvidoIsNotNull();
}
