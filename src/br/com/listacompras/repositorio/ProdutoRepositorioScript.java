package br.com.listacompras.repositorio;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class ProdutoRepositorioScript extends ProdutoRepositorio {
	
	private static final String NOME_BANCO = "listacompras";
	private static final int VERSAO_BANCO = 1;
	private static final String NOME_TABELA = "produto";	
	private static final String SCRIPT_DATABASE_DELETE = "DROP TABLE IF EXISTS " + NOME_TABELA;
	private static final String[] SCRIPT_DATABASE_CREATE = new String[]
		{
		"create table produto ( " +
				"_id integer primary key autoincrement, " +
				"descricao text not null, " +
				"quantidade numeric, " +
				"valor numeric, " +
				"riscado text );"		
		};
	
	private SQLiteHelper dbHelper;

	public ProdutoRepositorioScript(Context context) {
		dbHelper = new SQLiteHelper(context, NOME_BANCO, VERSAO_BANCO, SCRIPT_DATABASE_CREATE, SCRIPT_DATABASE_DELETE);
		db = dbHelper.getWritableDatabase();
	}
	
	public static SQLiteDatabase getConexao(){
		return db;
	}
	

}
