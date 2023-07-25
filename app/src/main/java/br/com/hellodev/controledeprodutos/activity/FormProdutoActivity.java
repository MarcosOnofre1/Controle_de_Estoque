package br.com.hellodev.controledeprodutos.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.normal.TedPermission;

import java.security.Permission;
import java.util.List;

import br.com.hellodev.controledeprodutos.model.Produto;
import br.com.hellodev.controledeprodutos.R;

public class FormProdutoActivity extends AppCompatActivity {


    private static final int REQUEST_GALERIA = 100;
    private EditText edit_produto;
    private EditText edit_quantidade;
    private EditText edit_preco;

    private Produto produto;
    private ImageView imagem_produto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_produto);

        iniciaComponetes();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            produto = (Produto) bundle.getSerializable("produto");

            editProduto();
        }


    }

    public void abrirGaleria(View view){
        verificaPermissaoGaleria();

    }

    private void verificaPermissaoGaleria(){
        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                abrirGaleria2();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(FormProdutoActivity.this, "Permissão Negada.", Toast.LENGTH_SHORT).show();
            }
        };

        // SE POR ACASO QUEIRA PERMITIR MAIS COISAS COMO EX: CAMERA E ETC, SO POR UMA "," E CONTINUAR O CODE
        showDialogPermission(permissionListener, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE});


    }


    private void abrirGaleria2(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_GALERIA);
    }

    //alerta de permissão para o usuario
    private void showDialogPermission(PermissionListener permissionListener, String[] permissoes){
        TedPermission.create()
                .setPermissionListener(permissionListener)
                .setDeniedTitle("Permissão negada.")
                .setDeniedMessage("Você negou a permissão para acessar a galeria do dispositivo, deseja permitir?")
                .setGotoSettingButtonText("SIM")
                .setDeniedCloseButtonText("NÃO")
                .setPermissions(permissoes)
                .check();

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

    private void iniciaComponetes() {

        edit_produto = findViewById(R.id.edit_produto);
        edit_quantidade = findViewById(R.id.edit_quantidade);
        edit_preco = findViewById(R.id.edit_preco);
        imagem_produto = findViewById(R.id.imagem_produto);

    }


}