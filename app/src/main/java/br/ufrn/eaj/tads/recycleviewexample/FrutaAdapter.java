package br.ufrn.eaj.tads.recycleviewexample;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.text.LoginFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Taniro on 17/09/2017.
 */

public class FrutaAdapter extends RecyclerView.Adapter {

    Context context;
    List<Fruta> listaFrutas;

    private static final int PENDING_REMOVAL_TIMEOUT = 3000; // 3sec
    List<Fruta> itemsPendingRemoval;

    private Handler handler = new Handler(); // hanlder que vai guardar os runnables que devem ser executados
    HashMap<Fruta, Runnable> pendingRunnables = new HashMap<>(); // map de frutas com runnables pendentes, para que seja possível cancelar

    public FrutaAdapter(Context c, List<Fruta> f){
        this.context = c;
        this.listaFrutas = f;
        this.itemsPendingRemoval = new ArrayList<>();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //View view = LayoutInflater.from(context).inflate(R.layout.fruta_inflater, parent, false);

        //View view = LayoutInflater.from(context).inflate(R.layout.fruta_card_inflater, parent, false);
        View view = LayoutInflater.from(context).inflate(R.layout.fruta_inflater_novo, parent, false);

        FrutaViewHolder holder = new FrutaViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        FrutaViewHolder frutaholder = (FrutaViewHolder) holder;
        final Fruta frutaescolhida = listaFrutas.get(position);
        frutaholder.textViewNome.setText(frutaescolhida.getNome());

        if (itemsPendingRemoval.contains(frutaescolhida)) {
            //view do swipe/delete
            frutaholder.layoutNormal.setVisibility(View.GONE);
            frutaholder.layoutGone.setVisibility(View.VISIBLE);
            frutaholder.undoButton.setVisibility(View.VISIBLE);
            frutaholder.undoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // usou o undo, remover a pendingRennable
                    Runnable pendingRemovalRunnable = pendingRunnables.get(frutaescolhida);
                    pendingRunnables.remove(frutaescolhida);
                    if (pendingRemovalRunnable != null){
                        handler.removeCallbacks(pendingRemovalRunnable);
                    }
                    itemsPendingRemoval.remove(frutaescolhida);
                    //binda novamente para redesenhar
                    notifyItemChanged(listaFrutas.indexOf(frutaescolhida));
                }
            });
        }
        else {
            //mostra o padrão
            frutaholder.textViewNome.setText(frutaescolhida.getNome());
            frutaholder.layoutNormal.setVisibility(View.VISIBLE);
            frutaholder.layoutGone.setVisibility(View.GONE);
            frutaholder.undoButton.setVisibility(View.GONE);
            frutaholder.undoButton.setOnClickListener(null);
            if (frutaescolhida.getBitten()){
                frutaholder.img.setImageResource(R.drawable.bitten);
            }else{
                frutaholder.img.setImageResource(R.drawable.fruit);
            }

            frutaholder.img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    frutaescolhida.setBitten(true);
                    notifyItemChanged(position);
                }
            });
        }

    }


    @Override
    public int getItemCount() {
        return listaFrutas == null ? 0 : listaFrutas.size();
    }


    public void removerComTempo(int position) {
        final Fruta fruta = listaFrutas.get(position);
        if (!itemsPendingRemoval.contains(fruta)) {
            itemsPendingRemoval.add(fruta);
            notifyItemChanged(position);
            Runnable pendingRemovalRunnable = new Runnable() {
                @Override
                public void run() {
                    remover(listaFrutas.indexOf(fruta));
                }
            };
            handler.postDelayed(pendingRemovalRunnable, PENDING_REMOVAL_TIMEOUT);
            pendingRunnables.put(fruta, pendingRemovalRunnable);
        }
    }

    public void remover(int position) {
        Fruta fruta = listaFrutas.get(position);
        if (itemsPendingRemoval.contains(fruta)) {
            itemsPendingRemoval.remove(fruta);
        }
        if (listaFrutas.contains(fruta)) {
            listaFrutas.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void mover(int fromPosition, int toPosition){


        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(listaFrutas, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(listaFrutas, i, i - 1);
            }
        }


        notifyItemMoved(fromPosition, toPosition);
        notifyItemChanged(toPosition);
        notifyItemChanged(fromPosition);
    }


    public class FrutaViewHolder extends RecyclerView.ViewHolder {

        final TextView textViewNome;
        final ImageView img;
        final LinearLayout layoutNormal;
        final LinearLayout layoutGone;
        final Button undoButton;

        public FrutaViewHolder(View v) {
            super(v);
            textViewNome = v.findViewById(R.id.nomeFruta);
            img = v.findViewById(R.id.imgFruta);
            layoutNormal = v.findViewById(R.id.layout_normal);
            layoutGone = v.findViewById(R.id.layout_gone);
            undoButton = v.findViewById(R.id.undo_button);

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
