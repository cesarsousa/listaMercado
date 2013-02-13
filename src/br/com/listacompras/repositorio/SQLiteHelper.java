package br.com.listacompras.repositorio;

import br.com.listacompras.Constante;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteHelper extends SQLiteOpenHelper {

	private String[] scriptSQLCreate;
	private String scriptSQLDelete;
	private String nomeBanco;
	
	public SQLiteHelper(Context context, String nomeBanco, int versaoBanco, String[] scriptSQLCreate, String scriptSQLDelete){
		super(context, nomeBanco, null, versaoBanco);
		this.scriptSQLCreate = scriptSQLCreate;
		this.scriptSQLDelete = scriptSQLDelete;
		this.nomeBanco = nomeBanco;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// criar um novo banco de dados
		Log.i(Constante.TAG_LOG, "Criando banco de dados " + nomeBanco);
		int qtdScripts = scriptSQLCreate.length;
		for(int i = 0; i < qtdScripts; i++){
			String sql = scriptSQLCreate[i];
			Log.i(Constante.TAG_LOG, sql);
			db.execSQL(sql);
		}		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(Constante.TAG_LOG, "Atualizando banco de dados. Versao antiga: " + oldVersion + ". Versao nova: " + newVersion + ". Todos os registros serao apagados.");
		db.execSQL(scriptSQLDelete);
		onCreate(db);		
	}

}
