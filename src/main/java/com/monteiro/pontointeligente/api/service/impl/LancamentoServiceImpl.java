package com.monteiro.pontointeligente.api.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.monteiro.pontointeligente.api.entity.Lancamento;
import com.monteiro.pontointeligente.api.repository.LancamentoRepository;
import com.monteiro.pontointeligente.api.service.LancamentoService;

@Service
public class LancamentoServiceImpl implements LancamentoService {

		private static final Logger log = LoggerFactory.getLogger(LancamentoServiceImpl.class);
		@Autowired
		private LancamentoRepository lancamentoRepository;
	@Override
	public Page<Lancamento> buscarPorFuncionarioId(Long funcionarioId, PageRequest pageRequest) {
		log.info("Buscamento lançamento para o Funcionario de ID : " + funcionarioId);
		return this.lancamentoRepository.findByFuncionarioId(funcionarioId, pageRequest);
	}

	@Override
	public Optional<Lancamento> buscarPorId(Long id) {
		log.info("Buscando um lançamento pelo ID : " + id);
		return Optional.ofNullable(this.lancamentoRepository.findOne(id));
	}

	@Override
	public Lancamento persistir(Lancamento lancamento) {
		log.info("Persistindo lançamento : " + lancamento.getId());
		return this.lancamentoRepository.save(lancamento);
	}

	@Override
	public void remover(Long id) {
		log.info("Removendo lançamento de ID : " + id);
		this.lancamentoRepository.delete(id);
		
	}

}
