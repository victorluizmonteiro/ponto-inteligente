package com.monteiro.pontointeligente.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.monteiro.pontointeligente.api.entity.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
	//Informa que a query não trave recursos no banco de dados.
	@Transactional(readOnly=true)
	Empresa findByCnpj(String cnpj);
}
