package br.com.yuri.conversor.api;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.yuri.conversor.models.ConversorMoedas;
import br.com.yuri.conversor.models.ExchangeRateApiResponse;

public class ApiClient {

	private String endereco = "https://v6.exchangerate-api.com/v6";

	private HttpClient client;
	
	private Gson gson;

	private String chaveApi;
	public ApiClient() {
		this.client = HttpClient.newHttpClient();
		this.gson = new GsonBuilder().
				setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).
				create();
	}

	public ApiClient(String chaveApi) {
		this.client = HttpClient.newHttpClient();
		this.chaveApi = chaveApi;
		this.gson = new GsonBuilder().
				setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).
				create();
	}

	public ConversorMoedas enviarRequisicao(String moedas, Double valor) {

		String url = this.montarEndereco(moedas, valor);


		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(url))
				.build();		

		try {
			HttpResponse<String> response = client
					.send(request, BodyHandlers.ofString());
			ExchangeRateApiResponse conversaoMoedaDto = this.gson.fromJson(response.body(), ExchangeRateApiResponse.class);
			
			return new ConversorMoedas(conversaoMoedaDto, valor);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
			return null;
		}
	}

	private String montarEndereco(String moedas, Double valor) {
		return this.endereco + "/" + this.chaveApi + "/pair/" + moedas + "/" + valor;
	}



}
