package br.com.listacompras.repositorio;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import br.com.listacompras.Constante;
import br.com.listacompras.Produto;

public class ProdutoRepositorio {
	
	@SuppressWarnings("unused")
	private static final String NOME_BDADOS = "listacompras";
	private static final String NOME_TABELA = "produto";
	
	protected static SQLiteDatabase db;	
	
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
		values.put(Produto.RISCADO, String.valueOf(produto.isRiscado()));
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
		values.put(Produto.RISCADO, String.valueOf(produto.isRiscado()));
		
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
		return db.delete(NOME_TABELA, where, whereArgs);
	}
	
	public Produto buscar(long id){
		// select * from produto where _id = ?
		Produto produto = null;
		Cursor cursor = db.query(true, NOME_TABELA, Produto.colunas, Produto._ID + "=" + id, null, null, null, null, null);
		if(cursor.getCount() > 0){
			cursor.moveToFirst();
			produto = new Produto();
			produto.setId(cursor.getLong(0));
			produto.setDescricao(cursor.getString(1));
			produto.setQuantidade(cursor.getInt(2));
			produto.setRiscado(Boolean.valueOf(cursor.getString(3)));						
		}
		return produto;		
	}
	
	public List<Produto> buscarProdutos(){
		List<Produto> produtos = new ArrayList<Produto>();
		Cursor cursor = getCursor();
		if(cursor.moveToFirst()){
			int _id = cursor.getColumnIndex(Produto._ID);
			int descricao = cursor.getColumnIndex(Produto.DESCRICAO);
			int quantidade = cursor.getColumnIndex(Produto.QUANTIDADE);
			int valor = cursor.getColumnIndex(Produto.VALOR);
			int riscado =  cursor.getColumnIndex(Produto.RISCADO);
			
			do{
				Produto produto = new Produto();
				produto.setId(cursor.getLong(_id));
				produto.setDescricao(cursor.getString(descricao));
				produto.setQuantidade(cursor.getInt(quantidade));
				produto.setValor(cursor.getDouble(valor));
				produto.setRiscado(Boolean.valueOf(cursor.getString(riscado)));
				produtos.add(produto);
			}while(cursor.moveToNext());
		}
		return produtos;
	}
	
	public Cursor getCursor(){
		try {
			// select * from produto
			return db.query(NOME_TABELA, Produto.colunas, null, null, null, null, null, null);
		} catch (SQLException e) {
			Log.e(Constante.TAG_LOG, "Erro ao buscar produtos: " + e.toString());
			return null;
		}
	}	
	
	public void fechar(){
		if(db != null) db.close();
	}
}
