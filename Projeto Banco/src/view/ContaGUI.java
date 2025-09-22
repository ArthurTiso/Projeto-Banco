package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List; 
import javax.swing.*;
import java.awt.event.*;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.ContaCorrente;
import service.ContaService;

import model.ContaCorrente;
import service.ContaService ;
import exception.SaldoInsuficienteException;

/**
 * ContaGUI - Janela mínima para exibir uma ContaCorrente e permitir
 * operações de Saque e Depósito seguindo o estilo do projeto-base.
 *
 * Use: new ContaGUI(conta, contaService);
 */
public class ContaGUI extends JFrame {

    private static final long serialVersionUID = 1L;

    private List<ContaCorrente> contas; // Lista de contaCorrente na var contas
    private ContaCorrente contaSelecionada;
    
    private ContaService service;            

    private JLabel lblNumero;                
    private JLabel lblTitular;
    private JLabel lblSaldo;

    private JButton btnSacar;                
    private JButton btnDepositar;

    private JList<String> listaContas;
    private DefaultListModel<String> listModel;

    
    

    // Construtor principal: recebe a conta carregada e o serviço
    public ContaGUI(List<ContaCorrente> contas, ContaService service) {
        this.contas = contas;
        this.service = service;

        setTitle("Gerenciador de Contas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));


        // Painel de informações
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(150);

        listModel = new DefaultListModel<>();
        for (ContaCorrente c : contas) {
            listModel.addElement(c.getNumero() + " - " + c.getTitular());
        }
        listaContas = new JList<>(listModel);
        listaContas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        splitPane.setLeftComponent(new JScrollPane(listaContas));

        // Painel de informações (detalhes da conta)
        JPanel painelInfo = new JPanel(new GridLayout(3, 2, 5, 5));
        painelInfo.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        painelInfo.add(new JLabel("Número:"));
        lblNumero = new JLabel("-");
        painelInfo.add(lblNumero);

        painelInfo.add(new JLabel("Titular:"));
        lblTitular = new JLabel("-");
        painelInfo.add(lblTitular);

        painelInfo.add(new JLabel("Saldo:"));
        lblSaldo = new JLabel("-");
        painelInfo.add(lblSaldo);

        splitPane.setRightComponent(painelInfo);

        add(splitPane, BorderLayout.CENTER);

        // Painel de botões
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnSacar = new JButton("Sacar");
        btnDepositar = new JButton("Depositar");
        painelBotoes.add(btnSacar);
        painelBotoes.add(btnDepositar);
        add(painelBotoes, BorderLayout.SOUTH);
        
        btnSacar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (contaSelecionada == null) {
                    JOptionPane.showMessageDialog(ContaGUI.this,
                            "Nenhuma conta selecionada!",
                            "Erro",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String entrada = JOptionPane.showInputDialog(
                        ContaGUI.this,
                        "Informe o valor para saque:",
                        "Saque",
                        JOptionPane.QUESTION_MESSAGE);

                if (entrada == null) return; // cancelado
                entrada = entrada.trim();
                if (entrada.isEmpty()) {
                    JOptionPane.showMessageDialog(ContaGUI.this,
                            "Valor vazio!",
                            "Erro",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                double valor;
                try {
                    valor = Double.parseDouble(entrada);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(ContaGUI.this,
                            "Valor inválido. Use ponto para decimal.",
                            "Erro",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    // 1. Realizar saque
                    service.sacar(contaSelecionada, valor);

                    // 2. Salvar todas as contas no arquivo
                    service.saveContas(contas, entrada);

                    // 3. Atualizar labels
                    atualizarLabels();

                    JOptionPane.showMessageDialog(ContaGUI.this,
                            "Saque realizado com sucesso!",
                            "Sucesso",
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (exception.SaldoInsuficienteException sie) {
                    JOptionPane.showMessageDialog(ContaGUI.this,
                            sie.getMessage(),
                            "Saldo insuficiente",
                            JOptionPane.ERROR_MESSAGE);
                } catch (IOException ioex) {
                    JOptionPane.showMessageDialog(ContaGUI.this,
                            "Erro ao salvar: " + ioex.getMessage(),
                            "Erro",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        btnDepositar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (contaSelecionada == null) {
                    JOptionPane.showMessageDialog(ContaGUI.this,
                            "Nenhuma conta selecionada!",
                            "Erro",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String entrada = JOptionPane.showInputDialog(
                        ContaGUI.this,
                        "Informe o valor para depósito:",
                        "Depósito",
                        JOptionPane.QUESTION_MESSAGE);

                if (entrada == null) return; // cancelado
                entrada = entrada.trim();
                if (entrada.isEmpty()) {
                    JOptionPane.showMessageDialog(ContaGUI.this,
                            "Valor vazio!",
                            "Erro",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                double valor;
                try {
                    valor = Double.parseDouble(entrada);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(ContaGUI.this,
                            "Valor inválido. Use ponto para decimal.",
                            "Erro",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    // 1. Realizar depósito
                    contaSelecionada.depositar(valor);

                    // 2. Salvar todas as contas no arquivo
                    service.saveContas(contas, "contas_atualizadas.txt");

                    // 3. Atualizar labels
                    atualizarLabels();

                    JOptionPane.showMessageDialog(ContaGUI.this,
                            "Depósito realizado com sucesso!",
                            "Sucesso",
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (IllegalArgumentException iae) {
                    JOptionPane.showMessageDialog(ContaGUI.this,
                            iae.getMessage(),
                            "Erro",
                            JOptionPane.ERROR_MESSAGE);
                } catch (IOException ioex) {
                    JOptionPane.showMessageDialog(ContaGUI.this,
                            "Erro ao salvar: " + ioex.getMessage(),
                            "Erro",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });



        // Listener: quando selecionar na lista → atualizar labels
        listaContas.addListSelectionListener(e -> {
            int idx = listaContas.getSelectedIndex();
            if (idx >= 0) {
                contaSelecionada = contas.get(idx);
                atualizarLabels();
            }
        });

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Atualiza labels de acordo com contaSelecionada
    private void atualizarLabels() {
        if (contaSelecionada != null) {
            lblNumero.setText(String.valueOf(contaSelecionada.getNumero()));
            lblTitular.setText(contaSelecionada.getTitular());
            lblSaldo.setText(String.format("R$ %.2f", contaSelecionada.getSaldo()));
        }
    }
}

