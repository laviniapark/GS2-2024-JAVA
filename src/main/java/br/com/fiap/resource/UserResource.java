package br.com.fiap.resource;

import java.sql.Connection;
import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.com.fiap.bo.UserBO;
import br.com.fiap.beans.User;
import br.com.fiap.conexoes.ConexaoFactory;

@Path("/usuarios")
public class UserResource {

	private Connection myConnection;
	private UserBO userBo;
	
	public UserResource() throws ClassNotFoundException, SQLException {
		this.myConnection = new ConexaoFactory().conexao();
		this.userBo = new UserBO(myConnection);
	}
	
	@POST
	@Path("/cadastrar")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response criarUsuario(User user) throws SQLException {
	    try {
	        String resultado = userBo.criarUsuario(user);
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
	                       .entity("Erro ao cadastrar usuário: " + e.getMessage()).build();
	    }
	}
	
	@PUT
	@Path("/atualizar/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response atualizarUsuario(@PathParam("id") int userId, User userAtualizado) throws SQLException {
	    try {
	        if (userId != userAtualizado.getUserId()) {
	            return Response.status(Response.Status.BAD_REQUEST)
	                           .entity("O ID do usuário na URL não corresponde ao ID no corpo da requisição.")
	                           .build();
	        }

	        String resultado = userBo.atualizarUsuario(userAtualizado);
	        myConnection.close();
			System.out.println("Conexao finalizada com sucesso!");
	        return Response.ok(resultado).build();
	    } catch (IllegalArgumentException e) {
	    	myConnection.close();
			System.out.println("Conexao finalizada com sucesso!");
	        return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
	    } catch (Exception e) {
	    	myConnection.close();
			System.out.println("Conexao finalizada com sucesso!");
	        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
	                       .entity("Erro ao atualizar usuário: " + e.getMessage()).build();
	    }
	}

	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deletarUsuario(@PathParam("id") int userId) throws SQLException {
	    try {
	        String resultado = userBo.deletarUsuario(userId);
	        myConnection.close();
			System.out.println("Conexao finalizada com sucesso!");
	        return Response.ok(resultado).build();
	    } catch (IllegalArgumentException e) {
	    	myConnection.close();
			System.out.println("Conexao finalizada com sucesso!");
	        return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
	    } catch (Exception e) {
	    	myConnection.close();
			System.out.println("Conexao finalizada com sucesso!");
	        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
	                       .entity("Erro ao deletar usuário: " + e.getMessage()).build();
	    }
	}

	
}
