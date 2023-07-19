package br.com.hellodev.controledeprodutos.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import br.com.hellodev.controledeprodutos.model.Produto;
import br.com.hellodev.controledeprodutos.R;

public class FormProdutoActivity extends AppCompatActivity {

    private EditText edit_produto;
    private EditText edit_quantidade;
    private EditText edit_preco;

    private Produto produto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_produto);


        edit_produto = findViewById(R.id.edit_produto);
        edit_quantidade = findViewById(R.id.edit_quantidade);
        edit_preco = findViewById(R.id.edit_preco);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            produto = (Produto) bundle.getSerializable("produto");

            editProduto();
        }


    }

    private void editProduto() {

        edit_produto.setText(produto.getNome());
        edit_quantidade.setText(String.valueOf(produto.getEstoque()));
        edit_preco.setText(String.valueOf(produto.getValor()));

    }

    public void salvarProduto(View view) {

        String nome = edit_produto.getText().toString();
        String quantidade = edit_quantidade.getText().toString();
        String preco = edit_preco.getText().toString();

        if (!nome.isEmpty()) {
            if (!quantidade.isEmpty()) {

                int qtd = Integer.parseInt(quantidade);
                if (qtd >= 1) {

                    if (!preco.isEmpty()) {

                        double valorProduto = Double.parseDouble(preco);

                        if (valorProduto > 0) {

                            if (produto == null) produto = new Produto();
                            produto.setNome(nome);
                            produto.setEstoque(qtd);
                            produto.setValor(valorProduto);

                            produto.salvarProduto();

                            finish();

                        } else {

                            edit_preco.requestFocus();
                            edit_preco.setError("Digite um valor maior que 0.");
                        }


                    } else {

                        edit_preco.requestFocus();
                        edit_preco.setError("Informe o valor do produto");
                    }

                } else {

                    edit_quantidade.requestFocus();
                    edit_quantidade.setError("Informe um valor maior que 0.");
                }

            } else {
                edit_quantidade.requestFocus();
                edit_quantidade.setError("Informe um valor válido.");
            }

        } else {
            edit_produto.requestFocus();
            edit_produto.setError("Informe o nome do produto.");
        }


    }


}