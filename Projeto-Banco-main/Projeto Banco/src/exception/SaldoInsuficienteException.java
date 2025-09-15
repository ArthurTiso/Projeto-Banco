package exception;

import java.io.IOException;

import javax.swing.JOptionPane;

public class SaldoInsuficienteException extends Exception {
	
	public SaldoInsuficienteException(String mensagem) {
        super(mensagem);
    }

    public SaldoInsuficienteException(String mensagem, Throwable causa) {
        super(mensagem, causa);
}
}