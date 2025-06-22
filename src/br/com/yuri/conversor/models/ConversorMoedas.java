package br.com.yuri.conversor.models;

public class ConversorMoedas {

	private String moedaBase;
	private String moedaAlvo;
	private Double taxaConversao;
	private Double valorInicial;
	private Double quantiaTotal;
	
	
	public ConversorMoedas(ExchangeRateApiResponse exchangeRateApiResponse, Double valorInicial) {
		this.moedaBase = exchangeRateApiResponse.baseCode();
		this.moedaAlvo = exchangeRateApiResponse.targetCode();
		this.taxaConversao = Double.valueOf(exchangeRateApiResponse.conversionRate());
		this.valorInicial = valorInicial;
		this.quantiaTotal = Double.valueOf(exchangeRateApiResponse.conversionResult());
	}


	public String getMoedaBase() {
		return moedaBase;
	}


	public String getMoedaAlvo() {
		return moedaAlvo;
	}


	public Double getTaxaConversao() {
		return taxaConversao;
	}


	public Double getValorInicial() {
		return valorInicial;
	}


	public Double getQuantiaTotal() {
		return quantiaTotal;
	}
	
	@Override
	public String toString() {
		return "ConversorMoedas {" +
	            "moedaBase='" + moedaBase + '\'' +
	            ", moedaAlvo='" + moedaAlvo + '\'' +
	            ", taxaConversao=" + String.format("%.4f", taxaConversao) + 
	            ", valorInicial=" + String.format("%.2f", valorInicial) + 
	            ", quantiaTotal=" + String.format("%.2f", quantiaTotal) +
	            '}';
	}
}
