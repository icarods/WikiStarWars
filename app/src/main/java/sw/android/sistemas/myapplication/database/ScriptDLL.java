package sw.android.sistemas.myapplication.database;

public class ScriptDLL {

    public static final String nameDataBase = "BANCO_PEOPLE_SWAPI";

    public static String getCreateTablePeople(){

        StringBuilder sql = new StringBuilder();

        sql.append(" CREATE TABLE IF NOT EXISTS " + nameDataBase + " ( ");
        sql.append("    ID         INTEGER       PRIMARY KEY AUTOINCREMENT NOT NULL, ");
        sql.append("    NOME       VARCHAR (250) NOT NULL DEFAULT (''), ");
        sql.append("    ALTURA     VARCHAR (250) NOT NULL DEFAULT (''), ");
        sql.append("    PESO       VARCHAR (250) NOT NULL DEFAULT (''), ");
        sql.append("    CABELO     VARCHAR (250) NOT NULL DEFAULT (''), ");
        sql.append("    PELE       VARCHAR (250) NOT NULL DEFAULT (''), ");
        sql.append("    OLHOS      VARCHAR (250) NOT NULL DEFAULT (''), ");
        sql.append("    NASCIMENTO VARCHAR (250) NOT NULL DEFAULT (''), ");
        sql.append("    GENERO     VARCHAR (250) NOT NULL DEFAULT ('')  ");
        sql.append(" ); ");

        return sql.toString();
    }
}
