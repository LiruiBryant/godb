package com.earyant.database;

import com.earyant.database.database.service.impl.CreateDataBaseServiceImpl;
import com.earyant.database.explain.CmdType;
import com.earyant.database.explain.CreateDbScheme;
import com.earyant.database.explain.CreateTableScheme;
import com.earyant.database.explain.service.impl.ExplainServiceImpl;

import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        String dbName = "db";

        String cmd = "CREATE DATABASE " + dbName;
        runShell(dbName, cmd);
        cmd = "CREATE TABLE `feature_conf` (\n          `id` varchar(255) DEFAULT NULL,\n          PRIMARY KEY (`id`)\n)";
        runShell(dbName, cmd);
    }

    private static void runShell(String dbName, String cmd) {
        ExplainServiceImpl explainService = new ExplainServiceImpl();
        CreateDataBaseServiceImpl createDataBaseService = new CreateDataBaseServiceImpl();
        Integer type = explainService.explainType(cmd);
        if (Objects.equals(type, CmdType.createTable.getValue())) {
            // 建表
            CreateTableScheme createTableScheme = explainService.explainCreateTable(dbName, cmd);
            createDataBaseService.createTable(createTableScheme);
        } else if (Objects.equals(type, CmdType.createDb.getValue())) {
            //建库
            CreateDbScheme createDbScheme = explainService.explainCreateDb(cmd);
            createDataBaseService.createDb(createDbScheme);
        }
    }
}