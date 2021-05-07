package com.example.modelproject.data;

public class ScriptDDL {

    //Usuario
    public static String getCreateTableUsuario() {
        StringBuilder sb = new StringBuilder();

        sb.append("CREATE TABLE IF NOT EXISTS [usuario] (\n" +
                " [codigo] VARCHAR (6) NOT NULL, \n" +
                " [usuario] VARCHAR (25) NOT NULL, \n" +
                " [nome] VARCHAR (40) NOT NULL, \n" +
                " [senha] VARCHAR (25) NOT NULL, \n" +
                " [email] VARCHAR (100), \n" +
                " [codpro] VARCHAR (6) NOT NULL, \n" +
                " [codsub] VARCHAR (6) NOT NULL, \n" +
                " [ativo] VARCHAR (1) NOT NULL, \n" +
                " CONSTRAINT [] PRIMARY KEY ([codigo], [codpro], [codsub]));");

        return sb.toString();
    }

    //Configuracao
    public static String getCreateTableConfiguracao() {
        StringBuilder sb = new StringBuilder();

        sb.append("CREATE TABLE IF NOT EXISTS [configuracao] (\n" +
                " [id] INT NOT NULL, \n" +
                " [url] VARCHAR (100) NOT NULL, \n" +
                " CONSTRAINT [] PRIMARY KEY ([id]));");

        return sb.toString();
    }

    public static String getCreateTableCompetencia() {
        StringBuilder sb = new StringBuilder();

        sb.append("CREATE TABLE IF NOT EXISTS [competencias] (\n" +
                " [compet] VARCHAR (8) NOT NULL, \n" +
                " [nivcom] VARCHAR (50) NOT NULL, \n" +
                " CONSTRAINT [] PRIMARY KEY ([compet]));");

        return sb.toString();
    }


    public static String getCreateTableFilial() {
        StringBuilder sb = new StringBuilder();

        sb.append("CREATE TABLE IF NOT EXISTS [filiais] (\n" +
                " [filial] VARCHAR (6) NOT NULL, \n" +
                " [fazenda] VARCHAR (30) NOT NULL, \n" +
                " CONSTRAINT [] PRIMARY KEY ([filial]));");

        return sb.toString();
    }

    public static String getCreateTableUGB() {
        StringBuilder sb = new StringBuilder();

        sb.append("CREATE TABLE IF NOT EXISTS [ugbs] (\n" +
                " [filial] VARCHAR (6) NOT NULL, \n" +
                " [codloc] VARCHAR (6) NOT NULL, \n" +
                " [codger] VARCHAR (6) NOT NULL, \n" +
                " [codcoo] VARCHAR (6) NOT NULL, \n" +
                " [codugb] VARCHAR (7) NOT NULL, \n" +
                " [desugb] VARCHAR (60) NOT NULL, \n" +
                " [notasm] INT NULL, \n" +
                " [nota5s] INT NULL, \n" +
                " [avlmat] INT NULL, \n" +
                " CONSTRAINT [] PRIMARY KEY ([filial],[codugb]));");

        return sb.toString();
    }

    public static String getCreateTableColaborador() {
        StringBuilder sb = new StringBuilder();

        sb.append("CREATE TABLE IF NOT EXISTS [colaboradores] (\n" +
                " [codzra] VARCHAR (7) NOT NULL, \n" +
                " [nome] VARCHAR (30) NOT NULL, \n" +
                " [codugb] VARCHAR, \n" +
                " CONSTRAINT [] PRIMARY KEY ([codzra]));");

        return sb.toString();
    }
}


