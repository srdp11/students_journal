package application;

import javafx.util.Pair;
import students_journal.InvalidDataException;
import students_journal.JournalException;
import students_journal.Student;
import students_journal.StudentsJournal;

import java.util.ArrayList;

public class CommandArgsProcessor
{
    private final String[] args_;
    private StudentsJournal journal_;

    public CommandArgsProcessor(String[] args, StudentsJournal journal)
    {
        args_ = args;
        journal_ = journal;
    }

    public void process() throws InvalidDataException, JournalException, ProgramArgsException
    {
        if (args_.length == 0)
        {
            System.out.println("use --help for obtain info about program");
            return;
        }

        final String command = args_[0];

        switch (command)
        {
            case "--add":
                addStudent();
                break;

            case "--remove":
                removeStudent();
                break;

            case "--show":
                showStudents();
                break;

            case "--help":
                showHelp();
                break;

            default:
                throw new ProgramArgsException("unknown command");
        }
    }

    private void addStudent() throws InvalidDataException, JournalException, ProgramArgsException
    {
        Student student = new Student();

        for (Pair<String, String> curr_pair : getFieldAndValues())
            student.setFieldByName(curr_pair.getKey(), curr_pair.getValue());

        journal_.addStudent(student);
        System.out.println("student was add");
    }

    private void removeStudent() throws JournalException, ProgramArgsException
    {
        if (args_.length > 2)
            throw new ProgramArgsException("usage: --remove <id>");

        final String raw_id = args_[1];

        int id;

        try
        {
            id = Integer.valueOf(raw_id);
        }
        catch (NumberFormatException ex)
        {
            throw new ProgramArgsException("invalid id (" + raw_id + "): id must be number");
        }

       journal_.removeStudentByID(id);

       System.out.println("student with id=" + id + " was deleted");
    }

    private void showStudents() throws JournalException, ProgramArgsException
    {
        ArrayList<Student> students;

        if (args_.length == 1)
        {
            students = journal_.getAllStudents();
        }
        else if (isValidConditions())
        {
            final ArrayList<String> conditions = decorateValues();
            students = journal_.getStudents(conditions);
        }
        else
        {
            throw new ProgramArgsException("wrong usage of --show command");
        }

        if (students.isEmpty())
        {
            System.out.println("students not found");
        }
        else
        {
            for (Student curr_student : students)
                System.out.println(curr_student);
        }
    }

    private void showHelp()
    {
        StringBuilder sb = new StringBuilder();

        sb.append("usage: students_journal [--help] <command> [<args>]").append(System.lineSeparator());
        sb.append("Commands:").append(System.lineSeparator());
        sb.append("--add <[field=value]>: ").append("add new student to journal with specified [field=value] (fields: id, name, second_name, patronymic, study_group, birthday (dd.MM.yyyy))").append(System.lineSeparator());
        sb.append("--remove <id>: ").append("remove student with specified <id>").append(System.lineSeparator());
        sb.append("--show <[field=value]>: ").append("show info about students. if args not specified show all students, otherwise only students which satisfied conditions <field=value>.").append(System.lineSeparator());
        sb.append(System.lineSeparator()).append("Examples: ").append(System.lineSeparator());
        sb.append("add: students_journal --add name=Vasya second_name=Petrov patronymic=Vasilevich study_group=VM-11 birthday=02.02.1997").append(System.lineSeparator());
        sb.append("remove: students_journal --remove 4").append(System.lineSeparator());
        sb.append("show: students_journal --show study_group=VM-11").append(System.lineSeparator());

        System.out.println(sb.toString());
    }

    private ArrayList<Pair<String, String>> getFieldAndValues() throws ProgramArgsException
    {
        ArrayList<Pair<String, String>> field_and_values = new ArrayList<>();

        for (int i = 1; i < args_.length; ++i)
        {
            final String[] arg_parts = args_[i].split("=");

            if (arg_parts.length != 2)
                throw new ProgramArgsException("arguments must have like 'field=value'");

            field_and_values.add(new Pair<>(arg_parts[0], arg_parts[1]));
        }

        return field_and_values;
    }

    private ArrayList<String> decorateValues() throws ProgramArgsException
    {
        ArrayList<String> decorated = new ArrayList<>();

        for (int i = 1; i < args_.length; ++i)
        {
            final String[] arg_parts = args_[i].split("=");

            if (arg_parts.length != 2)
                throw new ProgramArgsException("arguments must have like 'field=value'");

            decorated.add(arg_parts[0] + "='" + arg_parts[1] + "'");
        }

        return decorated;
    }

    private boolean isValidConditions()
    {
        for (int i = 1; i < args_.length; ++i)
        {
            final String[] arg_parts = args_[i].split("=");

            if (arg_parts.length != 2)
                return false;
        }

        return true;
    }
}
