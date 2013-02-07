package br.com.listacompras;

public class Produto implements Comparable<Produto>{
	
	private String descricao;
	private double valor;
	private boolean riscado;	
	
	public Produto(String descricao) {
		this.descricao = descricao;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public boolean isRiscado() {
		return riscado;
	}
	public void setRiscado(boolean riscado) {
		this.riscado = riscado;
	}
	public void setValor(double valor) {
		this.valor = valor;
	}
	public double getValor() {
		return valor;
	}	
	
	@Override
	public int compareTo(Produto produto) {
		return (this.descricao).compareTo(produto.getDescricao());
	}	
}
