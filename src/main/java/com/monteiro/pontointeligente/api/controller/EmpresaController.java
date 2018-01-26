package com.monteiro.pontointeligente.api.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.monteiro.pontointeligente.api.dto.EmpresaDto;
import com.monteiro.pontointeligente.api.entity.Empresa;
import com.monteiro.pontointeligente.api.response.Response;
import com.monteiro.pontointeligente.api.service.EmpresaService;

@RestController
@RequestMapping("api/empresas")
@CrossOrigin(origins="*")
public class EmpresaController {

	private static final Logger log = LoggerFactory.getLogger(EmpresaController.class);
	
	@Autowired
	private EmpresaService empresaService;
	
	@GetMapping("/cnpj/{cnpj}")
	public ResponseEntity<Response<EmpresaDto>>buscar(@PathVariable String cnpj){
		log.info("Buscando Empresa do CNPJ : " + cnpj);
		Response<EmpresaDto>response = new Response<EmpresaDto>();
		Optional<Empresa>empresa = this.empresaService.buscarPorCnpj(cnpj);
		if(!empresa.isPresent()){
			log.info("Erro ao buscar a empresa com o CNPJ : " + cnpj);
			response.getErrors().add("Empresa não encontrada para o CNPJ " + cnpj);
			return ResponseEntity.badRequest().body(response);
		}
		response.setData(this.converteEmpresaDto(empresa.get()));
		return ResponseEntity.ok(response);
		
	}
	
	private EmpresaDto converteEmpresaDto(Empresa empresa){
		EmpresaDto dto = new EmpresaDto();
		dto.setId(empresa.getId());
		dto.setCnpj(empresa.getCnpj());
		dto.setRazaoSocial(empresa.getRazaoSocial());
		
		return dto;
	}
}
