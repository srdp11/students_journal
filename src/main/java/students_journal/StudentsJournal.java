package students_journal;

import java.util.ArrayList;
import java.util.Map;

public class StudentsJournal
{
    private final String db_name_;

    public StudentsJournal(String db_name)
    {
        db_name_ = db_name;
    }

    public void initJournal() throws JournalException
    {
        try
        {
            DBManager.initDB(db_name_);
            createStudentsTableIfNotExists();
        }
        catch (DBException ex)
        {
            throw new JournalException("failed to open journal in '" + db_name_ + "'", ex);
        }
    }

    public void clearJournal() throws JournalException
    {
        try
        {
            DBManager.getDB(db_name_).execSQL("DELETE FROM " + Student.getTableName());
        }
        catch (DBException ex)
        {
            throw new JournalException("failed to add student " + "" + " to database", ex);
        }
    }

    public void closeJournal() throws JournalException
    {
        try
        {
            DBManager.closeDB(db_name_);
        }
        catch (DBException ex)
        {
            throw new JournalException("failed to close journal in '" + db_name_ + "'", ex);
        }
    }

    public void addStudent(Student student) throws JournalException
    {
        if (student == null)
            throw new JournalException("student record is null");

        ArrayList<String> fields = new ArrayList<>();
        ArrayList<String> values = new ArrayList<>();

        for (String field : Student.getFields())
        {
            final String value = student.getValues().get(field);

            if (field.equals(Student.ID) && value == null)
                continue;

            if (value == null && !Student.nullable(field))
                throw new JournalException("field '" + field + "' can't be nullable");

            fields.add(field);
            values.add("'" + value + "'");
        }

        try
        {
            DBManager.getDB(db_name_).execSQL(DBQuery.insertInto(Student.getTableName(), fields, values));
        }
        catch (DBException ex)
        {
            throw new JournalException("failed to add student " + "" + " to database", ex);
        }
    }

    public void removeStudentByID(int id) throws JournalException
    {
        try
        {
            Database db = DBManager.getDB(db_name_);
            db.execSQL(DBQuery.deleteWithID(Student.getTableName(), id));
        }
        catch (DBException ex)
        {
            throw new JournalException("failed to remove student with id=" + id + " from database");
        }
    }

    public ArrayList<Student> getStudents(ArrayList<String> conditions) throws JournalException
    {
        try
        {
            ArrayList<String> fields_to_select = Student.getFields();
            ArrayList<Student> students = new ArrayList<>();

            ArrayList<String> students_info = DBManager.getDB(db_name_).execSQL(DBQuery.selectWithCondition(Student.getTableName(), fields_to_select, conditions));

            for (int i = 0; i < students_info.size(); i += Student.fieldsNum())
            {
                Student student = new Student();

                for (int j = 0; j < fields_to_select.size(); ++j)
                {
                    try
                    {
                        student.setFieldByName(fields_to_select.get(j), students_info.get(j + i));
                    }
                    catch (InvalidDataException ex)
                    {
                        throw new JournalException("failed to assign value '" + students_info.get(j + i) + "' to field '" + fields_to_select.get(j) + "'");
                    }
                }

                students.add(student);
            }

            return students;
        }
        catch (DBException ex)
        {
            throw new JournalException("failed to select students from database '" + db_name_ + "'", ex);
        }
    }

    public ArrayList<Student> getAllStudents() throws JournalException
    {
        return getStudents(null);
    }

    private void createStudentsTableIfNotExists() throws DBException
    {
        final String fields = "id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                " name TEXT," +
                " second_name TEXT," +
                " patronymic TEXT," +
                " study_group TEXT," +
                " birthday TEXT";

        final String create_table_sql = "CREATE TABLE IF NOT EXISTS " + Student.getTableName() + "(" + fields + ")";

        DBManager.getDB(db_name_).execSQL(create_table_sql);
    }
}
