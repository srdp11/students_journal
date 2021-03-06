package students_journal;

import java.util.ArrayList;

public class DBQuery
{
    public static String insertInto(String table_name, ArrayList<String> fields, ArrayList<String> values)
    {
        final String joined_fields = join(fields, ',');
        final String joined_values = join(values, ',');

        StringBuilder sb = new StringBuilder();

        sb.append("INSERT INTO ").append(table_name);
        sb.append("(").append(joined_fields).append(")");
        sb.append(" VALUES (").append(joined_values).append(")");

        return sb.toString();
    }

    public static String deleteWithID(String table_name, int id)
    {
        return "DELETE FROM " + table_name + " WHERE id = " + String.valueOf(id);
    }

    public static String select(String table_name, ArrayList<String> fields_to_select)
    {
        return selectWithCondition(table_name, fields_to_select, null);
    }

    public static String selectWithCondition(String table_name, ArrayList<String> fields_to_select, ArrayList<String> conditions)
    {
        String where_part = "";

        if (conditions != null && !conditions.isEmpty())
            where_part = join(conditions, ',');

        final String joined_fields = join(fields_to_select, ',');
        String sql = "SELECT " + joined_fields + " FROM " + table_name;

        if (!where_part.isEmpty())
            sql += " WHERE " + where_part;

        return sql;
    }

    private static String join(ArrayList<String> array_list, char delimiter)
    {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < array_list.size(); ++i)
        {
            if (i != 0)
                sb.append(delimiter);

            sb.append(array_list.get(i));
        }

        return sb.toString();
    }
}
