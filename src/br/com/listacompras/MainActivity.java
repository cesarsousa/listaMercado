package br.com.listacompras;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.graphics.MaskFilter;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ListActivity {
	
	private AlertDialog alertDialog;
    
	private EditText editAddProduto;
	private Button btAddProduto;
	private Button btNovaLista;
	private TextView textTotalProdutos;	
	private TextView textValorTotal;	
	
	private static List<Produto> produtos = new ArrayList<Produto>();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
                
        editAddProduto = (EditText) findViewById(R.id.editAddProduto);
        btAddProduto = (Button) findViewById(R.id.btAddProduto);               
        btNovaLista = (Button) findViewById(R.id.btNovaLista);
        
        textTotalProdutos = (TextView) findViewById(R.id.footer_totalProdutos);
        textTotalProdutos.setText("Total Geral: " + produtos.size() + " produtos.");
        
        textValorTotal = (TextView) findViewById(R.id.footer_valorTotal);
        textValorTotal.setText("R$ " + getValorTotalDosProdutos());
        
        editAddProduto.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)){					
					Toast.makeText(MainActivity.this, "Para adicionar o produto pressione o botão add", Toast.LENGTH_SHORT).show();
					return true;
				}
				return false;
			}
		});
        
        btAddProduto.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String descricao = editAddProduto.getText().toString().trim();
				
				if("".equals(descricao)){
					Toast.makeText(MainActivity.this, "Digite o produto", Toast.LENGTH_SHORT).show();
				}else{
					produtos.add(new Produto(descricao));
					editAddProduto.setText("");
					editAddProduto.setFocusable(true);					
					
			        renderizarProdutos(); 
				}
			}		
		});
        
        btNovaLista.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				produtos = new ArrayList<Produto>();
				renderizarProdutos();				
			}
		});              
    }    

	@Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
    	super.onListItemClick(l, v, position, id);
    	
    	final Produto produtoSelecionado =  (Produto) this.getListAdapter().getItem(position);
    	
    	/*
    	 * configurar um campo de input no alert dialog.
    	 */
    	final EditText input = new EditText(this);
    	input.setSingleLine(true);
    	input.setHint("valor do produto");
    	input.setInputType(InputType.TYPE_CLASS_NUMBER);    	
    	
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setView(input);
    	builder.setTitle(produtoSelecionado.getDescricao().toUpperCase());
    	builder.setMessage("Pegar este produto e...");   	
    	
    	builder.setPositiveButton("Riscar", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				String valor = input.getText().toString().trim();
				List<Produto> listaAux = new ArrayList<Produto>();			
				
				for(Produto produto : produtos){
					if(produto.getDescricao().equals(produtoSelecionado.getDescricao())){						
						produto.setRiscado(true);
						produto.setValor(Double.parseDouble(valor));
					}
					listaAux.add(produto);
				}
				
				produtos = new ArrayList<Produto>();
				produtos.addAll(listaAux);
				
				renderizarProdutos();
			}
		});
    	
    	builder.setNegativeButton("Excluir", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				List<Produto> listaAux = new ArrayList<Produto>();
				
				for(Produto produto : produtos){
					if(!produto.getDescricao().equals(produtoSelecionado.getDescricao())){
						listaAux.add(produto);
					}
				}
				
				produtos = new ArrayList<Produto>();
				produtos.addAll(listaAux);
				
				renderizarProdutos();
			}
		});
    	
    	alertDialog = builder.create();
    	alertDialog.show();    	
    }
    
    private void renderizarProdutos() {
    	textTotalProdutos.setText("Total Geral: " + produtos.size() + " produtos.");
    	textValorTotal.setText("R$ " + getValorTotalDosProdutos());
    	Collections.sort(produtos);
		setListAdapter(new ProdutoAdapter(this, produtos));
	}
    
    private String getValorTotalDosProdutos() {
    	double valorTotal = 0.0;
    	for(Produto produto : produtos){
    		valorTotal += produto.getValor();
    	}
    	return String.valueOf(valorTotal);
	}
}
