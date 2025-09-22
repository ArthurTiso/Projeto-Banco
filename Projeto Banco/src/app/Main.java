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
/*
public class Main {

    public static void main(String[] args) {
        // TODO Auto-generated method stub

        ContaService cs = new ContaService();
        String arquivoEntrada = "conta.txt";
        String arquivoSaida = "conta_atualizada.txt";
        double valorSaque;

        try { 
        	
        	List<ContaCorrente> contas = cs.LerContas(arquivoEntrada);
        	
        	String dados = "Contas foram carregadas!\n\n";
        	for (ContaCorrente c : contas) {
        		dados = dados,,,,,,,,,,,,,,,,,,,
        			+ "Conta: " + c.getNumero() + " "
        			+ " Titular: " + c.getTitular() + " "
        			+ " Saldo: " + c.getTitular() + " "
        			+"\n"; //Aqui ele faz o mesmo esquema de for para armazenar e ler os dados de ContaCorrente via a VAR c, além de puxar elas pelos get do .c(conta)
        	}
        	// Mostrar em JOptionPane
            JOptionPane.showMessageDialog(null, dados, "Lista de Contas", JOptionPane.INFORMATION_MESSAGE);
            
            cs.saveContas(contas, arquivoSaida); //Chamada da função lá de ContaService que salva as multiplas contas num arquivo

        }catch (IOException e) {
        	
        }
    }
}
*/
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
