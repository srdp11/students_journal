package students_journal;

import java.sql.*;
import java.util.ArrayList;

public class Database
{
    private Connection connection_;

    public Database(Connection connection)
    {
        connection_ = connection;
    }

    public ArrayList<String> execSQL(String sql) throws DBException
    {
        try (Statement statement = connection_.createStatement())
        {
            ArrayList<String> result = new ArrayList<>();

            if (!sql.startsWith("SELECT"))
            {
                statement.executeUpdate(sql);
                return result;
            }

            try (ResultSet data = statement.executeQuery(sql))
            {
                final ResultSetMetaData metadata = data.getMetaData();
                final int column_count = metadata.getColumnCount();

                while (data.next())
                {
                    for (int i = 1; i <= column_count; ++i)
                        result.add(data.getString(i));
                }

                return result;
            }
        }
        catch (SQLException ex)
        {
            throw new DBException("failed to execute sql '" + sql + "'", ex);
        }
    }

    public boolean tableExists(String table_name) throws DBException
    {
        try
        {
            DatabaseMetaData metadata = connection_.getMetaData();

            try (ResultSet rs = metadata.getTables(null, null, table_name, null))
            {
                return rs.next();
            }
        }
        catch (SQLException ex)
        {
            throw new DBException("failed to get info about table '" + table_name + "'");
        }
    }

    public void closeDB() throws DBException
    {
        try
        {
            connection_.close();
        }
        catch (SQLException ex)
        {
            throw new DBException("failed to close db", ex);
        }
    }
}
