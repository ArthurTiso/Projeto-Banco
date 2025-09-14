package service;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import exception.SaldoInsuficienteException;
import model.ContaCorrente;

public class ContaService {
	
	
	//leitura dos dados no arquivo
	
	public ContaCorrente lerConta(String pathArquivo) throws IOException {
		try (BufferedReader br = new BufferedReader(new FileReader(pathArquivo))){
			String linha = br.readLine();
			if (linha == null || linha.isBlank()) {
				throw new IOException("Arquivo vazio!!");				
			}
			String[] partes = linha.split(",");
			int numero = Integer.parseInt(partes[0].trim());
			String titular = partes[1].trim();
			double saldo = Double.parseDouble(partes[2].trim());
			
			return new	ContaCorrente(numero, titular, saldo);
			}
		}
	
	//Salva dados no txt
	public void salvarConta(ContaCorrente conta, String pathArquivo) throws IOException {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(pathArquivo))){
			bw.write(conta.getNumero() + "," + conta.getTitular() + "," + conta.getSaldo());
		}
	}
	//saque via conta corrente
	 public void sacar(ContaCorrente conta, double valor) throws SaldoInsuficienteException {
	        conta.sacar(valor);
	
	}
}



