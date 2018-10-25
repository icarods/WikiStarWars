package sw.android.sistemas.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import sw.android.sistemas.myapplication.database.Connection;
import sw.android.sistemas.myapplication.models.People;
import sw.android.sistemas.myapplication.repository.RepositoryPeople;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>
        implements Filterable{

    List<People> people;
    List<People> peopleFilter;
    List<People> fav;

    Context context;

    public MyAdapter(List<People> people, Context context) {
        this.people = people;
        this.peopleFilter = people;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView height;
        public TextView gender;
        public TextView mass;
        public CheckBox selectionState;

        public MyViewHolder(final View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            height = (TextView) itemView.findViewById(R.id.height);
            gender = (TextView) itemView.findViewById(R.id.gender);
            mass = (TextView) itemView.findViewById(R.id.mass);
            selectionState = (CheckBox) itemView.findViewById(R.id.brand_select);

            fav = listFavorites();

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if(position != RecyclerView.NO_POSITION){
                        Intent intent = new Intent(context, sw.android.sistemas.myapplication.Character.class);
                        intent.putExtra("key", people.get(position));
                        context.startActivity(intent);
                    }
                }
            });

            selectionState.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    RepositoryPeople repositoryPeople = new RepositoryPeople(context);

                    if(isChecked && repositoryPeople.search(people.get(getAdapterPosition())) == false){
                        repositoryPeople.insert(people.get(getAdapterPosition()));
                        Toast.makeText(context, people.get(getAdapterPosition()).getName() +
                                " adicionado aos favoritos.", Toast.LENGTH_LONG).show();
                    }
                    if(!isChecked && repositoryPeople.search(people.get(getAdapterPosition())) == true){
                        repositoryPeople.delete(people.get(getAdapterPosition()));
                        Toast.makeText(context, people.get(getAdapterPosition()).getName() +
                                " deletado dos favoritos.", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.layout_listitem, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.name.setText("Nome: " + people.get(position).getName());
        holder.mass.setText("Peso: " + people.get(position).getMass());
        holder.gender.setText("Gênero: " + people.get(position).getGender());
        holder.height.setText("Altura: " + people.get(position).getHeight());

        if(new RepositoryPeople(context).search(people.get(position)) == true)
            holder.selectionState.setChecked(true);
        else
            holder.selectionState.setChecked(false);
    }

    @Override
    public int getItemCount() {
        return people.size();
    }

    public List<People> listFavorites(){
        return new RepositoryPeople(context).listAll();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();

                if (charString.isEmpty()) {
                    people = peopleFilter;
                } else {
                    List<People> filteredList = new ArrayList<>();
                    for (People row : peopleFilter) {
                        if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    people = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = people;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                people = (ArrayList<People>) filterResults.values;

                notifyDataSetChanged();
            }
        };
    }


}
