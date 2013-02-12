package br.com.listacompras;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class ProdutoRepositorio {
	
	private static final String NOME_BDADOS = "listacompras";
	private static final String NOME_TABELA = "produto";
	
	protected SQLiteDatabase db;
	
	public ProdutoRepositorio(Context context) {
		db = context.openOrCreateDatabase(NOME_BDADOS, Context.MODE_PRIVATE, null);
	}
	
	public long salvar(Produto produto){
		long id = produto.getId();
		if(id == 0){
			id = inserir(produto);
		}else{
			atualizar(produto);
		}
		return id;
	}	

	private long inserir(Produto produto) {
		ContentValues values = new ContentValues();
		values.put(Produto.DESCRICAO, produto.getDescricao());
		values.put(Produto.QUANTIDADE, produto.getQuantidade());
		values.put(Produto.VALOR, produto.getValor());
		values.put(Produto.RISCADO, produto.isRiscado());
		return inserir(values); 
	}

	private long inserir(ContentValues values) {
		return db.insert(NOME_TABELA, "", values);
	}
	
	private int atualizar(Produto produto) {
		ContentValues values = new ContentValues();
		values.put(Produto.DESCRICAO, produto.getDescricao());
		values.put(Produto.QUANTIDADE, produto.getQuantidade());
		values.put(Produto.VALOR, produto.getValor());
		values.put(Produto.RISCADO, produto.isRiscado());
		
		String _id = String.valueOf(produto.getId());
		String where = Produto._ID + "=?";
		String[] whereArgs = new String[]{_id};
		
		return atualizar(values, where, whereArgs);		
	}

	private int atualizar(ContentValues values, String where, String[] whereArgs) {
		return db.update(NOME_TABELA, values, where, whereArgs);
	}
	
	public int deletar(long id){
		String where = Produto._ID + "=?";
		String _id = String.valueOf(id);
		String[] whereArgs = new String[]{_id};
		return deletar(where, whereArgs);
	}

	private int deletar(String where, String[] whereArgs) {
		// TODO livro android pg 379
		return 0;
	}
	

}
