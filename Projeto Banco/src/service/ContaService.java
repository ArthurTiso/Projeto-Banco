package service;

import java.io.*;
import java.util.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.util.function.Predicate;

import java.util.ArrayList; 
import java.util.List;

import javax.swing.JOptionPane;

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
	 	
	 //Para salvar múltiplas contas em um arquivo
	 public void saveContas(List<ContaCorrente> contas, String pathArquivo) throws IOException{ //Função para escrever os dados no arquivo no caminho salvo em pathArquivo
		 try (BufferedWriter bw = new BufferedWriter(new FileWriter(pathArquivo))) {
			 for (ContaCorrente c : contas){  // Uso de for para que, a cada elemento na ContaCorrente, ocorrerá um uso da Variável c para o sabe do arquivo 
				 bw.write(c.getNumero() + ", " + c.getTitular() + ", " + c.getSaldo());
				 bw.newLine();
			 }
		 }
	 }
	 //Função para mostrar contas ao usuário
	   public void exibirContas(String titulo, List<ContaCorrente> contas) {
	        String dados = titulo + "\n\n"; //Dados carrega inicialmete a string anunciando que as contas foram carregadas, e logo em seguida (pelo uso do for) carrega também os dados de cada uma das contas (obtidos via gets)
	        for (ContaCorrente c : contas) {
	            dados = dados + "Conta: " + c.getNumero() + " " + " Titular: " + c.getTitular() + " " + " Saldo: " + c.getSaldo() + " " + "\n"; 
	        }
	        JOptionPane.showMessageDialog(null, dados, titulo, JOptionPane.INFORMATION_MESSAGE);
	    }
	 //Função para adicionar conta
	   
	   public void adicionarConta(List<ContaCorrente> contas, String arquivoSaida) {
	        try {
	            String numeroNovaC = JOptionPane.showInputDialog(null, "Numero da Conta:");
	            if (numeroNovaC == null ) return; //Cancelar caso n entre valor
	            int numero = Integer.parseInt(numeroNovaC.trim()); //Passa valor de String para Int e passa para numero

	            String titular = JOptionPane.showInputDialog(null, "Numero do titular:");
	            if (titular == null ) return; //Cancelar caso n entre valor
	            
	            String saldoNovaC = JOptionPane.showInputDialog(null, "Saldo da conta: ");
	            if (saldoNovaC == null ) return; //Cancelar caso n entre valor
	            double saldo = Double.parseDouble(saldoNovaC.trim()); //Passa valor de String para double e passa para saldo

	            ContaCorrente novaConta = new ContaCorrente(numero, titular, saldo);
	            contas.add(novaConta);
	            saveContas(contas, arquivoSaida);

	            JOptionPane.showMessageDialog(null, "Conta salva com sucesso!");

	        } catch (NumberFormatException nfe) {
	            JOptionPane.showMessageDialog(null, "Erro: " + nfe.getMessage() + "\n Insira caracteres válidos");
	        } catch (IOException e) {
	            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage() + "\n Falha ao salvar conta!" );
	        }
	    }

	   //Função para deletar conta
	   
	   public void excluirConta(List<ContaCorrente> contas, String arquivoSaida) {
	        try {
	            String numeroDel = JOptionPane.showInputDialog(null, "Informe o numero da conta a ser deletada: "); //String armazena numero da conta a ser deletada
	            if (numeroDel == null) return; //Caso não entre valor, cancela a operação via return
	            int numero = Integer.parseInt(numeroDel.trim()); //Passa valor para int e corta espaços pelo trim

	            ContaCorrente contaRemover = null; //Declara o contaremover como nulo
	            for (ContaCorrente c : contas ) { // For para loop que percorre todas as contas
	                if (c.getNumero() == numero) { //Se o numero salvo p/ deletar for igual o numero pego pelo get nesse loop, o contaRemover armazena ele e quebra o loop com o BREAK
	                    contaRemover = c;
	                    break;
	                }
	            }

	            if (contaRemover != null) { //Se o contaRemover for nulo, o contas.remove deleta a conta utilziando a var armazenada. O saveContas salva tudo e então mostra ao usuário sucesso ou falha na operação
	                contas.remove(contaRemover);
	                saveContas(contas, arquivoSaida);
	                JOptionPane.showMessageDialog(null, "Conta removida com Sucesso!");
	            } else {
	                JOptionPane.showMessageDialog(null, "Conta não encontrada");
	            }

	        } catch (NumberFormatException nfe) {
	            JOptionPane.showMessageDialog(null, "Erro" + nfe.getMessage() + "Digite um numero valido");
	        } catch (IOException e) {
	            JOptionPane.showMessageDialog(null, "Erro" + e.getMessage() + "Erro ao deletar conta");
	        }
	    }
	   
	   //Filtro 
	    public List<ContaCorrente> filtrarContas(List<ContaCorrente> contas, Predicate<ContaCorrente> filtro) {
	        return contas.stream().filter(filtro).toList();
	    }

	    //Ordenação com comparator
	    public List<ContaCorrente> ordenarContas(List<ContaCorrente> contas, Comparator<ContaCorrente> comparator) {
	        List<ContaCorrente> ordenadas = new ArrayList<>(contas);
	        ordenadas.sort(comparator);
	        return ordenadas;
	    }
	    
	    
	 //Calculo de tarifas (Vem do ENUM)
	    public double aplicarTarifa(ContaCorrente conta, TarifaStrategy estrategia) {
	        return estrategia.calcularTarifa(conta);
	    }

	}
	 
	  



