package br.com.fiap.main;

import java.io.IOException;

import javax.swing.JOptionPane;

import org.apache.http.client.ClientProtocolException;

import br.com.fiap.model.Endereco;
import br.com.fiap.service.ViaCepService;

public class TesteRespostaAPI {

	static String texto(String j) {
		return JOptionPane.showInputDialog(j);
	}
	
	public static void main(String[] args) throws ClientProtocolException, IOException {
		
		ViaCepService viacepService = new ViaCepService();
		
		String c = texto("Digite o numero do CEP");
		
		Endereco endereco = viacepService.getEndereco(c);
		
		System.out.println(endereco);

	}

}
