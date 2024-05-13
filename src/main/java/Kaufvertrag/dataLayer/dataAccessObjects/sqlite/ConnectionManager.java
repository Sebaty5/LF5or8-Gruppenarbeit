package Kaufvertrag.dataLayer.dataAccessObjects.sqlite;

import Kaufvertrag.dataLayer.dataAccessObjects.sqlite.database.Field;
import Kaufvertrag.dataLayer.dataAccessObjects.sqlite.database.Table;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConnectionManager {
    private static final String dbUrl = "jdbc:sqlite:./database/database.db";
    private final int databaseErrorCode = 2;
    private ArrayList<String> tableNames = new ArrayList<>();

    public static final ConnectionManager INSTANCE = new ConnectionManager();


    private ConnectionManager() {
        createNewDatabase();

    }

    private void createNewDatabase() {
        try(Connection connection = DriverManager.getConnection(dbUrl)) {
            if (connection == null) {
                System.out.println("Connection to database could not be established.");
                System.exit(databaseErrorCode);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(databaseErrorCode);
        }
    }

    public boolean executeSQL(String sqlPreparedString, String[] values)
    {
        try(Connection connection = DriverManager.getConnection(dbUrl)) {
            PreparedStatement statement = connection.prepareStatement(sqlPreparedString);
            for (int i = 0; i < values.length; i++) {
                statement.setString(i + 1, values[i]);
            }
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(databaseErrorCode);
        }
        return false;
    }

    public List<Map<String, String>> executeQuerySQL(String sqlPreparedString, String[] values)
    {
        try(Connection connection = DriverManager.getConnection(dbUrl)) {
            PreparedStatement statement = connection.prepareStatement(sqlPreparedString);
            for (int i = 0; i < values.length; i++) {
                statement.setString(i + 1, values[i]);
            }
            ResultSet resultSet = statement.executeQuery();
            return resultSetToArrayList(resultSet);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(databaseErrorCode);
        }
        return null;
    }

    public List<Map<String, String>> resultSetToArrayList(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();
        List<Map<String, String>> list = new ArrayList<>();
        while (resultSet.next()) {
            Map<String, String> row = new HashMap<>(columnCount);
            for (int i = 1; i <= columnCount; ++i) {
                row.put(metaData.getColumnName(i), resultSet.getString(i));
            }
            list.add(row);
        }
        return list;
    }

    public void createNewTable(Table table) {
        StringBuilder sqlString = new StringBuilder("CREATE TABLE IF NOT EXISTS " + table.getTableName() + " (");
        int i = 0;
        for (Field field : table.getFields()) {
            if(i > 0)  sqlString.append(",");
            sqlString.append("\n").append(field.getName()).append(" ").append(field.getType().toString());
            if(field.getPrimary()) sqlString.append(" PRIMARY KEY");
            if(field.getNotNull()) sqlString.append(" NOT NULL");
            i++;
        }
        sqlString.append("\n);");
        if(executeSQL(sqlString.toString(), new String[]{})) tableNames.add(table.getTableName());
    }
}





