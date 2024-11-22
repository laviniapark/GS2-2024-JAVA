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

import br.com.com.fiap.bo.KwhHoraBO;
import br.com.fiap.beans.KwhHora;
import br.com.fiap.conexoes.ConexaoFactory;

@Path("/kwh-hora")
public class KwhHoraResource {

	private Connection myConnection;
	private KwhHoraBO kwHBo;
	
	public KwhHoraResource() throws ClassNotFoundException, SQLException {
		this.myConnection = new ConexaoFactory().conexao();
		this.kwHBo = new KwhHoraBO(myConnection);
	}
	
	@POST
    @Path("/calcular")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response calcularKwhHora(KwhHora kwH) throws SQLException {
        try {
            kwHBo.inserirKwhHora(kwH.getValor());
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
        } catch (Exception e) {
        	myConnection.close();
			System.out.println("Conexao finalizada com sucesso!");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro inesperado: " + e.getMessage()).build();
        }
    }
	
	@GET
    @Path("/listar")
	@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response selecionarKwhHora() throws SQLException {
        try {
            ArrayList<KwhHora> lista = kwHBo.listarTodosKwhHora();
            if (lista.isEmpty()) {
                return Response.status(Response.Status.NO_CONTENT).build();
            }
            myConnection.close();
			System.out.println("Conexao finalizada com sucesso!");
            return Response.ok(lista).build();
        } catch (SQLException e) {
        	myConnection.close();
			System.out.println("Conexao finalizada com sucesso!");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao acessar o banco de dados: " + e.getMessage()).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro inesperado: " + e.getMessage()).build();
        }
    }
}
