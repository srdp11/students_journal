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
            try (ResultSet data = statement.executeQuery(sql))
            {
                ArrayList<String> result = new ArrayList<>();

                final ResultSetMetaData metadata = data.getMetaData();
                final int column_count = metadata.getColumnCount();

                while (data.next())
                {
                    for (int i = 1; i <= column_count; ++i)
                        result.add(metadata.getColumnName(i));
                }

                return result;
            }
        }
        catch (SQLException ex)
        {
            throw new DBException("failed to execute sql '" + sql + "'", ex);
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
