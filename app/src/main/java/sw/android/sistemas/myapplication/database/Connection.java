package sw.android.sistemas.myapplication.database;

import android.app.AlertDialog;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class Connection {

    SQLiteDatabase conexao;
    DadosOpenHelper dadosOpenHelper;

    public SQLiteDatabase createConnection(Context context){
        try {

            dadosOpenHelper = new DadosOpenHelper(context);
            conexao = dadosOpenHelper.getWritableDatabase();

            Log.d("BANCO-DAD0S-OK", String.valueOf(conexao));

        }catch (SQLException e){

            AlertDialog.Builder dlg = new AlertDialog.Builder(context);
            dlg.setTitle("ERRO AO CONECTAR DATABASE!");
            dlg.setMessage(e.getMessage());
            dlg.setNeutralButton("OK", null);

            Log.d("BANCO-DADOS-ERRO", String.valueOf(e));

        }
        return conexao;
    }

}
