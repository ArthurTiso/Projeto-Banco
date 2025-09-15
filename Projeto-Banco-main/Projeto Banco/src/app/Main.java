package app;

import javax.swing.JOptionPane;
import model.ContaCorrente;
import service.ContaService;
import exception.SaldoInsuficienteException;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        // TODO Auto-generated method stub

        ContaService cs = new ContaService();
        String arquivoEntrada = "conta.txt";
        String arquivoSaida = "conta_atualizada.txt";
        double valorSaque;

        try {
            ContaCorrente conta = cs.lerConta(arquivoEntrada);

            String dados = "Conta: " + conta.getNumero() + "\n" +
                           "Titular: " + conta.getTitular() + "\n" +
                           "Saldo: " + String.format("%.2f", conta.getSaldo());
            JOptionPane.showMessageDialog(null, dados, "Conta carregada", JOptionPane.INFORMATION_MESSAGE);

            while (true) {
                String entrada = JOptionPane.showInputDialog(null, "Informe o valor para o saque: ", "Saque", JOptionPane.QUESTION_MESSAGE);

                if (entrada == null) { // Caso de cancelamento
                    JOptionPane.showMessageDialog(null, "Operação cancelad!!", "Cancelado", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (entrada.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Valor não pode ser vazio. Tente novamente!", "Erro", JOptionPane.ERROR_MESSAGE);
                    continue;
                }

                try {
                    valorSaque = Double.parseDouble(entrada);
                    break;
                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(null, "Valor inválido. Digite um número.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }

                try {
                    cs.sacar(conta, valorSaque);
                    JOptionPane.showMessageDialog(null, "Saque realizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

                    // Salvar dados
                    try {
                        cs.salvarConta(conta, arquivoSaida);
                        JOptionPane.showMessageDialog(null, "Dados salvos em" + arquivoSaida, "Arquivo salvo", JOptionPane.INFORMATION_MESSAGE);

                    } catch (IOException e) {
                        JOptionPane.showMessageDialog(null, "Erro ao salvar arquivo: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    }

                } catch (SaldoInsuficienteException sie) {
                    JOptionPane.showMessageDialog(null, sie.getMessage(), "Saldo insuficiente", JOptionPane.ERROR_MESSAGE);
                }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Erro de I/O: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
