package br.com.fiap.resource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.com.fiap.bo.KwhReaisBO;
import br.com.fiap.beans.KwhReais;
import br.com.fiap.conexoes.ConexaoFactory;

@Path("/kwh-reais")
public class KwhReaisResource {

	private Connection myConnection;
	private KwhReaisBO KRBo;
	
	public KwhReaisResource() throws ClassNotFoundException, SQLException {
		this.myConnection = new ConexaoFactory().conexao();
		this.KRBo = new KwhReaisBO(myConnection);
	}
	
	@POST
    @Path("/calcular")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response calcularKwhReais(KwhReais kr) throws SQLException {
        try {
            KRBo.inserirKwhReais(kr.getValor(), kr.getTarifaKwh());
            myConnection.close();
			System.out.println("Conexao finalizada com sucesso!");
            return Response.status(Response.Status.CREATED).entity("Conversão realizada e salva no banco.").build();
        } catch (IllegalArgumentException e) {
        	myConnection.close();
			System.out.println("Conexao finalizada com sucesso!");
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao acessar o banco de dados: " + e.getMessage()).build();
        }
    }
	
	@GET
    @Path("/listar")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarKwhReais() {
        try {
            ArrayList<KwhReais> lista = KRBo.listarTodos();
            if (lista.isEmpty()) {
                return Response.status(Response.Status.NO_CONTENT).build();
            }
            myConnection.close();
			System.out.println("Conexao finalizada com sucesso!");
            return Response.ok(lista).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao acessar o banco de dados: " + e.getMessage()).build();
        }
    }
	
}
