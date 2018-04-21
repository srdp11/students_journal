import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import students_journal.InvalidDataException;
import students_journal.JournalException;
import students_journal.Student;
import students_journal.StudentsJournal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class StudentsJournalTest
{
    private static final String TEST_DB_NAME = "test.db";
    private StudentsJournal journal_;

    @Before
    public void setUpTest() throws JournalException, InvalidDataException
    {
        journal_ = new StudentsJournal(TEST_DB_NAME);
        journal_.initJournal();
    }

    @After
    public void tearDown() throws JournalException
    {
        journal_.clearJournal();
        journal_.closeJournal();
    }

    @Test
    public void addStudents() throws InvalidDataException, JournalException
    {
        final ArrayList<Student> test_students = getTestData();

        for (Student student : test_students)
            journal_.addStudent(student);

        ArrayList<Student> students_from_db = journal_.getAllStudents();

        assertEquals(test_students.size(), students_from_db.size());

        for (int i = 0; i < test_students.size(); ++i)
            assertEquals(test_students.get(i), students_from_db.get(i));
    }

    @Test
    public void removeStudentByID() throws InvalidDataException, JournalException
    {
        final ArrayList<Student> test_students = getTestData();
        journal_.addStudent(test_students.get(0));

        ArrayList<Student> students_from_db = journal_.getAllStudents();
        final Student student = students_from_db.get(0);
        assertEquals(1, students_from_db.size());

        assertNotNull(student.getID());

        journal_.removeStudentByID(student.getID());
        ArrayList<Student> updated_students = journal_.getAllStudents();
        assertEquals(0, updated_students.size());
    }

    private ArrayList<Student> getTestData() throws JournalException, InvalidDataException
    {
        ArrayList<Student> students = new ArrayList<>();
        students.add(createStudent("Ivan", "Ivanov", "Ivanovich", "VM-12", "15.11.1995"));
        students.add(createStudent("Oleg", "Petrov", null, "VM-11", "04.03.1996"));
        students.add(createStudent("Vladimir", "Vladimirov", "Vladimirovich", "VT-21", "10.12.1999"));

        return students;
    }

    private static Student createStudent(String name, String second_name, String patronymic,
                                         String group, String birthday)
            throws InvalidDataException
    {
        Student student = new Student();
        student.setName(name);
        student.setSecondName(second_name);
        student.setPatronymic(patronymic);
        student.setGroup(group);
        student.setBirthday(strToDate(birthday));

        return student;
    }

    private static Date strToDate(String date)
    {
        try
        {
            return new SimpleDateFormat("dd.MM.yyyy").parse(date);
        }
        catch (ParseException ex)
        {
            return null;
        }
    }
}
