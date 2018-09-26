package br.ufrn.eaj.tads.recycleviewexample;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
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

    private List<Fruta> frutaArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RecyclerView recyclerView = findViewById(R.id.recyclerview);

        carregaFrutas();

        final FrutaAdapter frutaAdapter = new FrutaAdapter(this, frutaArrayList);
        recyclerView.setAdapter(frutaAdapter);

        /*Animação do adaptador, mais informações em  https://github.com/wasabeef/recyclerview-animators
        * Ao usar comente a linha "recyclerView.setAdapter(frutaAdapter)";
        * */
        //recyclerView.setAdapter(new SlideInLeftAnimationAdapter(frutaAdapter));


        /* Definição do Layout que o recycler view usará */
        RecyclerView.LayoutManager layout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        /*
        Exemplos dos outros layouts possíveis
        */
        //RecyclerView.LayoutManager layout = new GridLayoutManager(this, 5);
        //RecyclerView.LayoutManager layout = new StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.VERTICAL);
        //RecyclerView.LayoutManager layout = new StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.HORIZONTAL);

        recyclerView.setLayoutManager(layout);

        recyclerView.setItemAnimator(new DefaultItemAnimator());

        /*
        Exemplos da biblioteca https://github.com/wasabeef/recyclerview-animators
         */
        //recyclerView.setItemAnimator(new FadeInDownAnimator());
        //recyclerView.setItemAnimator(new FlipInTopXAnimator());
        //recyclerView.setItemAnimator(new OvershootInLeftAnimator());
        //recyclerView.setItemAnimator(new LandingAnimator());

        /*
        //================== IMPLEMENTAÇÂO DO CLIQUE 1 ===========================

         Esse código não funciona



        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Não funciona", Toast.LENGTH_SHORT).show();
            }
        });

        /*
        //================== IMPLEMENTAÇÂO DO CLIQUE 2 ===========================

        recyclerView.addOnItemTouchListener(new MeuRecyclerViewClickListener(MainActivity.this, recyclerView, new MeuRecyclerViewClickListener.OnItemClickListener() {

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
        */


        //EXEMPLO 1 PARA AULA

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;

                Log.i("AULA17", "Drag flags: " + Integer.toBinaryString(dragFlags) + "Swipe flags: " + Integer.toBinaryString(swipeFlags)); //11 e 110000
                return makeMovementFlags(dragFlags, swipeFlags);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder dragged, @NonNull RecyclerView.ViewHolder target) {
                Log.i("AULA17", "OnMove invocado. Mover da posição " + dragged.getAdapterPosition() + " para " + target.getAdapterPosition());
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Log.i("AULA17", "OnSwipe invocado. Direção: " + Integer.toBinaryString(direction));

            }
        });

        itemTouchHelper.attachToRecyclerView(recyclerView);
        


        /*
        //EXEMPLO 2 PARA AULA

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, 0) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder dragged, @NonNull RecyclerView.ViewHolder target) {
                Log.i("AULA17", "OnMove invocado. Mover da posição " + dragged.getAdapterPosition() + " para " + target.getAdapterPosition());
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            }
        });

        itemTouchHelper.attachToRecyclerView(recyclerView);
        */


        /*
        //EXEMPLO 3 PARA AULA

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.START | ItemTouchHelper.END) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                //é usado para operações drag and drop
                int fromPosition = viewHolder.getAdapterPosition();
                int toPosition = target.getAdapterPosition();

                FrutaAdapter adapter = (FrutaAdapter) recyclerView.getAdapter();

                adapter.mover(fromPosition, toPosition);
                return true;// true se moveu, se não moveu, retorne falso
            }

            @Override
            public boolean isLongPressDragEnabled() {
                //return false; se quiser, é possivel desabilitar o drag and drop
                return true;
            }

            @Override
            public boolean isItemViewSwipeEnabled() {
                //return false; se quiser, é possivel desabilitar o swipe
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                //usado para implementar o swipe
                int posicao = viewHolder.getAdapterPosition();
                FrutaAdapter adapter = (FrutaAdapter) recyclerView.getAdapter();
                adapter.removerComTempo(posicao);
                //adapter.remover(posicao);
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                View itemView = viewHolder.itemView;
                Drawable background = new ColorDrawable(Color.RED);

                // not sure why, but this method get's called for viewholder that are already swiped away
                if (viewHolder.getAdapterPosition() == -1) {
                    // not interested in those
                    return;
                }

                Log.i("AULA17", "dx = " + dX);
                // Here, if dX > 0 then swiping right.
                // If dX < 0 then swiping left.
                // If dX == 0 then at at start position.

                // draw red background

                if(dX < 0) {
                    Log.i("AULA17", "dX < 0");
                    background.setBounds(itemView.getRight() + (int) dX, itemView.getTop(), itemView.getRight(), itemView.getBottom());
                }else if(dX > 0){
                    Log.i("AULA17", "dX > 0");
                    background.setBounds(itemView.getLeft(), itemView.getTop(), itemView.getRight()  + (int) dX, itemView.getBottom());
                }
                background.draw(c);

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }

        });

        itemTouchHelper.attachToRecyclerView(recyclerView);
        */


    }

    public void carregaFrutas() {

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


        for (int i = 0; i < 50000; i++) {
            frutaArrayList.add(new Fruta("Fruta " + i, R.drawable.fruit));
        }


    }
}
