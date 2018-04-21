package students_journal;

public class JournalException extends Exception
{
    public JournalException()
    {
    }

    public JournalException(String message)
    {
        super(message);
    }

    public JournalException(Throwable cause)
    {
        super(cause);
    }

    public JournalException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
