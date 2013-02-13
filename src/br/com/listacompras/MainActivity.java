package br.com.listacompras;

import java.util.Collections;
import java.util.List;

import android.R.integer;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import br.com.listacompras.repositorio.ProdutoRepositorio;
import br.com.listacompras.repositorio.ProdutoRepositorioScript;

@SuppressLint("DefaultLocale")
public class MainActivity extends ListActivity {
	
	private AlertDialog alertDialog;
    
	private EditText editAddProduto;
	private Button btAddProduto;
	private Button btNovaLista;
	private TextView textTotalProdutos;	
	private TextView textValorTotal;	
	
	private ProdutoRepositorio repositorio;
	private static List<Produto> produtos;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        repositorio = new ProdutoRepositorioScript(this);
        produtos = repositorio.buscarProdutos();        
                
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
					
					repositorio.salvar(new Produto(descricao));					
					editAddProduto.setText("");
					editAddProduto.setFocusable(true);					
					
			        renderizarProdutos(); 
				}
			}		
		});
        
        btNovaLista.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
								
				for(Produto produto : repositorio.buscarProdutos()){
					repositorio.deletar(produto.getId());
				}
				
				renderizarProdutos();				
			}
		});
        
        renderizarProdutos();
    }    

	@Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
    	super.onListItemClick(l, v, position, id);
    	
    	final Produto produtoSelecionado =  (Produto) this.getListAdapter().getItem(position);
    	
    	LayoutInflater factory = LayoutInflater.from(this);
    	final View viewFormAlterar = factory.inflate(R.layout.alterar_produto, null);
    	
    	/*
    	 * configurar um campo de input no alert dialog.
    	 */
    	/*final EditText inputQuantidade = new EditText(this);
    	inputQuantidade.setSingleLine(true);
    	inputQuantidade.setHint("QTD");
    	inputQuantidade.setInputType(InputType.TYPE_CLASS_NUMBER); 
    	
    	final EditText inputValor = new EditText(this);
    	inputValor.setSingleLine(true);
    	inputValor.setHint("valor do produto");
    	inputValor.setInputType(InputType.TYPE_CLASS_NUMBER);   */ 	
    	
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setView(viewFormAlterar);
    	builder.setTitle(produtoSelecionado.getDescricao());
    	builder.setMessage("Pegar este produto e...");   	
    	
    	builder.setPositiveButton("Riscar", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				final EditText editQuantidade = (EditText) viewFormAlterar.findViewById(R.id.alterarQuantidade);
				String strQuantidade = editQuantidade.getText().toString().trim();
				int quantidade = "".equals(strQuantidade) ? 0 : Integer.parseInt(strQuantidade);
							
					final EditText editValor = (EditText) viewFormAlterar.findViewById(R.id.alterarValor);
					
					String strValor = editValor.getText().toString().trim();				
					double valor = "".equals(strValor) ? 0 : Double.parseDouble(editValor.getText().toString().trim());
					
					produtoSelecionado.setQuantidade(quantidade);
					produtoSelecionado.setValor(quantidade == 0 ? 0 : valor);
					produtoSelecionado.setRiscado(true);				
					
					repositorio.salvar(produtoSelecionado);				
					
					renderizarProdutos();
				}
			
		});
    	
    	builder.setNegativeButton("Excluir", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				repositorio.deletar(produtoSelecionado.getId());
				
				renderizarProdutos();
			}
		});
    	
    	alertDialog = builder.create();
    	alertDialog.show();    	
    }
    
    private void renderizarProdutos() {
    	produtos = repositorio.buscarProdutos();    	
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
        
    @Override
    protected void onPause() {
    	repositorio.fechar();
    	super.onPause();
    }
    
    
}
