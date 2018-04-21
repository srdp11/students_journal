package students_journal;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.TreeMap;

public class DBManager
{
    private static final String SQLITE_PREFIX = "jdbc:sqlite:";
    private static final String DB_FOLDER = "databases";

    private static TreeMap<String, Database> databases_ =  new TreeMap<>();

    public static void initDB(String db_name) throws DBException
    {
        createDBFolderIfNotExists();

        if (getDB(db_name) != null)
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
            Connection connection = DriverManager.getConnection(SQLITE_PREFIX + "./" + DB_FOLDER + "/" + db_name);

            if (connection == null)
                throw new DBException("connection for db '" + db_name + "' is null");

            databases_.put(db_name, new Database(connection));
        }
        catch (SQLException ex)
        {
            throw new DBException("failed to connect to '" + db_name + "'", ex);
        }
    }

    public static Database getDB(String db_name)
    {
        return databases_.get(db_name);
    }

    private static String getDBPath(String db_name)
    {
        File curr_dir = new File("");
        return curr_dir.getAbsolutePath() + "/" + DB_FOLDER + "/" + db_name;
    }

    private static void createDBFolderIfNotExists() throws DBException
    {
        File db_dir = new File(DB_FOLDER);

        if (!db_dir.exists())
        {
            if (!db_dir.mkdirs())
                throw new DBException("failed to create folder " + db_dir.getPath() + " for DB");
        }
    }
}
