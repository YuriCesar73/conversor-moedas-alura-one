package br.com.yuri.conversor.models;

public record ExchangeRateApiResponse(String baseCode, String targetCode, String conversionRate, String conversionResult) {

}
