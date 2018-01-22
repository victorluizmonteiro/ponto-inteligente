package com.monteiro.pontointeligente.api.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.monteiro.pontointeligente.api.entity.Empresa;
import com.monteiro.pontointeligente.api.entity.Funcionario;
import com.monteiro.pontointeligente.api.enums.PerfilEnum;
import com.monteiro.pontointeligente.api.utils.PasswordUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class FuncionarioRepositoryTest {
	@Autowired
	private FuncionarioRepository funcionarioRepository;
	@Autowired
	private EmpresaRepository empresaRepository;
	
	private static final String CPF = "4567893111";
	private static final String EMAIL = "victor@gmail.com";
	
	@Before
	public void setUp(){
		Empresa empresa = this.empresaRepository.save(obterDadosEmpresa());
		this.funcionarioRepository.save(obterDadosFuncionario(empresa));
	}
	
	@After
	public void tearDown(){
		this.empresaRepository.deleteAll();
	}
	
	@Test
	public void testBuscarFuncionarioPorEmail(){
		Funcionario funcionario = this.funcionarioRepository.findByEmail(EMAIL);
		
		assertEquals(EMAIL, funcionario.getEmail());
	}
	@Test
	public void testBuscarFuncionarioPorCpf(){
		Funcionario funcionario = this.funcionarioRepository.findByCpf(CPF);
		
		assertEquals(CPF, funcionario.getCpf());
	}
	@Test
	public void testBuscarFuncionarioPorEmailECpf(){
		Funcionario funcionario = this.funcionarioRepository.findByCpfOrEmail(CPF, EMAIL);
		
		assertNotNull(funcionario);
	}
	@Test
	public void testBuscarFuncionarioPorEmailOuCpfParaCpfInvalido(){
		Funcionario funcionario = this.funcionarioRepository.findByCpfOrEmail("489009021", EMAIL);
		
		assertNotNull(funcionario);
	}
	
	@Test
	public void testBuscarFuncionarioPorEmailOuCpfParaEmailInvalido(){
		Funcionario funcionario = this.funcionarioRepository.findByCpfOrEmail(CPF, "exemplo@ix.com");
		
		assertNotNull(funcionario);
	}
	
	
	public Empresa obterDadosEmpresa(){
		Empresa empresa = new Empresa();
		empresa.setCnpj("432214495");
		empresa.setRazaoSocial("Empresa exemplo");
		return empresa;
	}
	
	public Funcionario obterDadosFuncionario(Empresa empresa){
		Funcionario funcionario = new Funcionario();
		funcionario.setNome("Fulano de Tal");
		funcionario.setPerfil(PerfilEnum.ROLE_USUARIO);
		funcionario.setSenha(PasswordUtils.gerarBCrypt("123456"));
		funcionario.setCpf(CPF);
		funcionario.setEmail(EMAIL);
		funcionario.setEmpresa(empresa);
		return funcionario;
	}

}
