package com.monteiro.pontointeligente.api.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.monteiro.pontointeligente.api.entity.Empresa;
import com.monteiro.pontointeligente.api.repository.EmpresaRepository;
import com.monteiro.pontointeligente.api.service.EmpresaService;
@Service
public class EmpresaServiceImpl  implements EmpresaService{
	@Autowired
	private EmpresaRepository empresaRepository;
	
	private static final Logger log = LoggerFactory.getLogger(EmpresaServiceImpl.class);
	@Override
	
	public Optional<Empresa> buscarPorCnpj(String cnpj) {
		log.info("Buscando empresa pelo CNPJ {}" + cnpj);
		return Optional.ofNullable(empresaRepository.findByCnpj(cnpj));
	}

	@Override
	public Empresa persistir(Empresa empresa) {
		log.info("Cadastrando empresa...");
		empresaRepository.save(empresa);
		
		return empresa;
	}

}
