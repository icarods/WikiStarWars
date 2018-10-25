package sw.android.sistemas.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import sw.android.sistemas.myapplication.models.People;
import sw.android.sistemas.myapplication.repository.RepositoryPeople;

public class AdapterFavorites extends RecyclerView.Adapter<AdapterFavorites.MyViewHolder>
                                implements Filterable {

    private List<People> favorites;
    private List<People> favoritesFilter;
    private Context context;

    public AdapterFavorites(Context context, List<People> favorites){
        this.context = context;
        this.favorites = favorites;
        this.favoritesFilter = favorites;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView name;
        public TextView height;
        public TextView gender;
        public TextView mass;
        private Button button_trash;
        public TextView empty;

        public MyViewHolder(final View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            height = (TextView) itemView.findViewById(R.id.height);
            gender = (TextView) itemView.findViewById(R.id.gender);
            mass = (TextView) itemView.findViewById(R.id.mass);
            button_trash = (Button) itemView.findViewById(R.id.trash);
            empty = (TextView) itemView.findViewById(R.id.empty);

            button_trash.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    final AlertDialog.Builder builder = new AlertDialog.Builder(context);

                    builder.setMessage("Tem certeza que quer deletar " + favorites.get(getAdapterPosition()).getName() + " dos favoritos?");

                    builder.setPositiveButton("SIM!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                             final RepositoryPeople repositoryPeople = new RepositoryPeople(context);
                             People people = favorites.get(getAdapterPosition());
                             repositoryPeople.delete(favorites.get(getAdapterPosition()));
                             favorites.remove(people);
                             notifyDataSetChanged();

                             if(favorites.size() == 0){
                                 final AlertDialog.Builder builderEmpty = new AlertDialog.Builder(context);
                                 builderEmpty.setMessage("Lista de favoritos vazia.");
                                 builderEmpty.setPositiveButton("Ok!", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        context.startActivity(new Intent(context, MainActivity.class));
                                    }
                                 });
                                 AlertDialog dialogOK = builderEmpty.create();
                                 dialogOK.show();
                             }

                        }

                    });

                    builder.setNegativeButton("NÃO!", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if(position != RecyclerView.NO_POSITION){
                        Intent intent = new Intent(context, sw.android.sistemas.myapplication.Character.class);
                        intent.putExtra("favorites", favorites.get(position));
                        context.startActivity(intent);
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.content_favoritos, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.name.setText("Nome: " + favorites.get(position).getName());
        holder.mass.setText("Peso: " + favorites.get(position).getMass());
        holder.gender.setText("Gênero: " + favorites.get(position).getGender());
        holder.height.setText("Altura: " + favorites.get(position).getHeight());

    }

    @Override
    public int getItemCount() {
        return favorites.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {
                    favorites = favoritesFilter;
                } else {
                    List<People> filteredList = new ArrayList<>();
                    for (People row : favoritesFilter) {
                        if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    favorites = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = favorites;
                return filterResults;

            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults filterResults) {
                favorites = (ArrayList<People>) filterResults.values;

                notifyDataSetChanged();

            }
        };
    }

}
