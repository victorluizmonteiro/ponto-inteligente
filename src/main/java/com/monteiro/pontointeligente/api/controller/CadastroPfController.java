package com.monteiro.pontointeligente.api.controller;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.monteiro.pontointeligente.api.dto.CadastroPFDto;
import com.monteiro.pontointeligente.api.entity.Empresa;
import com.monteiro.pontointeligente.api.entity.Funcionario;
import com.monteiro.pontointeligente.api.enums.PerfilEnum;
import com.monteiro.pontointeligente.api.response.Response;
import com.monteiro.pontointeligente.api.service.EmpresaService;
import com.monteiro.pontointeligente.api.service.FuncionarioService;
import com.monteiro.pontointeligente.api.utils.PasswordUtils;

@RestController
@RequestMapping("api/cadastro-pf")
@CrossOrigin(origins="*")
public class CadastroPfController {
	private static final Logger log = LoggerFactory.getLogger(CadastroPfController.class);
	@Autowired
	private EmpresaService empresaService;
	
	@Autowired
	private FuncionarioService funcionarioService;
	@PostMapping
	public ResponseEntity<Response<CadastroPFDto>>cadastrar
	(@Valid @RequestBody CadastroPFDto cadastroPfDTO,BindingResult result) throws NoSuchAlgorithmException{
		
		log.info("Cadastrando Funcionario PF {} " + cadastroPfDTO.toString());
		Response<CadastroPFDto> response = new Response<CadastroPFDto>(); 
		
		validarDadosExistentes(cadastroPfDTO, result);
		Funcionario funcionario = converteDtoEmPF(cadastroPfDTO);
		if(result.hasErrors()){
			log.error("Erro validando dados de cadastro PF: {}", result.getAllErrors());
			result.getAllErrors().forEach(errors -> response.getErrors().add(errors.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
			}
		
		Optional<Empresa>empresa = this.empresaService.buscarPorCnpj(cadastroPfDTO.getCnpj());
		empresa.ifPresent(emp -> funcionario.setEmpresa(emp));
		
		this.funcionarioService.persistir(funcionario);
		
		response.setData(this.converterCadastroPFDto(funcionario));
		return ResponseEntity.ok(response);
		
		
	}
	
	/**
	 * Verifica se a empresa está cadastrada e se o funcionário não existe na base de dados.
	 * 
	 * @param cadastroPFDto
	 * @param result
	 */
	private void validarDadosExistentes(CadastroPFDto cadastroPFDto, BindingResult result){
		Optional<Empresa>empresa = this.empresaService.buscarPorCnpj(cadastroPFDto.getCnpj());
		if(!empresa.isPresent()){
			result.addError(new ObjectError("empres", "Empresa não existe"));
		}
		
		this.funcionarioService.buscarPorCpf(cadastroPFDto.getCpf())
		.ifPresent(func -> result.addError(new ObjectError("funcionario", "CPF ja cadastrado !")));
		
		this.funcionarioService.buscarPorEmail(cadastroPFDto.getEmail())
		.ifPresent(func -> result.addError(new ObjectError("funcionario", "Email já cadastrado !")));
	}
	
	/**
	 * Converte os dados do DTO para funcionário.
	 * 
	 * @param cadastroPFDto
	 * @param result
	 * @return Funcionario
	 * @throws NoSuchAlgorithmException
	 */
	private Funcionario converteDtoEmPF(CadastroPFDto cadastroPFDto)throws NoSuchAlgorithmException{
		Funcionario funcionario = new Funcionario();
		funcionario.setNome(cadastroPFDto.getNome());
		funcionario.setCpf(cadastroPFDto.getCpf());
		funcionario.setEmail(cadastroPFDto.getEmail());
		funcionario.setPerfil(PerfilEnum.ROLE_USUARIO);
		funcionario.setSenha(PasswordUtils.gerarBCrypt(cadastroPFDto.getSenha()));
		// Faz a verificação nos atributos Optional do Dto, caso tenham valores, inserimos em nosso Entity
		cadastroPFDto.getQtdHorasAlmoco()
		.ifPresent(qtdHoraAlmoco -> funcionario.setQtdHorasAlmoco(Float.valueOf(qtdHoraAlmoco)));
		cadastroPFDto.getQtdHorasTrabalhoDia()
		.ifPresent(qtdHoraTrabalhoDia -> funcionario.setQtdHorasTrabalhoDia(Float.valueOf(qtdHoraTrabalhoDia)));
		cadastroPFDto.getValorHora().ifPresent(valorHora -> funcionario.setValorHora(new java.math.BigDecimal(valorHora)));
		
		return funcionario;
	}
	
	/**
	 * Popula o DTO de cadastro com os dados do funcionário e empresa.
	 * 
	 * @param funcionario
	 * @return CadastroPFDto
	 */
	private CadastroPFDto converterCadastroPFDto(Funcionario funcionario){
		CadastroPFDto cadastroPFDto = new CadastroPFDto();
		cadastroPFDto.setNome(funcionario.getNome());
		cadastroPFDto.setCpf(funcionario.getCpf());
		cadastroPFDto.setEmail(funcionario.getEmail());
		cadastroPFDto.setCpf(funcionario.getEmpresa().getCnpj());
		funcionario.getQtdHorasAlmocoOpt().ifPresent(qtdHorasAlmoco -> cadastroPFDto
				.setQtdHorasAlmoco(Optional.of(Float.toString(qtdHorasAlmoco))));	
		funcionario.getQtdHorasTrabalhoDiaOpt()
		.ifPresent(horaTrabalhoDia -> cadastroPFDto.setQtdHorasTrabalhoDia(Optional.of(Float.toString(horaTrabalhoDia))));
		funcionario.getValorHoraOpt().ifPresent(valorHora -> cadastroPFDto.setValorHora(Optional.of(valorHora.toString())));
		
		return cadastroPFDto;
	}
}
