package br.com.listacompras;

import android.provider.BaseColumns;


public class Produto implements Comparable<Produto>, BaseColumns{
	
	public static String[] colunas = {Produto._ID, Produto.DESCRICAO, Produto.QUANTIDADE, Produto.VALOR, Produto.RISCADO};
	
	public static final String DESCRICAO = "descricao";
	public static final String QUANTIDADE = "quantidade";
	public static final String VALOR = "valor";
	public static final String RISCADO = "riscado";
	
	private long id;
	private String descricao;
	private int quantidade;
	private double valor;
	private boolean riscado;
	
	public Produto(){}
	
	public Produto(String descricao) {
		this.descricao = descricao;
		this.quantidade = 0;
	}
	
	public Produto(String descricao, int quantidade) {
		this.descricao = descricao;
		this.quantidade = quantidade;
	}
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	
	public String getDescricao() {
		return descricao;
	}	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public int getQuantidade() {
		return quantidade;
	}
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
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
