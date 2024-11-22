package br.com.com.fiap.bo;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.fiap.dao.EnderecoDAO;
import br.com.fiap.model.Endereco;
import br.com.fiap.service.ViaCepService;

public class EnderecoBO {

	private Connection myConnection;
	private ViaCepService viaCepService;
	private EnderecoDAO enderecoDao;

	public EnderecoBO(Connection connection, ViaCepService viaCepService) {
		this.myConnection = connection;
		this.viaCepService = viaCepService;
		this.enderecoDao = new EnderecoDAO(connection);
	}

	public int cadastrarEndereco(Endereco endereco) throws SQLException, IOException {

		validarEndereco(endereco);

		validarCep(endereco.getCep());

		return viaCepService.inserirOuBuscarEndereco(myConnection, endereco.getCep());
	}

	private void validarEndereco(Endereco endereco) {
		if (endereco == null || endereco.getCep() == null || endereco.getCep().isEmpty()) {
			throw new IllegalArgumentException("O CEP do endereço é obrigatório.");
		}
	}

	private void validarCep(String cep) {
		if (!cep.matches("\\d{5}-\\d{3}")) {
			throw new IllegalArgumentException("CEP inválido. Deve estar no formato 12345-678.");
		}
	}

	public ArrayList<Endereco> selecionarTodosEnderecos() throws ClassNotFoundException, SQLException {
		try {
			enderecoDao = new EnderecoDAO(myConnection);

			return (ArrayList<Endereco>) enderecoDao.selecionarTodos();
		} catch (Exception e) {
			System.out.println("Erro ao selecionar todos os endereços: " + e.getMessage());
			throw e;
		}
	}

	public Endereco selecionarEndereco(String cep) throws SQLException {
		try {
			validarCep(cep);
			return enderecoDao.selecionar(cep);
		} catch (SQLException e) {
			System.out.println("Erro ao selecionar endereço: " + e.getMessage());
			throw e;
		}
	}

}
