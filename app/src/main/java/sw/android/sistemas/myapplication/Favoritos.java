package sw.android.sistemas.myapplication;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import sw.android.sistemas.myapplication.models.People;
import sw.android.sistemas.myapplication.repository.RepositoryPeople;

public class Favoritos extends AppCompatActivity {

    private List<People> favorites;

    private RecyclerView mRecyclerView;
    private AdapterFavorites mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        favorites = (List<People>) getIntent().getSerializableExtra("key");

        if (favorites.isEmpty()) {

            setContentView(R.layout.favorites_empty);

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar.setTitle("Meus Favoritos");
            setSupportActionBar(toolbar);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
            });

        } else {

            setContentView(R.layout.activity_favoritos);

            Collections.sort(favorites, new Comparator<People>() {
                @Override
                public int compare(People o1, People o2) {
                    return o1.getName().compareTo(o2.getName()) < 0 ? -1 :
                            (o1.getName().compareTo(o2.getName()) > 0 ? +1 : 0);
                }
            });

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar.setTitle("Meus Favoritos");
            setSupportActionBar(toolbar);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
            });

            Log.d("ACTIVITY-FAV", favorites.get(0).getName());

            mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view_fav);
            mRecyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mAdapter = new AdapterFavorites(this, favorites);
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        favorites = new RepositoryPeople(this).listAll();
        Intent intent = new Intent(this, Favoritos.class);
        intent.putExtra("key", (Serializable) favorites);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_favorites, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.search_favorites)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(favorites.size() != 0) mAdapter.getFilter().filter(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}
