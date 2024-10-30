package data;

import lombok.SneakyThrows;
import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLHelper {

    private static final QueryRunner QUERY_RUNNER = new QueryRunner();

    private SQLHelper() {
    }

    //задаем параметры подключения к базе данных
    private static Connection getConn() throws SQLException {
        return DriverManager.getConnection(System.getProperty("db.url"), "app", "pass");
    }

    //метод для очистки базы данных
    @SneakyThrows
    public static void cleanDatabase() {
        var connection = getConn();
        QUERY_RUNNER.execute(connection,"DELETE FROM auth_codes");
        QUERY_RUNNER.execute(connection,"DELETE FROM card_transactions");
        QUERY_RUNNER.execute(connection,"DELETE FROM cards");
        QUERY_RUNNER.execute(connection,"DELETE FROM users");
    }
    //метод для очистки таблицы - когда надо сбросить счетчик
    @SneakyThrows
    public static void cleanAuthCodes() {
        var connection = getConn();
        QUERY_RUNNER.execute(connection,"DELETE FROM auth_codes");
    }
}

