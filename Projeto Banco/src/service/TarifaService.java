package service;

import model.ContaCorrente;

public class TarifaService {

    private TarifaStrategy estrategia; // Estratégia selecionada

    // ---------- Construtor ----------
    public TarifaService(TarifaStrategy estrategia) {
        this.estrategia = estrategia;
    }

    // ---------- Calcular tarifa ----------
    public double calcularTarifa(ContaCorrente conta) {
        // Chama o método do enum para calcular a tarifa conforme a estratégia
        return estrategia.calcularTarifa(conta);
    }

    // ---------- Alterar estratégia ----------
    public void setEstrategia(TarifaStrategy estrategia) {
        this.estrategia = estrategia;
    }
}
