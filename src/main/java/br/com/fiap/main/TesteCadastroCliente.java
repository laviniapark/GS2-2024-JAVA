package br.com.fiap.main;

import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import br.com.com.fiap.bo.ClienteBO;
import br.com.fiap.beans.Cliente;
import br.com.fiap.conexoes.ConexaoFactory;

public class TesteCadastroCliente {
    public static void main(String[] args) {
        Connection connection = null;
        try {
            ConexaoFactory conexaoFactory = new ConexaoFactory();
            connection = conexaoFactory.conexao();
            System.out.println("Conexão com o banco estabelecida com sucesso!");

            String cep = JOptionPane.showInputDialog("Insira o CEP desejado (incluindo o traço)");
            String nome = JOptionPane.showInputDialog("Insira o nome do cliente:");

            ClienteBO clienteBO = new ClienteBO(connection);
            Cliente cliente = clienteBO.inserirCliente(nome, cep);

            String mensagem = String.format("Cliente '%s' inserido com sucesso!\nCEP: %s\nEndereço ID: %d",
                    cliente.getNome(), cliente.getCep(), cliente.getEnderecoId());
            JOptionPane.showMessageDialog(null, mensagem);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao inserir cliente: " + e.getMessage(), "Erro",
                    JOptionPane.ERROR_MESSAGE);
        } finally {

            if (connection != null) {
                try {
                    connection.close();
                    System.out.println("Conexão com o banco encerrada.");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

