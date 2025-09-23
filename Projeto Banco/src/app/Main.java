package app;

import java.io.IOException;
import java.util.List;
import java.util.Comparator;
import java.util.function.Predicate;
import javax.swing.JOptionPane;

import model.ContaCorrente;
import service.ContaService;
import service.TarifaStrategy;
import service.TarifaService;

//Main normal
public class Main {

    public static void main(String[] args) {
        ContaService cs = new ContaService();
        String arquivoEntrada = "contas.txt";
        String arquivoSaida = "contas_atualizadas.txt";

        try {  

            //Leitura de contas
            List<ContaCorrente> contas = cs.LerContas(arquivoEntrada);

            // Exibição das contas
            cs.exibirContas("Contas foram carregadas!", contas);

            // Adicionar conta
            int addConta = JOptionPane.showConfirmDialog(null, "Deseja adicionar uma nova conta?", "Nova conta", JOptionPane.YES_NO_OPTION);
            if (addConta == JOptionPane.YES_OPTION) {
                cs.adicionarConta(contas, arquivoSaida);
                cs.exibirContas("Contas atualizadas após adição:", contas);
            }

            // Excluir conta
            int delConta = JOptionPane.showConfirmDialog(null, "Deseja excluir alguma conta?", "Excluir conta", JOptionPane.YES_NO_OPTION);
            if (delConta == JOptionPane.YES_OPTION) {
                cs.excluirConta(contas, arquivoSaida);
                cs.exibirContas("Contas atualizadas após exclusão:", contas);
            }

            // Filtrar contas acima de 5000
            
            int filt5000 = JOptionPane.showConfirmDialog(null, "Deseja filtrar as contas acima de 5000?", "Filtrar", JOptionPane.YES_OPTION);
            if (filt5000 == JOptionPane.YES_OPTION) {
            Predicate<ContaCorrente> saldoMaior5000 = c -> c.getSaldo() > 5000;
            List<ContaCorrente> contasSaldoAlto = cs.filtrarContas(contas, saldoMaior5000);
            cs.exibirContas("Contas com saldo > 5000:", contasSaldoAlto);
            }
            
            int filtPar = JOptionPane.showConfirmDialog(null, "Deseja filtrar as contas com numero par?", "Filtrar", JOptionPane.YES_OPTION);
            if (filtPar == JOptionPane.YES_OPTION) {
            Predicate<ContaCorrente> numeroPar = c -> c.getNumero() % 2 == 0;
            List<ContaCorrente> contasNumeroPar = cs.filtrarContas(contas, numeroPar);
            cs.exibirContas("Contas com número par:", contasNumeroPar);
            }

         // Comparator de saldos
            Comparator<ContaCorrente> porSaldoDecrescente = (c1, c2) -> Double.compare(c2.getSaldo(), c1.getSaldo());
            Comparator<ContaCorrente> porTitular = (c1, c2) -> c1.getTitular().compareToIgnoreCase(c2.getTitular());

            //Coloca em list e exibe por saldo
            List<ContaCorrente> ordenadasPorSaldo = cs.ordenarContas(contas, porSaldoDecrescente);
            cs.exibirContas("Contas ordenadas por saldo (decrescente):", ordenadasPorSaldo);

          //Coloca em list e exibe por titular
            List<ContaCorrente> ordenadasPorTitular = cs.ordenarContas(contas, porTitular);
            cs.exibirContas("Contas ordenadas por titular (alfabética):", ordenadasPorTitular);

            // Tarifas com o Strategy lá do Service

            int aplicarTarifa = JOptionPane.showConfirmDialog(null, "Deseja aplicar tarifas às contas?", "Aplicar tarifas", JOptionPane.YES_NO_OPTION);
            if (aplicarTarifa == JOptionPane.YES_OPTION) {
                for (ContaCorrente c : contas) { 
                    String[] opcoes = {"FIXA", "PERCENTUAL", "ISENTA"}; // Perguntar ao usuário qual estratégia aplicar
                    String escolha = (String) JOptionPane.showInputDialog(
                            null,
                            "Escolha a estratégia de tarifa para a conta " + c.getNumero() + ":",
                            "Estratégia de Tarifa",
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            opcoes,
                            opcoes[0] // padrão FIXA
                    );

                    if (escolha != null) { // Se usuário não cancelou
                        TarifaStrategy estrategia = TarifaStrategy.valueOf(escolha);
                        TarifaService ts = new TarifaService(estrategia); // Cria serviço com a estratégia escolhida
                        
                        double tarifa = ts.calcularTarifa(c);      // Calcula tarifa
                        c.setSaldo(c.getSaldo() - tarifa);         // Aplica tarifa subtraindo do saldo
                        
                        JOptionPane.showMessageDialog(null, "Conta: " + c.getNumero() + " - Tarifa aplicada (" + estrategia + "): " + tarifa + "\nNovo saldo: " + c.getSaldo());
                    }
                }
            }


            // ---------- Salvar alterações finais ----------
            cs.saveContas(contas, arquivoSaida);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
        }
    }
}


