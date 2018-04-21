package application;

import students_journal.InvalidDataException;
import students_journal.JournalException;
import students_journal.StudentsJournal;

public class App
{
    private static final String DB_NAME = "production.db";

    public static void main(String[] args)
    {
        StudentsJournal journal = new StudentsJournal(DB_NAME);

        try
        {
            journal.initJournal();
        }
        catch (JournalException ex)
        {
            System.out.println("failed to init journal: " + ex.getMessage());
            return;
        }

        CommandArgsProcessor args_processor = new CommandArgsProcessor(args, journal);

        try
        {
            args_processor.process();
        }
        catch (InvalidDataException | ProgramArgsException | JournalException ex)
        {
            System.out.println("failed to process operation: " + ex.getMessage());
            return;
        }

        try
        {
            journal.closeJournal();
        }
        catch (JournalException ex)
        {
            System.out.println("failed to close journal: " + ex.getMessage());
        }
    }
}
