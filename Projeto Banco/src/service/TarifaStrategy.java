package service;

import model.ContaCorrente;

public enum TarifaStrategy {
    FIXA {
        @Override
        public double calcularTarifa(ContaCorrente conta) {
            return 10.0; // aplica R$10,00 fixo
        }
    },
    PERCENTUAL {
        @Override
        public double calcularTarifa(ContaCorrente conta) {
            return conta.getSaldo() * 0.01; // aplica 1% do saldo
        }
    },
    ISENTA {
        @Override
        public double calcularTarifa(ContaCorrente conta) {
            return 0.0; // não aplica tarifa
        }
    };

    public abstract double calcularTarifa(ContaCorrente conta);
}
