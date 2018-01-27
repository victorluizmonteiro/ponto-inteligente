package com.monteiro.pontointeligente.api.controller;

import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.monteiro.pontointeligente.api.dto.FuncionarioDto;
import com.monteiro.pontointeligente.api.entity.Funcionario;
import com.monteiro.pontointeligente.api.response.Response;
import com.monteiro.pontointeligente.api.service.FuncionarioService;
import com.monteiro.pontointeligente.api.utils.PasswordUtils;

@RestController
@RequestMapping("api/funcionarios")
@CrossOrigin(origins="*")
public class FuncionarioController {

	private static final Logger log = LoggerFactory.getLogger(FuncionarioController.class);
	
	@Autowired
	private FuncionarioService funcionarioService;
	
	/**
	 * Atualiza os dados de um funcionário.
	 * 
	 * @param id
	 * @param funcionarioDto
	 * @param result
	 * @return ResponseEntity<Response<FuncionarioDto>>
	 * @throws NoSuchAlgorithmException
	 */
	@PutMapping("/{id}")
	public ResponseEntity<Response<FuncionarioDto>>atualizar
	(@Valid @PathVariable Long id,@RequestBody FuncionarioDto funcionarioDto,BindingResult result) throws NoSuchAlgorithmException{
		log.info("Atualizando funcionario {} " + funcionarioDto.toString());
		Response<FuncionarioDto>response = new Response<FuncionarioDto>();
		
		Optional<Funcionario>funcionario = this.funcionarioService.buscarPorId(id);
		if(!funcionario.isPresent()){
			result.addError(new ObjectError("funcionario", "Funcionário não existe"));
		}
		this.atualizarDadosFuncionario(funcionario.get(), funcionarioDto, result);
		
		if(result.hasErrors()){
			log.info("Erro ao validar Funcionario {} " + result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		this.funcionarioService.persistir(funcionario.get());
		response.setData(this.converterFuncionarioDto(funcionario.get()));
		
		return ResponseEntity.ok(response);
	}
	
	
	/**
	 * Atualiza os dados do funcionário com base nos dados encontrados no DTO.
	 * 
	 * @param funcionario
	 * @param funcionarioDto
	 * @param result
	 * @throws NoSuchAlgorithmException
	 */
	private void atualizarDadosFuncionario(Funcionario funcionario, FuncionarioDto funcionarioDto, BindingResult result)
			throws NoSuchAlgorithmException {
		
			funcionario.setNome(funcionarioDto.getNome());
			/* Verifica se o email do funcionario cadastrado é igual ao do DTO, caso não seja, verificamos se exise na base
			 * caso exista, retornamos erro na tela.
			 * 
			 */
			if(!funcionario.getEmail().equals(funcionarioDto.getEmail())){
				this.funcionarioService.buscarPorEmail(funcionarioDto.getEmail())
				.ifPresent(func -> result.addError(new ObjectError("funcionario", "Email ja existente !")));
				funcionario.setEmail(funcionarioDto.getEmail());
			}
			
			funcionario.setQtdHorasAlmoco(null);
			funcionarioDto.getQtdHorasAlmoco()
			.ifPresent(qtdHorasAlmoco -> funcionario.setQtdHorasAlmoco(Float.valueOf(qtdHorasAlmoco)));
			
			funcionario.setQtdHorasTrabalhoDia(null);
			funcionarioDto.getQtdHorasTrabalhoDia()
			.ifPresent(qtdHorasTrabalho -> funcionario.setQtdHorasTrabalhoDia(Float.valueOf(qtdHorasTrabalho)));
			
			funcionario.setValorHora(null);
			funcionarioDto.getValorHora().ifPresent(valorHora -> funcionario.setValorHora(new BigDecimal(valorHora)));
			
			if(funcionarioDto.getSenha().isPresent()){
				funcionario.setSenha(PasswordUtils.gerarBCrypt(funcionarioDto.getSenha().get()));
			}
				
		
	}
	/**
	 * Retorna um DTO com os dados de um funcionário.
	 * 
	 * @param funcionario
	 * @return FuncionarioDto
	 */
	private FuncionarioDto converterFuncionarioDto(Funcionario funcionario) {
		FuncionarioDto funcionarioDto = new FuncionarioDto();
		funcionarioDto.setId(funcionario.getId());
		funcionarioDto.setNome(funcionario.getNome());
		funcionarioDto.setEmail(funcionario.getEmail());
		funcionario.getQtdHorasAlmocoOpt()
		.ifPresent(qtdHoraAlmoco -> funcionarioDto.setQtdHorasAlmoco(Optional.of(Float.toString(qtdHoraAlmoco))));
		funcionario.getQtdHorasTrabalhoDiaOpt()
		.ifPresent(qtdHorasTrabalhada -> funcionarioDto.setQtdHorasTrabalhoDia(Optional.of(Float.toString(qtdHorasTrabalhada))));
		funcionario.getValorHoraOpt()
		.ifPresent(valorHora -> funcionarioDto.setValorHora(Optional.of(valorHora.toString())));

		
		return funcionarioDto;
		
		
	}

}
