package com.br.list.list.list.git.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.br.list.list.list.git.R;
import com.br.list.list.list.git.model.WorldPopulation;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Rafael Magni 20/02/2019
 */
public class ListViewAdapter  extends BaseAdapter {

    // Declare Variables
    Context mContext;
    LayoutInflater inflater;
    private ArrayList<WorldPopulation> worldpopulationlist = null;
    private ArrayList<WorldPopulation> arraylist;

    public ListViewAdapter(Context context, ArrayList<WorldPopulation> worldpopulationlist) {
        //Popula com os valores recebido
        mContext = context;
        this.worldpopulationlist = worldpopulationlist;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<WorldPopulation>();
        this.arraylist.addAll(worldpopulationlist);
    }

    public class ViewHolder {
        TextView nome;
        TextView desc;
        TextView url;
    }

    /**
     * @return Retorna o tamanho
     */
    @Override
    public int getCount() {
        return worldpopulationlist.size();
    }

    /**
     * @param position Recebe a posicao do item
     * @return Retorna o item
     */
    @Override
    public WorldPopulation getItem(int position) {
        return worldpopulationlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.list_item, null);

            holder.nome = (TextView) view.findViewById(R.id.tvNome);
            holder.desc = (TextView) view.findViewById(R.id.tvDesc);
            holder.url = (TextView) view.findViewById(R.id.tvURL);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Mostra informação
        holder.nome.setText(worldpopulationlist.get(position).getNome());
        holder.desc.setText(worldpopulationlist.get(position).getDescricao());
        holder.url.setText(worldpopulationlist.get(position).getUrl());

        return view;
    }

    // Classe responsavel pelo filtro
    public void filter(String charText) {
        //Passa o texto para minusculo
        charText = charText.toLowerCase(Locale.getDefault());

        //Limpa ArrayList
        worldpopulationlist.clear();

        if (charText.length() == 0) {

            //Grava valor no ArrayList
            worldpopulationlist.addAll(arraylist);
        }
        else
        {
            for (WorldPopulation wp : arraylist)
            {
                if (wp.getNome().toLowerCase(Locale.getDefault()).contains(charText))
                {
                    //Grava as linhas a ser mostrada no ListView
                    worldpopulationlist.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

}