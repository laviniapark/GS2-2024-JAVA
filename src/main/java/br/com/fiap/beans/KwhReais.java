package br.com.fiap.beans;

public class KwhReais extends ValoresAritmeticos {

	private double tarifaKwh;

	public KwhReais() {
		super();
	}

	public KwhReais(double valor, double tarifaKwh) {
		super(valor);
		this.tarifaKwh = tarifaKwh;
	}

	@Override
	public void calcular() {
		double tarifaBase = 0.656;

		double custo = getValor() * (tarifaBase + tarifaKwh);

		setResultado(custo);
	}

	public double getTarifaKwh() {
		return tarifaKwh;
	}

	public void setTarifaKwh(double tarifaKwh) {
		this.tarifaKwh = tarifaKwh;
	}

	@Override
	public String toString() {
		return String.format(
				"ConversaoCusto [consumo (kWh)=%.2f, tarifa adicional (R$/kWh)=%.4f, custo total (R$)=%.2f]",
				getValor(), tarifaKwh, getResultado());
	}
}
