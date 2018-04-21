package students_journal;

import java.util.ArrayList;
import java.util.Map;

public class StudentsJournal
{
    public static final String DB_NAME = "statistics.db";

    static
    {
        try
        {
            DBManager.initDB(DB_NAME);
        }
        catch (DBException ex)
        {
            throw new RuntimeException("failed to init db '" + DB_NAME + "'", ex);
        }
    }

    public static void addStudent(Student student) throws JournalException
    {
        if (student == null)
            throw new JournalException("student record is null");

        ArrayList<String> fields = new ArrayList<>();
        ArrayList<String> values = new ArrayList<>();

        for (Map.Entry<String, String> entry : student.getValues().entrySet())
        {
            fields.add(entry.getKey());
            values.add(entry.getValue());
        }

        try
        {
            DBManager.getDB(DB_NAME).execSQL(DBQuery.insertInto(Student.getTableName(), fields, values));
        }
        catch (DBException ex)
        {
            throw new JournalException("failed to add student " + "" + " to database");
        }
    }

    public static void removeStudentByID(int id) throws JournalException
    {
        try
        {
            Database db = DBManager.getDB(DB_NAME);
            db.execSQL(DBQuery.deleteWithID(Student.getTableName(), id));
        }
        catch (DBException ex)
        {
            throw new JournalException("failed to remove student with id=" + id + " from database");
        }
    }

    public static ArrayList<Student> getAllStudents() throws JournalException
    {
        try
        {
            ArrayList<String> fields = Student.getFields();
            ArrayList<Student> students = new ArrayList<>();

            ArrayList<String> students_info = DBManager.getDB(DB_NAME).execSQL(DBQuery.select(Student.getTableName(), fields));

            for (int i = 0; i < students_info.size(); i += Student.fieldsNum())
            {
                Student student = new Student();

                for (int j = 0; j < fields.size(); ++j)
                {
                    if (!student.setFieldByName(fields.get(j), students_info.get(j + i)))
                        throw new JournalException("failed to assign value '" + students_info.get(j + i) + "' to field '" + fields.get(j) + "'");
                }
            }

            return students;
        }
        catch (DBException ex)
        {
            throw new JournalException("failed to select students from '" + DB_NAME + "' database");
        }
    }

    private static void createStudentsTableIfNotExists() throws DBException
    {
        final String fields = "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " name TEXT," +
                " second_name TEXT," +
                " patronymic TEXT," +
                " group TEXT," +
                " birthday TEXT";

        final String create_table_sql = "CREATE TABLE IF NOT EXISTS " + Student.getTableName() + "(" + fields + ")";

        DBManager.getDB(DB_NAME).execSQL(create_table_sql);
    }
}
