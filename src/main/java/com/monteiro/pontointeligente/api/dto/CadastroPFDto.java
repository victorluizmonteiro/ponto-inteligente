package com.monteiro.pontointeligente.api.dto;

import java.util.Optional;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

public class CadastroPFDto {
private Long id;
	
	@NotEmpty(message="Nome não pode ser vazio !")
	@Length(min=3,max=200,message="O nome deve ter de 3 á 200 caracteres !")
	private String nome;
	
	@NotEmpty(message="Email não pode ser vazio !")
	@Length(min=3,max=200,message="O email deve ter de 3 a 200 caracteres !")
	@Email(message="Email inválido")
	private String email;
	
	@NotEmpty(message="CPF não pode ser vazio !")
	@CPF(message="CPF inválido")
	private String cpf;
	
	@NotEmpty(message="Senha não pode ser vazia !")
	private String senha;

	
	@NotEmpty(message="CNPJ não pode ser vazio !")
	@CNPJ(message="CNPJ inválido !")
	private String cnpj;
	private Optional<String> valorHora = Optional.empty();
	private Optional<String> qtdHorasTrabalhoDia = Optional.empty();
	private Optional<String> qtdHorasAlmoco = Optional.empty();
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public Optional<String> getValorHora() {
		return valorHora;
	}
	public void setValorHora(Optional<String> valorHora) {
		this.valorHora = valorHora;
	}
	public Optional<String> getQtdHorasTrabalhoDia() {
		return qtdHorasTrabalhoDia;
	}
	public void setQtdHorasTrabalhoDia(Optional<String> qtdHorasTrabalhoDia) {
		this.qtdHorasTrabalhoDia = qtdHorasTrabalhoDia;
	}
	public Optional<String> getQtdHorasAlmoco() {
		return qtdHorasAlmoco;
	}
	public void setQtdHorasAlmoco(Optional<String> qtdHorasAlmoco) {
		this.qtdHorasAlmoco = qtdHorasAlmoco;
	}
	@Override
	public String toString() {
		return "FuncionarioDto [id=" + id + ", nome=" + nome + ", email=" + email + ", senha=" + senha + ", cpf=" + cpf
				+ ", valorHora=" + valorHora + ", qtdHorasTrabalhoDia=" + qtdHorasTrabalhoDia + ", qtdHorasAlmoco="
				+ qtdHorasAlmoco + ", cnpj=" + cnpj + "]";
	}
	

}
