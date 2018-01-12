package com.monteiro.pontointeligente.api.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.monteiro.pontointeligente.api.enums.TipoEnum;
@Entity
@Table(name="TB_LANCAMENTO")
public class Lancamento {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private Date data;
	private String descricao;
	private String localizacao;
	private Date dataCriacao;
	private Date dataAtualizacao;
	private TipoEnum tipo;
	private Funcionario funcionario;

}
