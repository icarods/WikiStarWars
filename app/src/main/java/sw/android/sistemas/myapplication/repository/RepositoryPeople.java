package sw.android.sistemas.myapplication.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.widget.LinearSmoothScroller;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import sw.android.sistemas.myapplication.database.Connection;
import sw.android.sistemas.myapplication.database.ScriptDLL;
import sw.android.sistemas.myapplication.models.People;

public class RepositoryPeople {

    private SQLiteDatabase connection;

    private int id;
    private String name;
    private String height;
    private String mass;
    private String hair_color;
    private String skin_color;
    private String eye_color;
    private String birth_year;
    private String gender;

    public RepositoryPeople (Context context){
        this.connection = new Connection().createConnection(context);
    }

    public void insert (People people){

        ContentValues values = new ContentValues();

        values.put("NOME", people.getName());
        values.put("ALTURA", people.getHeight());
        values.put("PESO", people.getMass());
        values.put("CABELO", people.getHair_color());
        values.put("PELE", people.getSkin_color());
        values.put("OLHOS", people.getEye_color());
        values.put("NASCIMENTO", people.getBirth_year());
        values.put("GENERO", people.getGender());

        Log.d("REPOSITORY-PEOPLE", people.getName());

        connection.insertOrThrow(ScriptDLL.nameDataBase, null, values);
    }

    public void delete (People people){

       String[] par = new String[1];
       par[0] = people.getName();

       connection.delete(ScriptDLL.nameDataBase, "NOME = ?", par);
    }

    public boolean search (People people){

        String[] par = new String[1];
        par[0] = people.getName();

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT * ");
        sql.append(" FROM " + ScriptDLL.nameDataBase);
        sql.append(" WHERE NOME = ? ");

        Cursor cursor = connection.rawQuery(sql.toString(),par);

        if(cursor.getCount() > 0){
            return true;
        }

        return false;
    }

    public List<People> listAll(){

        List<People> peoples = new ArrayList<>();

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT ID, NOME, ALTURA, PESO, CABELO, PELE, OLHOS, NASCIMENTO, GENERO ");
        sql.append(" FROM " + ScriptDLL.nameDataBase);

        Cursor cursor = connection.rawQuery(sql.toString(), null);

        if (cursor.getCount() > 0){
            cursor.moveToFirst();

            do {

                id = cursor.getInt(cursor.getColumnIndexOrThrow("ID"));
                name = cursor.getString(cursor.getColumnIndexOrThrow("NOME"));
                height = cursor.getString(cursor.getColumnIndexOrThrow("ALTURA"));
                mass = cursor.getString(cursor.getColumnIndexOrThrow("PESO"));
                hair_color = cursor.getString(cursor.getColumnIndexOrThrow("CABELO"));
                skin_color = cursor.getString(cursor.getColumnIndexOrThrow("PELE"));
                eye_color = cursor.getString(cursor.getColumnIndexOrThrow("OLHOS"));
                birth_year = cursor.getString(cursor.getColumnIndexOrThrow("NASCIMENTO"));
                gender = cursor.getString(cursor.getColumnIndexOrThrow("GENERO"));

                People people = new People(name, height, mass, hair_color, skin_color, eye_color, birth_year, gender,
                        null, null, null, null, null, null, null,
                        null);

                peoples.add(people);

            }while (cursor.moveToNext());
        }
        return peoples;
    }

}
