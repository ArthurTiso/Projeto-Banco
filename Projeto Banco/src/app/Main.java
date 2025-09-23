package app;

import java.util.List;

import javax.swing.JOptionPane;
import model.ContaCorrente;
import service.ContaService;
import view.ContaGUI;
import exception.SaldoInsuficienteException;
import java.io.IOException;
import java.util.ArrayList;


//Main normal

public class Main {

    public static void main(String[] args) {
        // TODO Auto-generated method stub

        ContaService cs = new ContaService();
        String arquivoEntrada = "contas.txt";
        String arquivoSaida = "contas_atualizadas.txt";
        double valorSaque;

        try {  
        	
        	List<ContaCorrente> contas = cs.LerContas(arquivoEntrada);
        	
        	String dados = "Contas foram carregadas!\n\n";
        	for (ContaCorrente c : contas) {
        		dados = dados
        			+ "Conta: " + c.getNumero() + " "
        			+ " Titular: " + c.getTitular() + " "
        			+ " Saldo: " + c.getSaldo() + " "
        			+"\n"; //Aqui ele faz o mesmo esquema de for para armazenar e ler os dados de ContaCorrente via a VAR c, além de puxar elas pelos get do .c(conta)
        	}
        	// Mostrar em JOptionPane
            JOptionPane.showMessageDialog(null, dados, "Lista de Contas", JOptionPane.INFORMATION_MESSAGE);
            
            //Adição de conta
            int addConta = JOptionPane.showConfirmDialog(null, "Deseja adicionar uma nova conta?", "Nova conta", JOptionPane.YES_NO_OPTION);
            
            if (addConta == JOptionPane.YES_OPTION) {
            	try {
            		String numeroNovaC = JOptionPane.showInputDialog(null, "Numero da Conta:");
            		if (numeroNovaC == null ) return; //Cancelar caso n entre valor
            		int numero = Integer.parseInt(numeroNovaC.trim()); //Passa valor de String para Int e passa para numero

            		String titular = JOptionPane.showInputDialog(null, "Numero do titular:");
            		if (titular == null ) return; //Cancelar caso n entre valor
            		
            		String saldoNovaC = JOptionPane.showInputDialog(null, "Saldo da conta: ");
            		if (numeroNovaC == null ) return; //Cancelar caso n entre valor
            		double saldo = Double.parseDouble(saldoNovaC.trim()); //Passa valor de String para double e passa para saldo
            		
            		ContaCorrente novaConta = new ContaCorrente(numero, titular, saldo);
            		contas.add(novaConta);
            		cs.saveContas(contas, arquivoSaida);
            		
            		JOptionPane.showMessageDialog(null, "Conta salva com sucesso!");
            		         		
                	JOptionPane.showMessageDialog(null, dados, "Lista de Contas", JOptionPane.INFORMATION_MESSAGE); //Mostra contas novamente
                	
            	} catch (NumberFormatException nfe) {
            		JOptionPane.showMessageDialog(null, "Erro: " + nfe.getMessage() + "\n Insira caracteres válidos");
            		
            	} catch (IOException e) {
            		JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage() + "\n Falha ao salvar conta!" );
            	}
            	
            	
            }
            
            cs.saveContas(contas, arquivoSaida); //Chamada da função lá de ContaService que salva as multiplas contas num arquivo

        }catch (IOException e) {
        	JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
        }
    }
}
/*
//Main com GUI

public class Main {
    public static void main(String[] args) {
        ContaService cs = new ContaService();
        try {
            List<ContaCorrente> contas = cs.LerContas("contas.txt");
            new ContaGUI(contas, cs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
*/
