package br.com.fiap.beans;

public class ClienteEletro {

	private int clienteEletroId;
	private int clienteId;
	private int eletronicoId;
	private double horasUsado;

	public ClienteEletro() {
		super();
	}

	public ClienteEletro(int clienteEletroId, int clienteId, int eletronicoId, double horasUsado) {
		super();
		this.clienteEletroId = clienteEletroId;
		this.clienteId = clienteId;
		this.eletronicoId = eletronicoId;
		this.horasUsado = horasUsado;
	}

	public int getClienteEletroId() {
		return clienteEletroId;
	}

	public void setClienteEletroId(int clienteEletroId) {
		this.clienteEletroId = clienteEletroId;
	}

	public int getClienteId() {
		return clienteId;
	}

	public void setClienteId(int clienteId) {
		this.clienteId = clienteId;
	}

	public int getEletronicoId() {
		return eletronicoId;
	}

	public void setEletronicoId(int eletronicoId) {
		this.eletronicoId = eletronicoId;
	}

	public double getHorasUsado() {
		return horasUsado;
	}

	public void setHorasUsado(double horasUsado) {
		this.horasUsado = horasUsado;
	}

}
