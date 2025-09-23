package model;

import exception.SaldoInsuficienteException;

public abstract class Conta{
	protected int numero;
	protected String titular;
	protected double saldo;
	
	
	public Conta (int numero, String titular, double saldoInicial ) {
		this.numero = numero;
		this.titular = titular;
		this.saldo = saldoInicial;
		
	}
	
	//saque
	public void sacar(double valor) throws SaldoInsuficienteException{	
		if (valor <= 0) {
			throw new IllegalArgumentException("Valor do saqe deve ser positivo!");
		}
	}
	
	//deposito
	public void depositar(double valor) {
		if (valor <= 0) {
			throw new IllegalArgumentException("Valor do depósito deve ser positivo!");
		}
		saldo = valor + saldo;
		
	}
	
	public void imprimirDados() {
		 System.out.println("Titular da conta: " +  titular);
		 System.out.println("Numero da conta: " + numero);
		 System.out.println("Saldo da conta: R$"+ String.format("%.2f", saldo));
	}

	public int getNumero() {
		return numero;
	}


	public String getTitular() {
		return titular;
	}


	public double getSaldo() {
		return saldo;
	}
	
	public void setSaldo(double saldo) {
	    this.saldo = saldo;
	}


	

	
	
}
