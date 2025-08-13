package model;

import exception.SaldoInsuficienteException;

public abstract class Conta{
	protected int numero;
	protected String titular;
	protected double saldo;
	
	
	public Conta (int numero, String titular, double saldo ) {
		this.numero = numero;
		this.titular = titular;
		this.saldo = saldo;
		
	}
	
	public void sacar(double valor) throws SaldoInsuficienteException{
		System.out.println("nada");
	}
	
	public void depositar(double valor) {
		if (valor > 0) {
			saldo = saldo + valor;
		}
		
	}
	
	public void imprimirDados() {
		 System.out.println("Titular da conta: " +  titular);
		 System.out.println("Numero da conta: " + numero);
		 System.out.println("Saldo da conta: "+ saldo);
	}
	
	
}
