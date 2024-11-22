package br.com.fiap.beans;

public class Eletrodomestico {

	private int eletronicoId;
	private String categoria;
	private String marca;
	private String modelo;
	private int consumoWatts;

	public Eletrodomestico() {
		super();
	}

	public Eletrodomestico(int eletronicoId, String categoria, String marca, String modelo, int consumoWatts) {
		super();
		this.eletronicoId = eletronicoId;
		this.categoria = categoria;
		this.marca = marca;
		this.modelo = modelo;
		this.consumoWatts = consumoWatts;
	}

	public int getEletronicoId() {
		return eletronicoId;
	}

	public void setEletronicoId(int eletronicoId) {
		this.eletronicoId = eletronicoId;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public int getConsumoWatts() {
		return consumoWatts;
	}

	public void setConsumoWatts(int consumoWatts) {
		this.consumoWatts = consumoWatts;
	}

}
