package com.eu.habbo.habbohotel.modtool;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.gameclients.GameClient;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class FilterSQL {
    public static Map<String, String> loadWordfilter() {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        Map<String, String> data = new HashMap<>();

        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection();
             Statement statement = connection.createStatement();
             ResultSet set = statement.executeQuery("SELECT `word`, `replacement` FROM `wordfilter`")) {
            while (set.next()) {
                data.put(resultSet.getString("word").toLowerCase(), resultSet.getString("replacement"));
                }
        } catch (SQLException e) {
            Emulator.getLogging().logSQLException(e);
        }
        return data;
    }

    /*public static void save(Map<String, String> wordfilter, GameClient client) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        Map.Entry<String, String> word;

        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("REPLACE INTO wordfilter (`word`, `replacement`) VALUES(?, ?)", Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, wordfilter.getKey());
                statement.setString(2, description);
                statement.setInt(3, roomId);
                statement.setInt(4, guild.getOwnerId());
                statement.setInt(5, colorOne);
                statement.setInt(6, colorTwo);
                statement.setString(7, badge);
                statement.setInt(8, Emulator.getIntUnixTimestamp());
                statement.execute();

                try (ResultSet set = statement.getGeneratedKeys()) {
                    if (set.next()) {
                        guild.setId(set.getInt(1));
                    }
                }
            }

            preparedStatement.executeBatch();

        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }
    }

    /*public static void insert(String word) {
        Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;

        try {
            sqlConnection = SqlHelper.getConnection();

            preparedStatement = SqlHelper.prepare("INSERT INTO wordfilter (`word`, `replacement`) VALUES(?, ?)", sqlConnection);

            preparedStatement.setString(1, word);
            preparedStatement.setString(2, "bobba");
            preparedStatement.addBatch();
            preparedStatement.executeBatch();


        } catch (SQLException e) {
            SqlHelper.handleSqlException(e);
        } finally {
            SqlHelper.closeSilently(preparedStatement);
            SqlHelper.closeSilently(sqlConnection);
        }
    }*/
}
