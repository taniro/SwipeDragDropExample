package br.ufrn.eaj.tads.recycleviewexample;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Taniro on 17/09/2017.
 */

public class FrutaAdapter extends RecyclerView.Adapter {

    Context context;
    List<Fruta> listaFrutas;

    public FrutaAdapter(Context c, List<Fruta> f){
        this.context = c;
        this.listaFrutas = f;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //View view = LayoutInflater.from(context).inflate(R.layout.fruta_inflater, parent, false);

        View view = LayoutInflater.from(context).inflate(R.layout.fruta_card_inflater, parent, false);

        FrutaViewHolder holder = new FrutaViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        FrutaViewHolder frutaholder = (FrutaViewHolder) holder;
        Fruta frutaescolhida = listaFrutas.get(position);
        frutaholder.textViewNome.setText(frutaescolhida.getNome());

        if (frutaescolhida.getBitten()){
            frutaholder.img.setImageResource(R.drawable.bitten);
        }else{
            frutaholder.img.setImageResource(R.drawable.fruit);
        }

        frutaholder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listaFrutas.get(position).setBitten(true);
                notifyItemChanged(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaFrutas == null ? 0 : listaFrutas.size();
    }

    public class FrutaViewHolder extends RecyclerView.ViewHolder {

        final TextView textViewNome;
        final ImageView img;
        public FrutaViewHolder(View v) {
            super(v);
            textViewNome = v.findViewById(R.id.nomeFruta);
            img = v.findViewById(R.id.imgFruta);

            /*
            Esses onClicks funcionam mas não podemos saber qual item do adapter foi clicado.

            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "Clique Imagem", Toast.LENGTH_SHORT).show();
                }
            });
            
            textViewNome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context, "Clique Texto", Toast.LENGTH_SHORT).show();
                }
            });
            */
        }
    }

}
