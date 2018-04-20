package students_journal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.TreeMap;

public class DBManager
{
    private static final String SQLITE_PREFIX = "jdbc:sqlite";

    private static TreeMap<String, Connection> connections_ =  new TreeMap<>();

    public static void initConnection(String db_name) throws DBException
    {
        if (getDBConnection(db_name) != null)
            throw new DBException("connection for '" + db_name + "' is already init");

        try
        {
            Class.forName("org.sqlite.JDBC");
        }
        catch (ClassNotFoundException ex)
        {
            throw new DBException("failed to register driver for sqlite", ex);
        }

        try
        {
            Connection connection = DriverManager.getConnection(SQLITE_PREFIX + "/" + db_name);

            if (connection == null)
                throw new DBException("connection for db '" + db_name + "' is null");

            connections_.put(db_name, connection);
        }
        catch (SQLException ex)
        {
            throw new DBException("failed to connect to '" + db_name + "'", ex);
        }
    }

    public static Connection getDBConnection(String db_name)
    {
        return connections_.get(db_name);
    }
}
