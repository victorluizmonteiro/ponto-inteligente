package com.monteiro.pontointeligente.api.controller;
import java.security.NoSuchAlgorithmException;

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

import com.monteiro.pontointeligente.api.dto.CadastroPJDto;
import com.monteiro.pontointeligente.api.entity.Empresa;
import com.monteiro.pontointeligente.api.entity.Funcionario;
import com.monteiro.pontointeligente.api.enums.PerfilEnum;
import com.monteiro.pontointeligente.api.response.Response;
import com.monteiro.pontointeligente.api.service.EmpresaService;
import com.monteiro.pontointeligente.api.service.FuncionarioService;
import com.monteiro.pontointeligente.api.utils.PasswordUtils;

@RestController
@RequestMapping("api/cadastrar-pj")
@CrossOrigin(origins="*")
public class CadastroPjController {
	
	private static final Logger log = LoggerFactory.getLogger(CadastroPjController.class);
	
	@Autowired
	private EmpresaService empresaService;
	
	@Autowired 
	private FuncionarioService funcionarioService;
	
	public CadastroPjController() {
	}

	/**
	 * Cadastra uma pessoa jurídica no sistema.
	 * 
	 * @param cadastroPJDto
	 * @param result
	 * @return ResponseEntity<Response<CadastroPJDto>>
	 * @throws NoSuchAlgorithmException
	 */
	@PostMapping 
	public ResponseEntity<Response<CadastroPJDto>>cadastrar
		(@Valid @RequestBody CadastroPJDto cadastroPjDTO,BindingResult result)throws NoSuchAlgorithmException{
		log.info("Cadastrando PJ : {}" + cadastroPjDTO.toString());
		Response<CadastroPJDto>response = new Response<CadastroPJDto>();
		
		validarDadosExistentes(cadastroPjDTO, result);
		Empresa empresa = this.converterDtoParaEmpresa(cadastroPjDTO);
		Funcionario funcionario = this.converterDtoParaFuncionario(cadastroPjDTO);
		if (result.hasErrors()) {
			log.error("Erro validando dados de cadastro PJ: {}", result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
			return ResponseEntity.badRequest().body(response);
		}
		
		this.empresaService.persistir(empresa);
		funcionario.setEmpresa(empresa);
		this.funcionarioService.persistir(funcionario);
		
		response.setData(this.converterCadastroPJDto(funcionario));
		return ResponseEntity.ok(response);
		
	}
	
	/**
	 * Verifica se a empresa ou funcionário já existem na base de dados.
	 * 
	 * @param cadastroPJDto
	 * @param result
	 */
	
	private void validarDadosExistentes(CadastroPJDto cadastroPjDTO,BindingResult result){
		this.empresaService.buscarPorCnpj(cadastroPjDTO.getCnpj())
		.ifPresent(emp -> result.addError(new ObjectError("empresa", "Empresa já existente.")));

		this.funcionarioService.buscarPorCpf(cadastroPjDTO.getCpf())
		.ifPresent(func -> result.addError(new ObjectError("funcionario", "CPF já existente.")));

		this.funcionarioService.buscarPorEmail(cadastroPjDTO.getEmail())
		.ifPresent(func -> result.addError(new ObjectError("funcionario", "Email já existente.")));
	}
	
	/**
	 * Converte os dados do DTO para empresa.
	 * 
	 * @param cadastroPJDto
	 * @return Empresa
	 */
	private Empresa converterDtoParaEmpresa(CadastroPJDto cadastroPJDto){
		Empresa empresa = new Empresa();
		empresa.setCnpj(cadastroPJDto.getCnpj());
		empresa.setRazaoSocial(cadastroPJDto.getRazaoSocial());
		return empresa;
		
	}
	
	/**
	 * Converte os dados do DTO para Funcionário
	 * 
	 * @param cadastroPJDto
	 * @return Funcionario
	 */
	
	private Funcionario converterDtoParaFuncionario(CadastroPJDto cadastroPJDto){
		Funcionario funcionario =  new Funcionario();
		funcionario.setCpf(cadastroPJDto.getCpf());
		funcionario.setNome(cadastroPJDto.getNome());
		funcionario.setEmail(cadastroPJDto.getEmail());
		funcionario.setSenha(PasswordUtils.gerarBCrypt(cadastroPJDto.getSenha()));
		funcionario.setPerfil(PerfilEnum.ROLE_ADMIN);
		
		return funcionario;
	}
	
	
	/**
	 * Popula o DTO de cadastro com os dados do funcionário e empresa.
	 * 
	 * @param funcionario
	 * @return CadastroPJDto
	 */
	private CadastroPJDto converterCadastroPJDto(Funcionario funcionario){
		CadastroPJDto cadastroPJDto = new CadastroPJDto();
		cadastroPJDto.setId(funcionario.getId());
		cadastroPJDto.setNome(funcionario.getNome());
		cadastroPJDto.setCpf(funcionario.getCpf());
		cadastroPJDto.setEmail(funcionario.getEmail());
		cadastroPJDto.setSenha(funcionario.getSenha());
		cadastroPJDto.setRazaoSocial(funcionario.getEmpresa().getRazaoSocial());
		cadastroPJDto.setCnpj(funcionario.getEmpresa().getCnpj());
		
		return cadastroPJDto;
	}

}
