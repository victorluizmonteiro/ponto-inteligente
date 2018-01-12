package com.monteiro.pontointeligente.api.entity;

import java.sql.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
@Entity
@Table(name="TB_EMPRESA")
public class Empresa {
	
	private Long id;
	private String razaoSocial;
	private String cnpj;
	private Date dataCriacao;
	private Date dataAtualizacao;
	private List<Funcionario> funcionarios;

}
