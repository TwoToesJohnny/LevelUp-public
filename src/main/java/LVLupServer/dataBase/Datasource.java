package LVLupServer.dataBase;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;

import java.sql.SQLException;

import java.util.logging.Level;
import java.util.logging.Logger;
/**
Sets up connection pooling for database
 */
public class Datasource {
    private static final Logger LOGGER = Logger.getLogger(DataSource.class.getName());
    private static final HikariConfig config = new HikariConfig();
    private static final HikariDataSource ds;

    static {
        config.setJdbcUrl("jdbc:mysql://localhost:3306/levelup_schema");
        config.setUsername("Shaun");
        config.setPassword("Shaun1234");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        ds = new HikariDataSource(config);
    }
    private Datasource(){}

    public static Connection getConnection() {
        try {
            return ds.getConnection();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error getting connection", e);
            throw new RuntimeException(e);
        }
    }

}
