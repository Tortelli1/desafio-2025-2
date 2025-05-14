package br.edu.unoesc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.unoesc.model.Locacao;

@Repository
public interface LocacaoRepository extends JpaRepository<Locacao, Integer> {

    boolean existsByExemplares_IdAndDataDevolvidoIsNull(Integer exemplarId);

    List<Locacao> findAll();

    List<Locacao> findByCpf(String cpf);
     
    List<Locacao> findByCpfContainingIgnoreCaseAndDataDevolvidoIsNotNull(String cpf);
    
    List<Locacao> findByCpfContainingIgnoreCaseAndDataDevolvidoIsNull(String cpf);
    
    List<Locacao> findByNomeContainingIgnoreCaseAndDataDevolvidoIsNotNull(String nome);
    
    List<Locacao> findByNomeContainingIgnoreCaseAndDataDevolvidoIsNull(String nome);
    
    List<Locacao> findByEmailContainingIgnoreCaseAndDataDevolvidoIsNotNull(String email);
    
    List<Locacao> findByEmailContainingIgnoreCaseAndDataDevolvidoIsNull(String email);
    
    List<Locacao> findByDataDevolvidoIsNull();

    List<Locacao> findByDataDevolvidoIsNotNull();
    
}
