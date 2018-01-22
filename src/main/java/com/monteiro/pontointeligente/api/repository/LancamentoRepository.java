package com.monteiro.pontointeligente.api.repository;

import java.util.List;

import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.monteiro.pontointeligente.api.entity.Lancamento;

@Transactional(readOnly=true)
@NamedQueries({
	@NamedQuery(name="LancamentoRepository.findByFuncionarioId",
			query="SELECT lanc FROM Lancamento lanc where lanc.funcionario.id = :funcionarioId")
})
public interface LancamentoRepository extends JpaRepository<Lancamento, Long>{
	//Parametro é o mesmo que passamos na namedQuery
	List<Lancamento>findByFuncionarioId(@Param("funcionarioId") Long funcionarioId);
	//Retorna os dados paginados, podemos configurar a quantidade do retorno
	Page<Lancamento>findByFuncionarioId(@Param("funcionarioId") Long funcionarioId,Pageable pageable);
}
