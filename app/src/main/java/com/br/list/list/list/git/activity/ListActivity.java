package com.br.list.list.list.git.activity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.br.list.list.list.git.R;
import com.br.list.list.list.git.adapter.ListViewAdapter;
import com.br.list.list.list.git.model.HttpsHandler;
import com.br.list.list.list.git.model.WorldPopulation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Rafael Magni 20/02/2019
 */
public class ListActivity extends AppCompatActivity {

    ArrayList<WorldPopulation> listGit;

    ListViewAdapter adapter;

    private ListView lv;

    private EditText busca;

    private Context cx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSystemService(Context.INPUT_METHOD_SERVICE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        cx = ListActivity.this;

        listGit = new ArrayList<>();
        lv = findViewById(R.id.list);
        busca = findViewById(R.id.edtSearch);

        new GetContacts().execute();

        busca.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
                //Verifica se texto informado e maior que 3
                if (busca.length() >= 3){
                    String text = busca.getText().toString().toLowerCase(Locale.getDefault());
                    //Passa texto para buscar
                    adapter.filter(text);

                }else if (busca.length() == 0){
                    adapter.filter("");
                }
            }
        });
    }

    private class GetContacts extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected Void doInBackground(Void... arg0) {
            HttpsHandler sh = new HttpsHandler();
            String api = "https://api.github.com/repositories";
            String jsonStr = sh.makeServiceCall(api);
            Log.i("TAG", jsonStr);
            if (jsonStr != null) {
                try {

                    // Obtem resposta JSON Array
                    JSONArray repositorio = new JSONArray(String.valueOf(jsonStr));

                    // loop através de todos os repositorios
                    for (int i = 0; i < repositorio.length(); i++) {
                        JSONObject c = repositorio.getJSONObject(i);

                        //Pega as info do JSon
                        String nome = c.getString("name");
                        String desc = c.getString("description");
                        String url = c.getString("url");

                        WorldPopulation wp = new WorldPopulation();

                        wp.setNome(nome.trim());
                        wp.setDescricao(desc.trim());
                        wp.setUrl(url.trim());

                        // adicionando contato à lista de contatos
                        listGit.add(wp);
                    }
                } catch (final JSONException e) {
                    ListActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //Mostra erro
                            Toast.makeText(ListActivity.this,
                                    "Erro de análise Json: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                }

            } else {
                ListActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Mostra erro
                        Toast.makeText(ListActivity.this,
                                "Não foi possível obter o json do servidor. Verifique o LogCat para possíveis erros!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            //Passar resultados para a classe ListViewAdapter
            adapter = new ListViewAdapter(cx, listGit);

            // Vincula o adaptador ao ListView
            lv.setAdapter(adapter);
        }
    }

}
