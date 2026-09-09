package ru.netology.data;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHelper {
    private static final QueryRunner runner = new QueryRunner();
    private static final String URL = "jdbc:mysql://localhost:3306/app";
    private static final String USER = "app";
    private static final String PASS = "pass";

    private SQLHelper() {
    }

    private static Connection getConn() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    public static String getVerificationCode() {
        var codeSQL = "SELECT code FROM auth_codes ORDER BY created DESC LIMIT 1;";
        try (var conn = getConn()) {
            return runner.query(conn, codeSQL, new ScalarHandler<>());
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public static void cleanDatabase() {
        try (var conn = getConn()) {
            runner.execute(conn, "DELETE FROM auth_codes;");
            runner.execute(conn, "DELETE FROM card_transactions;");
            runner.execute(conn, "DELETE FROM cards;");
            runner.execute(conn, "UPDATE users SET status = 'active';");
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}