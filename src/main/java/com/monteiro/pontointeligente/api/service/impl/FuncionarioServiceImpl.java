package com.monteiro.pontointeligente.api.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.monteiro.pontointeligente.api.entity.Funcionario;
import com.monteiro.pontointeligente.api.repository.FuncionarioRepository;
import com.monteiro.pontointeligente.api.service.FuncionarioService;

@Service
public class FuncionarioServiceImpl implements FuncionarioService{
	
	private static final Logger log = LoggerFactory.getLogger(FuncionarioServiceImpl.class);
	@Autowired
	private FuncionarioRepository funcionarioRepository;
	@Override
	
	public Funcionario persistir(Funcionario funcionario) {
	log.info("Persistindo funcionario : " + funcionario.getNome());
		return this.funcionarioRepository.save(funcionario);
	}

	@Override
	public Optional<Funcionario> buscarPorCpf(String cpf) {
		log.info("Buscando funcionario pelo CPF : " + cpf );
	  
		return Optional.ofNullable(this.funcionarioRepository.findByCpf(cpf));
	}

	@Override
	public Optional<Funcionario> buscarPorEmail(String email) {
		log.info("Buscando funcionario pelo Email : " + email );
		return Optional.ofNullable(this.funcionarioRepository.findByEmail(email));
	}

	@Override
	public Optional<Funcionario> buscarPorId(Long id) {
		log.info("Buscando funcionario pelo Id : " + id );
		return Optional.ofNullable(this.funcionarioRepository.findOne(id));
	}

}
