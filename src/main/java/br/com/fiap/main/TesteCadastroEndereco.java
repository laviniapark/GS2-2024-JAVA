package br.com.fiap.main;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import br.com.com.fiap.bo.EnderecoBO;
import br.com.fiap.conexoes.ConexaoFactory;
import br.com.fiap.model.Endereco;
import br.com.fiap.service.ViaCepService;

public class TesteCadastroEndereco {

	public static void main(String[] args) throws ClassNotFoundException {

		try (Connection connection = new ConexaoFactory().conexao()) {
			System.out.println("Conexão com o banco estabelecida!");

			ViaCepService viaCepService = new ViaCepService();

			EnderecoBO enderecoBO = new EnderecoBO(connection, viaCepService);

			Endereco endereco = new Endereco();
			endereco.setCep("01121-000");

			int enderecoId = enderecoBO.cadastrarEndereco(endereco);

			System.out.println("Endereço cadastrado com sucesso! ID: " + enderecoId);
		} catch (SQLException | IOException e) {
			e.printStackTrace();
		}
	}
}
