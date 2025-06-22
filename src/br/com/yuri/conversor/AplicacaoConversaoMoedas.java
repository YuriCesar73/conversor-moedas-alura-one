package br.com.yuri.conversor;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.google.gson.Gson;

import br.com.yuri.conversor.api.ApiClient;
import br.com.yuri.conversor.models.ConversorMoedas;

public class AplicacaoConversaoMoedas {

	private Map<Integer, String> relacaoMoedas;
	private List<ConversorMoedas> historicoConversoes;
	private ApiClient apiClient;


	public AplicacaoConversaoMoedas(ApiClient apiClient) {
		this.relacaoMoedas = new HashMap<Integer, String>();
		this.historicoConversoes = new ArrayList<ConversorMoedas>();
		this.apiClient = apiClient;
		this.iniciarValores();
	}


	public void iniciarAplicacao() {
		Scanner leitura = new Scanner(System.in);
		String input = "";
		int opcaoEscolhida = -1;
		String valor = "";

		while(true) {
			System.out.println("Seja bem vindo(a) ao conversor de moedas!\n\n");
			
			this.listarOpcoesMenu();
			this.imprimirAsteriscos();
			
			input = leitura.nextLine();

			if(input.equalsIgnoreCase("9")) {
				break;
			}

			try {				
				opcaoEscolhida = Integer.valueOf(input);

				if(opcaoEscolhida > 7 && opcaoEscolhida < 1) {
					System.out.println("Opção inválida");
					break;
				}
			} catch (NumberFormatException e) {
				System.out.println("Insira um número válido");
			}

			if(opcaoEscolhida == 7) {
				this.listarHistorico();
			} else if (opcaoEscolhida == 8) {
				this.criarArquivo();
			}
			else {
				System.out.println("Digite o valor que deseja converter: ");
				valor = leitura.nextLine();

				double valorInserido = Double.valueOf(valor);
				this.converterMoedas(relacaoMoedas.get(opcaoEscolhida), valorInserido);
			}
		}
		
		leitura.close();
		System.out.println("Você saiu do programa");
	}

	private void converterMoedas(String moedas, double valorInserido) {
		ConversorMoedas response =  this.apiClient.enviarRequisicao(moedas, valorInserido);

		if(response != null) {
			this.historicoConversoes.add(response);
			System.out.println("\nValor inserido " + valorInserido + "[" + response.getMoedaBase() + "] corresponde ao valor final de =>>>> " + response.getQuantiaTotal() + "[" + response.getMoedaAlvo() + "]. O valor da taxa de conversão foi de " + response.getTaxaConversao() + "\n\n");

		} else {
			System.out.println("Não foi completar a resposta. Tente novamente mais tarde!");
		}
	}

	private void iniciarValores() {
		this.relacaoMoedas.put(1, "USD/ARS");
		this.relacaoMoedas.put(2, "ARS/USD");
		this.relacaoMoedas.put(3, "USD/BRL");
		this.relacaoMoedas.put(4, "BRL/USD");
		this.relacaoMoedas.put(5, "USD/COP");
		this.relacaoMoedas.put(6, "COP/USD");
	}

	private void listarHistorico() {
		this.imprimirAsteriscos();
		System.out.println("Histórico de conversões realizadas: ");
		for (ConversorMoedas conversorMoedas : historicoConversoes) {
			System.out.println(conversorMoedas.toString());
		}
		this.imprimirAsteriscos();
	}
	
	private void criarArquivo() {
		FileWriter escrita;
		try {
			System.out.println("\n\n Criando arquivo.....");
			escrita = new FileWriter("conversoes.json");
			Gson gson = new Gson();
			
			escrita.write(gson.toJson(historicoConversoes));
			escrita.close();
			
			System.out.println("Arquivo salvo com sucesso");
			this.imprimirAsteriscos();
		} catch (IOException e) {
			System.out.println("Não foi possível salvar o arquivo!");
			System.out.println(e.getMessage());
		}
		

	}

	
	
	private void listarOpcoesMenu() {
		String opcoes = """
				1) Dolar =>> Peso argentino
				2) Peso argentino =>> Dolar
				3) Dolar =>> Real brasileiro
				4) Real brasileiro =>> Dolar
				5) Dolar =>> Peso colombiano
				6) Peso colombiano =>> Dolar
				7) Listar histórico
				8) Criar arquivo com meu histórico
				9) Sair
				Escolha uma opção válida:
				""";

		System.out.println(opcoes);

	}
	
	private void imprimirAsteriscos() {
		System.out.println("***************************************************************************************");
	}
}
