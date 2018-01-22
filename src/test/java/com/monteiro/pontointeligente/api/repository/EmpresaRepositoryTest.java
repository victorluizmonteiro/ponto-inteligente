package com.monteiro.pontointeligente.api.repository;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.monteiro.pontointeligente.api.entity.Empresa;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class EmpresaRepositoryTest {
	
	@Autowired
	private EmpresaRepository empresaRepository;
	
	private static final String CNPJ = "48974576833";
	
	@Before
	public void setUp(){
		Empresa empresa = new Empresa();
		empresa.setCnpj(CNPJ);
		empresa.setRazaoSocial("Empresa exemplo");
		this.empresaRepository.save(empresa);
	}
	
	
	@After
	public void tearDown(){
		this.empresaRepository.deleteAll();
	}
	@Test
	public void testBuscarPorCnpj(){
		Empresa empresa = this.empresaRepository.findByCnpj(CNPJ);
		
		assertEquals(CNPJ,empresa.getCnpj());
	}
	

}
