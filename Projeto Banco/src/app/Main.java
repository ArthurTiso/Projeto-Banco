package app;

import java.util.List;

import javax.swing.JOptionPane;
import model.ContaCorrente;
import service.ContaService;
import view.ContaGUI;
import exception.SaldoInsuficienteException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

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
        	
        	String dados = "Contas foram carregadas!\n\n"; //Dados carrega inicialmete a string anunciando que as contas foram carregadas, e logo em seguida (pelo uso do for) carrega também os dados de cada uma das contas (obtidos via gets)
        	for (ContaCorrente c : contas) {
        		dados = dados + "Conta: " + c.getNumero() + " " + " Titular: " + c.getTitular() + " " + " Saldo: " + c.getSaldo() + " " + "\n"; 
        		//Aqui ele faz o mesmo esquema de for para armazenar e ler os dados de ContaCorrente via a VAR c, além de puxar elas pelos get do .c(conta)
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
            		
            		//Chama o processo de gerar as contas salvas, agora com os dados atualizados
            		dados = "Contas foram carregadas!\n\n"; //Dados carrega inicialmete a string anunciando que as contas foram carregadas, e logo em seguida (pelo uso do for) carrega também os dados de cada uma das contas (obtidos via gets)
                	for (ContaCorrente c : contas) {
                		dados = dados
                			+ "Conta: " + c.getNumero() + " "
                			+ " Titular: " + c.getTitular() + " "
                			+ " Saldo: " + c.getSaldo() + " "
                			+"\n"; //Aqui ele faz o mesmo esquema de for para armazenar e ler os dados de ContaCorrente via a VAR c, além de puxar elas pelos get do .c(conta)         		
                	}          		
                	JOptionPane.showMessageDialog(null, dados, "Lista de Contas", JOptionPane.INFORMATION_MESSAGE); //Mostra contas novamente
                	
            	} catch (NumberFormatException nfe) {
            		JOptionPane.showMessageDialog(null, "Erro: " + nfe.getMessage() + "\n Insira caracteres válidos");
            		
            	} catch (IOException e) {
            		JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage() + "\n Falha ao salvar conta!" );
            	}
            }
            	//Exclusão de contas
            	
            	int delConta = JOptionPane.showConfirmDialog(null, "Deseja excluir alguma conta?", "excluir conta", JOptionPane.YES_NO_OPTION);
            	if (delConta == JOptionPane.YES_OPTION) {
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
            			
            		if (contaRemover != null) { //Se o contaRemover for nulo, o contas.remove deleta a conta utilziando a var armazenada. O cs.savecontas salva tudo e então mostra ao usuário sucesso ou falha na operação
            			contas.remove(contaRemover);
            			cs.saveContas(contas, arquivoSaida);
            			JOptionPane.showMessageDialog(null, "Conta removida com Sucerro!");
            		} else JOptionPane.showMessageDialog(null, "Conta não encontrada");
            		
            		dados = "Contas foram carregadas!\n\n"; //Dados carrega inicialmete a string anunciando que as contas foram carregadas, e logo em seguida (pelo uso do for) carrega também os dados de cada uma das contas (obtidos via gets)
                	for (ContaCorrente c : contas) {
                		dados = dados + "Conta: " + c.getNumero() + " " + " Titular: " + c.getTitular() + " " + " Saldo: " + c.getSaldo() + " " +"\n"; 
                		//Aqui ele faz o mesmo esquema de for para armazenar e ler os dados de ContaCorrente via a VAR c, além de puxar elas pelos get do .c(conta)         		
                	}          		
                	JOptionPane.showMessageDialog(null, dados, "Lista de Contas", JOptionPane.INFORMATION_MESSAGE); //Mostra contas novamente
            		
            		}catch (NumberFormatException nfe) {
            			JOptionPane.showMessageDialog(null, "Erro" + nfe.getMessage() + "Digite um numero valido");
            		} catch (IOException e) {
            			JOptionPane.showMessageDialog(null, "Erro" + e.getMessage() + "Erro ao deletar conta");
            		}
            	}
        
        int ordenar = JOptionPane.showConfirmDialog(null, "Deseja ordenar as contas por acima de 5000?", "Ordenar", JOptionPane.YES_NO_OPTION);
        if (ordenar == JOptionPane.YES_OPTION) { //Se usuário selecionar SIM
        	
        	Predicate<ContaCorrente> saldoMaior5000 = c -> c.getsaldo() > 5000;
        	
        	String dadosOrdenados = "Contas ordenadas por saldo: \n\n";
        	for (ContaCorrente c : contas) {
        		dadosOrdenados += "Conta: " +c.getNumero() + "Titular: " + c.getTitular() + "Saldo: " + c.getSaldo() + "\n\n";
        	} //Mesmo esquema de sempre, armazena os valores em loop no dadosOrdenados enquanto pega eles via Get enquanto houverem contas
        	JOptionPane.showMessageDialog(null, dadosOrdenados + "Lista Ordenada");
        }   
     
        cs.saveContas(contas, arquivoSaida);
        
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
        }
    }
}

            		
    

//Main com GUI
/*
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

