package br.com.fiap.main;

import java.sql.Connection;

import javax.swing.JOptionPane;

import br.com.fiap.beans.Cliente;
import br.com.fiap.conexoes.ConexaoFactory;
import br.com.fiap.dao.ClienteDAO;

public class TesteSelecionarIdCliente {

	public static void main(String[] args) {
		try (Connection connection = new ConexaoFactory().conexao()) {
			ClienteDAO clienteDAO = new ClienteDAO(connection);

			String input = JOptionPane.showInputDialog("Digite o ID do cliente:");
			int clienteId = Integer.parseInt(input);

			Cliente cliente = clienteDAO.selecionar(clienteId);

			if (cliente != null) {
				String mensagem = String.format("Cliente encontrado:\nID: %d\nNome: %s\nEndereço ID: %d\nCEP: %s",
						cliente.getClienteId(), cliente.getNome(), cliente.getEnderecoId(), cliente.getCep());
				JOptionPane.showMessageDialog(null, mensagem);
			} else {
				JOptionPane.showMessageDialog(null, "Cliente não encontrado!");
			}

		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
		}

	}

}
