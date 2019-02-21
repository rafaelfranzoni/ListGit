package com.br.list.list.list.git.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.br.list.list.list.git.R;

import static android.widget.Toast.LENGTH_LONG;

/**
 * Created by Rafael Magni 20/02/2019
 */
public class LoginActivity extends AppCompatActivity {

    public static final String PREFERENCES = "br.com.list.git.preferences";

    private Button btnLogar, btnLimpa;

    private EditText edtUsuario, edtSenha;

    private TextView informa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSystemService(Context.INPUT_METHOD_SERVICE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        btnLogar = findViewById(R.id.btnLoga);
        btnLimpa = findViewById(R.id.btnLimpa);
        edtUsuario = (EditText)this.findViewById(R.id.edtUser);
        edtSenha = (EditText)this.findViewById(R.id.edtPassword);
        informa = findViewById(R.id.tvInformativo);

        btnLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usu = getSharedPreferences("LOGIN");
                String senha = getSharedPreferences("SENHA");
                informa.setText("");

                if (validateEmail(edtUsuario.getText().toString()) && edtSenha.length()>=6){

                    if (usu==null && senha==null){
                        setSharedPreferences("LOGIN", edtUsuario.getText().toString());
                        setSharedPreferences("SENHA", edtSenha.getText().toString());

                        Toast.makeText(LoginActivity.this, "OK, usuário incluído com sucesso", LENGTH_LONG).show();

                        //Chama a proxima tela
                        Intent it = new Intent(LoginActivity.this, ListActivity.class);
                        LoginActivity.this.startActivity(it);
                        //Finaliza a tela atual
                        finish();
                    }else if (usu.equals(edtUsuario.getText().toString()) && senha.equals(edtSenha.getText().toString())){
                        Toast.makeText(LoginActivity.this, "OK, usuário configurado com sucesso", LENGTH_LONG).show();

                        Intent it = new Intent(LoginActivity.this, ListActivity.class);
                        LoginActivity.this.startActivity(it);
                        finish();
                    }else {
                        //Inseri a mensagem no TextView
                        informa.setText("***Caso todas essas opções estejam corretas clique sobre o botão " +
                                "‘Limpar os dados salvos do login’, e clique novamente sobre p botão ‘Logar’.");
                    }

                }else {

                    Toast.makeText(LoginActivity.this, "Ops, algo esta errado nas suas informações", LENGTH_LONG).show();

                    //Inseri a mensagem no TextView
                    informa.setText("***Verifique se o e-mail informado contem: ‘@’ seguido de pelo " +
                            "menos uma letra minúscula, e ‘.’ seguido de pelo menos uma letra minúscula.\n" +
                            "\n" +
                            "\n" +
                            "***Verifique se a senha informada é maior que 6 caracteres.\n" +
                            "\n" +
                            "\n" +
                            "***Caso todas essas opções estejam corretas clique sobre o botão " +
                            "‘Limpar os dados salvos do login’, e clique novamente sobre p botão ‘Logar’.");
                }

            }
        });

        btnLimpa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Limpa as info salva na memoria
                clearSharedPreferences("LOGIN");
                clearSharedPreferences("SENHA");

                Toast.makeText(LoginActivity.this, "Ok, usuário excluído da memoria", LENGTH_LONG).show();
            }
        });
    }

    /**
     * Salva informação na memoria do aparelho.
     *
     * @param tag Facilitador de busca de informação tipo String.
     * @param info Informação a ser salva tipo String.
     *
     * Created by Rafael Magni 20/02/2019
     */
    private void setSharedPreferences(String tag, String info) {
        SharedPreferences prefs = getSharedPreferences(PREFERENCES, 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(tag, info);
        editor.commit();
    }

    /**
     * Pega informação salva na memoria.
     *
     * @param tag Facilitador de busca tipo String.
     * @return Info salva na memoria.
     *
     * Created by Rafael Magni 20/02/2019
     */
    private String getSharedPreferences(String tag) {
        SharedPreferences prefs = getSharedPreferences(PREFERENCES, 0);
        String descricao = prefs.getString(tag, null);
        return descricao;
    }

    /**
     * Remove informação salva na memoria.
     *
     * @param tag facilitador de busca da informação.
     *
     * Created by Rafael Magni 20/02/2019
     */
    private void clearSharedPreferences(String tag){
        SharedPreferences preferences = getSharedPreferences(PREFERENCES, 0);
        preferences.edit().remove(tag).commit();
    }

    /**
     * Recebe o email e verifica se ele contem '@' ou '.'.
     *
     * @param email Recebe uma String email
     * @return true|false
     *
     * Created by Rafael Magni 20/02/2019
     */
    private boolean validateEmail(final String email) {
        if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return true;
        }
        return false;
    }
}
