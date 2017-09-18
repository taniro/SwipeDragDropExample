package br.ufrn.eaj.tads.recycleviewexample;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInLeftAnimationAdapter;
import jp.wasabeef.recyclerview.animators.FadeInDownAnimator;
import jp.wasabeef.recyclerview.animators.FlipInRightYAnimator;
import jp.wasabeef.recyclerview.animators.FlipInTopXAnimator;
import jp.wasabeef.recyclerview.animators.LandingAnimator;
import jp.wasabeef.recyclerview.animators.OvershootInLeftAnimator;

public class MainActivity extends AppCompatActivity {

    private List<Fruta> frutaArrayList =  new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        carregaFrutas();

        FrutaAdapter  frutaAdapter = new FrutaAdapter(this, frutaArrayList);
        recyclerView.setAdapter(frutaAdapter);

        /*Animação do adaptador, mais informações em  https://github.com/wasabeef/recyclerview-animators
        * Ao usar comente a linha "recyclerView.setAdapter(frutaAdapter)";
        * */
        //recyclerView.setAdapter(new SlideInLeftAnimationAdapter(frutaAdapter));


        RecyclerView.LayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        /*
        Exemplos dos outros layouts possíveis
        */
        //RecyclerView.LayoutManager layout = new GridLayoutManager(this, 5);
        //RecyclerView.LayoutManager layout = new StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.VERTICAL);
        //RecyclerView.LayoutManager layout = new StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.HORIZONTAL);

        recyclerView.setLayoutManager(layout);


        /*
         Esse código não funciona

        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Não funciona", Toast.LENGTH_SHORT).show();
            }
        });
         */

        recyclerView.addOnItemTouchListener(new MeuRecyclerViewClickListener(MainActivity.this, recyclerView, new MeuRecyclerViewClickListener.OnItemClickListener(){

            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(MainActivity.this, "Clique simples", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, final int position) {

                final Fruta removida = frutaArrayList.get(position);

                frutaArrayList.remove(position);
                recyclerView.getAdapter().notifyItemRemoved(position);
                Toast.makeText(MainActivity.this, "Clique longo", Toast.LENGTH_SHORT).show();

                Snackbar snack = Snackbar.make((View)recyclerView.getParent(),"Removido", Snackbar.LENGTH_LONG)
                        .setAction("Cancelar", new View.OnClickListener(){
                            @Override
                            public void onClick(View view) {
                                frutaArrayList.add(position, removida);
                                recyclerView.getAdapter().notifyItemInserted(position);
                            }
                        });
                snack.show();
            }
        }));

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        /*
        Exemplos da biblioteca https://github.com/wasabeef/recyclerview-animators
         */
        //recyclerView.setItemAnimator(new FadeInDownAnimator());
        //recyclerView.setItemAnimator(new FlipInTopXAnimator());
        //recyclerView.setItemAnimator(new OvershootInLeftAnimator());
        //recyclerView.setItemAnimator(new LandingAnimator());

    }

    public void carregaFrutas(){

        frutaArrayList.add((new Fruta("Laranja", R.drawable.fruit)));
        frutaArrayList.add((new Fruta("Maca", R.drawable.fruit)));
        frutaArrayList.add((new Fruta("Pera", R.drawable.fruit)));
        frutaArrayList.add((new Fruta("Uva", R.drawable.fruit)));
        frutaArrayList.add((new Fruta("Goiaba", R.drawable.fruit)));
        frutaArrayList.add((new Fruta("Melao", R.drawable.fruit)));
        frutaArrayList.add((new Fruta("Limao", R.drawable.fruit)));
        frutaArrayList.add((new Fruta("Graviola", R.drawable.fruit)));
        frutaArrayList.add((new Fruta("Açaí", R.drawable.fruit)));
        frutaArrayList.add((new Fruta("Tomate", R.drawable.fruit)));
        frutaArrayList.add((new Fruta("Jaboticaba", R.drawable.fruit)));
        frutaArrayList.add((new Fruta("Acerola", R.drawable.fruit)));
        frutaArrayList.add((new Fruta("Manga", R.drawable.fruit)));
        frutaArrayList.add((new Fruta("Kiwi", R.drawable.fruit)));
        frutaArrayList.add((new Fruta("Morango", R.drawable.fruit)));

        for (int i = 0; i<50000; i++ ){
            frutaArrayList.add(new Fruta("Fruta "+i, R.drawable.fruit));
        }

    }
}
