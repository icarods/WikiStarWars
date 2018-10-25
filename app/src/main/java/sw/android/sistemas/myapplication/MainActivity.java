package sw.android.sistemas.myapplication;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.SearchView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sw.android.sistemas.myapplication.models.People;
import sw.android.sistemas.myapplication.models.SWAPIList;
import sw.android.sistemas.myapplication.database.Connection;
import sw.android.sistemas.myapplication.repository.RepositoryPeople;

public class MainActivity extends AppCompatActivity {

    Toolbar myToolbar;
    ActionBar bar;
    SearchView searchView;

    List<People> pp = new ArrayList<>();

    private List<People> favorites = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        bar = getSupportActionBar();

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        for (int i = 1; i < 10; i++) {
            Call<SWAPIList<People>> call = new ConnectionRetrofit().connection().getAllPeople(i);

            call.enqueue(new Callback<SWAPIList<People>>() {
                @Override
                public void onResponse(Call<SWAPIList<People>> call, Response<SWAPIList<People>> response) {

                    SWAPIList<People> responsePeople = response.body();

                    if(responsePeople == null){
                        Log.d("SEXO", "NULL");
                    }

                    for (int k = 0; k < responsePeople.results.size(); k++) {
                        pp.add(responsePeople.getResults().get(k));
                    }

                    Collections.sort(pp, new Comparator<People>() {
                        @Override
                        public int compare(People o1, People o2) {
                            return o1.getName().compareTo(o2.getName()) < 0 ? -1 :
                                    (o1.getName().compareTo(o2.getName()) > 0 ? +1 : 0);
                        }
                    });

                    mAdapter = new MyAdapter(pp, MainActivity.this);
                    mRecyclerView.setAdapter(mAdapter);
                }

                @Override
                public void onFailure(Call<SWAPIList<People>> call, Throwable t) {

                }
            });
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.search_people)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));

       searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                mAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return false;
            }

        });

        return true;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        favorites = new RepositoryPeople(this).listAll();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, Favoritos.class);
            intent.putExtra("key", (Serializable) favorites);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
