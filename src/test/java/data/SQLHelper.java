package data;

import lombok.SneakyThrows;
import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static java.lang.Long.parseLong;

public class SQLHelper {

    private static final String url = System.getProperty("db.url");
    private static final String username = System.getProperty("username");
    private static final String password = System.getProperty("password");

    private static final QueryRunner runner = new QueryRunner();

    private SQLHelper() {
    }

    private static Connection getConnMySql() throws SQLException {
        return DriverManager.getConnection(url,username,password);
    }


    //последний платящий пользователь
    @SneakyThrows
    public static String getLastPayUserStatusMySQL() {
        Thread.sleep(10000);
        var payStatus = "SELECT status FROM payment_entity order by created desc LIMIT 1";
        var conn = getConnMySql();
        return runner.query(conn, payStatus, new ScalarHandler<>());
    }

    //последняя сумма оплаты
    @SneakyThrows
    public static int getLastPayUserAmountMySQL() {
        Thread.sleep(10000);
        var amount = "SELECT amount FROM payment_entity order by created desc LIMIT 1";
        var conn = getConnMySql();
        return runner.query(conn, amount, new ScalarHandler<>());
    }

    //статус последнего пользователя с оплатой в кредит
    @SneakyThrows
    public static String getLastPayOnCreditUserStatusMySQL() {
        Thread.sleep(10000);
        var creditStatus = "SELECT status FROM credit_request_entity order by created desc LIMIT 1";
        var conn = getConnMySql();
        return runner.query(conn, creditStatus, new ScalarHandler<>());
    }
    @SneakyThrows
    public static void cleanTables() throws SQLException {

        val deleteOrderEntity = "DELETE FROM order_entity";
        val deletePaymentEntity = "DELETE FROM payment_entity";
        val deleteCreditRequestEntity = "DELETE FROM credit_request_entity";
        val countSQL = "SELECT COUNT(*) FROM order_entity";
        val runner = new QueryRunner();
        val conn = DriverManager.getConnection(
                url, username, password);
        runner.update(conn, deleteOrderEntity);
        runner.update(conn, deletePaymentEntity);
        runner.update(conn, deleteCreditRequestEntity);
        runner.query(conn, countSQL, new ScalarHandler<>());
    }

    //есть ли запись
    @SneakyThrows
    public static String countRecords() {
        val countSQL = "SELECT COUNT(*) FROM order_entity";
        val runner = new QueryRunner();
        Long count = Long.valueOf(" ");
        val conn = DriverManager.getConnection(
                url, username, password);
        count = runner.query(conn, countSQL, new ScalarHandler<>());
        return  Long.toString(count);
    }
}