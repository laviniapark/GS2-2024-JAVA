package br.com.fiap.service;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import br.com.fiap.dao.EnderecoDAO;
import br.com.fiap.model.Endereco;

public class ViaCepService {

	public Endereco getEndereco(String cep) throws ClientProtocolException, IOException {

		Endereco endereco = null;

		HttpGet request = new HttpGet("https://viacep.com.br/ws/" + cep + "/json/");

		try (CloseableHttpClient httpClient = HttpClientBuilder.create().disableRedirectHandling().build();
				CloseableHttpResponse response = httpClient.execute(request)) {

			HttpEntity entity = response.getEntity();

			if (entity != null) {
				String result = EntityUtils.toString(entity);

				Gson gson = new Gson();
				endereco = gson.fromJson(result, Endereco.class);
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}

		return endereco;
	}

	public boolean inserirEnderecoBanco(Connection connection, String cep) throws ClientProtocolException, IOException, SQLException {

	    if (cep == null || cep.isEmpty()) {
	        throw new IllegalArgumentException("CEP não pode ser nulo ou vazio.");
	    }

	    try {
	        System.out.println("Buscando informações do endereço para o CEP: " + cep);
	        Endereco endereco = getEndereco(cep);

	        if (endereco == null) {
	            System.out.println("Endereço não encontrado para o CEP: " + cep);
	            return false;
	        }

	        System.out.println("Dados do endereço recebidos para inserção:");
	        System.out.println("CEP: " + endereco.getCep());
	        System.out.println("Logradouro: " + endereco.getLogradouro());
	        System.out.println("Bairro: " + endereco.getBairro());
	        System.out.println("Localidade: " + endereco.getLocalidade());
	        System.out.println("UF: " + endereco.getUf());

	        EnderecoDAO enderecoDAO = new EnderecoDAO(connection);
	        enderecoDAO.inserir(endereco);

	        System.out.println("Endereço cadastrado com sucesso! CEP: " + cep);
	        return true;
	    } catch (SQLException e) {
	        System.err.println("Erro ao inserir endereço no banco de dados. Detalhes: " + e.getMessage());
	        throw e;
	    }
	}



	public int inserirOuBuscarEndereco(Connection connection, String cep) throws SQLException, ClientProtocolException, IOException {
		int enderecoId = buscarEnderecoId(connection, cep);
		if (enderecoId > 0) {
			System.out.println("CEP já existente. ID encontrado: " + enderecoId);
			return enderecoId;
		}

		inserirEnderecoBanco(connection, cep);
		return buscarEnderecoId(connection, cep);
	}

	public int buscarEnderecoId(Connection connection, String cep) throws SQLException {
		System.out.println("Buscando endereco_id no banco para o CEP: " + cep);

		String sql = "SELECT endereco_id FROM t_enderecos WHERE cep = ?";
		try (PreparedStatement stmt = connection.prepareStatement(sql)) {
			stmt.setString(1, cep);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				int enderecoId = rs.getInt("endereco_id");
				System.out.println("Endereco encontrado. ID: " + enderecoId);
				return enderecoId;
			} else {
				System.out.println("Nenhum resultado encontrado para o CEP: " + cep);
			}
		} catch (SQLException e) {
			System.err.println("Erro ao buscar endereco_id para o CEP: " + cep + " - " + e.getMessage());
			throw e;
		}
		return 0;
	}

}
