package service;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList; 
import java.util.List; 

import exception.SaldoInsuficienteException;
import model.Conta;
import model.ContaCorrente;


import java.util.ArrayList;



public class ContaService {
	
	
	//leitura dos dados no arquivo
	
	public ContaCorrente LerConta(String pathArquivo) throws IOException {
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
			System.out.println("Arquivo salvo em: " + new java.io.File(pathArquivo).getAbsolutePath());
		}
	}
	//saque via conta corrente
	 public void sacar(ContaCorrente conta, double valor) throws SaldoInsuficienteException {
	        conta.sacar(valor);
	}
	 
	 //Leitura de varias contas
	 public List<ContaCorrente> LerContas(String pathArquivo) throws IOException {
		 List<ContaCorrente> contas = new ArrayList<>();//Cria arraylist para armazenar novas contas
		 try (BufferedReader br = new BufferedReader(new FileReader(pathArquivo))) { //Inicia leitura via Buffered Reader(Puxa o arquivo pela var pathArquivo
			 String linha;
			 while ((linha = br.readLine()) != null) { //Leitura vai até o fim
				 if (linha.isBlank()) continue;//Leitura continue mesmo se encontrar linha em branco
				 String[] partes = linha.split(",");//Separação via virgula
				 int numero = Integer.parseInt(partes[0].trim());//Faz parse da entrada para Int e usa o trim para remover espaço em branco!
				 String titular = partes[1].trim();
				 double saldo = Double.parseDouble(partes[2].trim());
				 contas.add(new ContaCorrente(numero, titular, saldo));
			 }
			}
		 return contas; 
}
	 //Para salvar múltiplas contas em um arquivo
	 public void saveContas(List<ContaCorrente> contas, String pathArquivo) throws IOException{ //Função para escrever os dados no arquivo no caminho salvo em pathArquivo
		 try (BufferedWriter bw = new BufferedWriter(new FileWriter(pathArquivo))) {
			 for (ContaCorrente c : contas){  // Uso de for para que, a cada elemento na ContaCorrente, ocorrerá um uso da Variável c para o sabe do arquivo 
				 bw.write(c.getNumero() + ", " + c.getTitular() + ", " + c.getSaldo());
				 bw.newLine();
			 }
		 }
	 }
}



