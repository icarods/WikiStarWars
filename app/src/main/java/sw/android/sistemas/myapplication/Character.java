package sw.android.sistemas.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.Serializable;

import sw.android.sistemas.myapplication.models.People;
import sw.android.sistemas.myapplication.repository.RepositoryPeople;

public class Character extends AppCompatActivity {

    Toolbar toolbar;
    People character;
    public TextView height;
    public TextView mass;
    public TextView hair_color;
    public TextView skin_color;
    public TextView eye_color;
    public TextView birth_year;
    public TextView gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character);

        Bundle extras = getIntent().getExtras();


        if(extras.containsKey("key") == true){
            character = (People) extras.get("key");
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar.setTitle(character.getName());
            setSupportActionBar(toolbar);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
            });
        }else{
            character = (People) extras.get("favorites");
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar.setTitle(character.getName());
            setSupportActionBar(toolbar);

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), Favoritos.class);
                    intent.putExtra("key", (Serializable)  new RepositoryPeople(getApplicationContext()).listAll());
                    startActivity(intent);
                }
            });
        }

        height = (TextView) findViewById(R.id.height);
        mass  = (TextView) findViewById(R.id.mass);
        hair_color = (TextView) findViewById(R.id.hair_color);
        skin_color = (TextView) findViewById(R.id.skin_color);
        eye_color = (TextView) findViewById(R.id.eye_color);
        birth_year = (TextView) findViewById(R.id.birth_year);
        gender = (TextView) findViewById(R.id.gender);

        height.setText("ALTURA: " + character.getHeight());
        mass.setText("PESO: " + character.getMass());
        hair_color.setText("COR DO CABELO: " + character.getHair_color());
        skin_color.setText("COR DA PELE: " + character.getSkin_color());
        eye_color.setText("COR DOS OLHOS: " + character.getEye_color());
        birth_year.setText("ANO DE NASCIMENTO: " + character.getBirth_year());
        gender.setText("GÊNERO: " + character.getGender());
    }

}
