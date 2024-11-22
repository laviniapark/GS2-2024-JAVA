package br.com.fiap.resource;

import java.sql.Connection;
import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.com.fiap.bo.SuporteBO;
import br.com.fiap.beans.Suporte;
import br.com.fiap.conexoes.ConexaoFactory;

@Path("/suporte")
public class SuporteResource {

	private Connection myConnection;
	private SuporteBO supBo;
	
	public SuporteResource() throws ClassNotFoundException, SQLException {
		this.myConnection = new ConexaoFactory().conexao();
		this.supBo = new SuporteBO(myConnection);
	}
	
	@POST
	@Path("/enviar")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response enviarMensagem(Suporte sup) throws SQLException {
	    try {
	        String resultado = supBo.inserirSuporte(sup);
	        myConnection.close();
			System.out.println("Conexao finalizada com sucesso!");
	        return Response.status(Response.Status.CREATED).entity(resultado).build();
	    } catch (IllegalArgumentException e) {
	    	myConnection.close();
			System.out.println("Conexao finalizada com sucesso!");
	        return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
	    } catch (Exception e) {
	    	myConnection.close();
			System.out.println("Conexao finalizada com sucesso!");
	        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
	                       .entity("Erro ao enviar mensagem: " + e.getMessage()).build();
	    }
	}
	
}
