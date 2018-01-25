package com.monteiro.pontointeligente.api.dto;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

public class CadastroPJDto {
	
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
	
	@NotEmpty(message="Razão social não pode ser vazia !")
	@Length(min=3,max=200,message="A Razão Social deve ter de 3 á 200 caracteres")
	private String razaoSocial;
	
	@NotEmpty(message="CNPJ não pode ser vazio !")
	@CNPJ(message="CNPJ inválido !")
	private String cnpj;
	
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
	public String getRazaoSocial() {
		return razaoSocial;
	}
	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	
	@Override
	public String toString() {
		return "CadastroPjDTO [id=" + id + ", nome=" + nome + ", email=" + email + ", senha=" + senha + ", cpf=" + cpf
				+ ", razaoSocial=" + razaoSocial + ", cnpj=" + cnpj + "]";
	}
	

}
