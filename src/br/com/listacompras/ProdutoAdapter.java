package br.com.listacompras;

import java.util.List;

import android.R.color;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

public class ProdutoAdapter extends BaseAdapter implements ListAdapter {
	
	private Context context;
	private List<Produto> produtos;
	
	public ProdutoAdapter(Context context, List<Produto> produtos) {
		this.context = context;
		this.produtos = produtos;
	}

	@Override
	public int getCount() {		
		return produtos.size();
	}

	@Override
	public Object getItem(int position) {
		return produtos.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		
		Produto produto = produtos.get(position);
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View stringView = inflater.inflate(R.layout.list_adapter_produto, null);
		
		TextView textViewNomeProduto = (TextView) stringView.findViewById(R.id.listAdapterOrcamento_nomeProduto);
		textViewNomeProduto.setText(produto.getDescricao());
		
		if(produto.isRiscado()){
			textViewNomeProduto.setTextColor(Color.RED);
			
			String tempString = produto.getDescricao();
			SpannableString spanString = new SpannableString(tempString);
			spanString.setSpan(new UnderlineSpan(), 0, spanString.length(), 0);
			spanString.setSpan(new StyleSpan(Typeface.BOLD), 0, spanString.length(), 0);
			spanString.setSpan(new StyleSpan(Typeface.ITALIC), 0, spanString.length(), 0);
			textViewNomeProduto.setText(spanString);	
			
		}	
		
		return stringView;
	}
}
