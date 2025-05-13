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
     
    List<Locacao> findByCpfAndDataDevolvidoIsNotNull(String cpf);
    
    List<Locacao> findByCpfAndDataDevolvidoIsNull(String cpf);
    
    List<Locacao> findByNomeAndDataDevolvidoIsNotNull(String nome);
    
    List<Locacao> findByNomeAndDataDevolvidoIsNull(String nome);
    
    List<Locacao> findByEmailAndDataDevolvidoIsNotNull(String email);
    
    List<Locacao> findByEmailAndDataDevolvidoIsNull(String email);
    
    List<Locacao> findByDataDevolvidoIsNull();

    List<Locacao> findByDataDevolvidoIsNotNull();
    
}
