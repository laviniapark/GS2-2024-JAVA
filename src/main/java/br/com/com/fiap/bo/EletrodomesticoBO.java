package br.com.com.fiap.bo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.fiap.beans.Eletrodomestico;
import br.com.fiap.dao.EletrodomesticoDAO;


public class EletrodomesticoBO {

	private Connection myConnection;
	private EletrodomesticoDAO eletroDao;
	
	public EletrodomesticoBO(Connection connection) throws SQLException, ClassNotFoundException {
		this.myConnection = connection;
		this.eletroDao = new EletrodomesticoDAO(connection);
	}
	
	public String inserirEletrodomestico(Eletrodomestico eletrodomestico) throws Exception {
        validarEletrodomestico(eletrodomestico);

        if (eletroDao.existeEletrodomestico(eletrodomestico)) {
            throw new Exception("Eletrodoméstico já cadastrado.");
        }

        return eletroDao.inserir(eletrodomestico);
    }

    private void validarEletrodomestico(Eletrodomestico eletrodomestico) {
        if (eletrodomestico.getCategoria() == null || eletrodomestico.getCategoria().isEmpty()) {
            throw new IllegalArgumentException("A categoria é obrigatória.");
        }
        if (eletrodomestico.getMarca() == null || eletrodomestico.getMarca().isEmpty()) {
            throw new IllegalArgumentException("A marca é obrigatória.");
        }
        if (eletrodomestico.getModelo() == null || eletrodomestico.getModelo().isEmpty()) {
            throw new IllegalArgumentException("O modelo é obrigatório.");
        }
        if (eletrodomestico.getConsumoWatts()<= 0) {
            throw new IllegalArgumentException("O consumo Watts deve ser maior que zero.");
        }
        if (eletrodomestico.getCategoria().length() > 50 || 
            eletrodomestico.getMarca().length() > 50 || 
            eletrodomestico.getModelo().length() > 50) {
            throw new IllegalArgumentException("Categoria, Marca ou Modelo excedem o tamanho máximo de 50 caracteres.");
        }
    }
	
    public ArrayList<Eletrodomestico> selecionarTodosEletrodomesticos() throws ClassNotFoundException, SQLException {
		try {
			eletroDao = new EletrodomesticoDAO(myConnection);

			return (ArrayList<Eletrodomestico>) eletroDao.selecionarTodos();
		} catch (Exception e) {
			System.out.println("Erro ao selecionar todos os eletrodomesticos: " + e.getMessage());
			throw e;
		}
	}
    
    public Eletrodomestico selecionarEletrodomestico(int eletronicoId) throws SQLException {
		try {
			return eletroDao.selecionar(eletronicoId);
		} catch (SQLException e) {
			System.out.println("Erro ao selecionar eletrodomestico: " + e.getMessage());
			throw e;
		}
	}
    
    public String atualizarEletrodomestico(Eletrodomestico eletrodomestico) throws Exception {
	    try {
	    	
	        myConnection.setAutoCommit(false);

	        String resultado = eletroDao.atualizar(eletrodomestico, myConnection);

	        myConnection.commit();
	        return resultado;
	    } catch (Exception e) {
	        if (myConnection != null && !myConnection.isClosed() && !myConnection.getAutoCommit()) {
	            try {
	                myConnection.rollback();
	                System.err.println("Transação revertida com sucesso.");
	            } catch (SQLException rollbackEx) {
	                System.err.println("Erro ao reverter a transação: " + rollbackEx.getMessage());
	            }
	        }
	        throw new Exception("Erro ao atualizar eletrodomestico: " + e.getMessage(), e);
	    }
	}
    
    public String deletarEletrodomestico(int eletronicoId) throws SQLException {
        try {
            return eletroDao.deletar(eletronicoId);
        } catch (SQLException e) {
            System.out.println("Erro ao deletar eletrodomestico: " + e.getMessage());
            throw e;
        }
    }
    
}
