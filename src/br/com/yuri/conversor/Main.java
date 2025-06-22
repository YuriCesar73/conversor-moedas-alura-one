package br.com.yuri.conversor;

import br.com.yuri.conversor.api.ApiClient;

public class Main {


	public static void main(String[] args) {
		
		String chaveApi = "44d6cd19a8e6797577ae2e78";
		ApiClient apiClient = new ApiClient(chaveApi);
		AplicacaoConversaoMoedas app = new AplicacaoConversaoMoedas(apiClient);
		app.iniciarAplicacao();
	}
}


