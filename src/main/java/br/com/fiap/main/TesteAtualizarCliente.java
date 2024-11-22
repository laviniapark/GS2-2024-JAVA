package br.com.fiap.main;

import java.sql.Connection;

import javax.swing.JOptionPane;

import br.com.fiap.beans.Cliente;
import br.com.fiap.conexoes.ConexaoFactory;
import br.com.fiap.dao.ClienteDAO;

public class TesteAtualizarCliente {

	public static void main(String[] args) {
		try (Connection connection = new ConexaoFactory().conexao()) {
			
			ClienteDAO clienteDAO = new ClienteDAO(connection);

			int clienteId = Integer.parseInt(JOptionPane.showInputDialog("Digite o ID do cliente a ser atualizado:"));
			String nome = JOptionPane.showInputDialog("Digite o novo nome do cliente:");
			String cep = JOptionPane.showInputDialog("Digite o novo CEP do cliente (formato: 12345-678)");

			if (!cep.matches("\\d{5}-\\d{3}")) {
				JOptionPane.showMessageDialog(null, "CEP inválido! Use o formato 12345-678.", "Erro",
						JOptionPane.ERROR_MESSAGE);
				return;
			}

			// Criar o objeto Cliente
			Cliente cliente = new Cliente();
			cliente.setClienteId(clienteId);
			cliente.setNome(nome);
			cliente.setCep(cep);

			// Atualizar o cliente
			String resultado = clienteDAO.atualizar(cliente, connection);
			JOptionPane.showMessageDialog(null, resultado);

		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}
}
