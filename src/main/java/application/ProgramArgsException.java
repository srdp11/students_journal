package application;

public class ProgramArgsException extends Exception
{
    public ProgramArgsException()
    {
    }

    public ProgramArgsException(String message)
    {
        super(message);
    }

    public ProgramArgsException(Throwable cause)
    {
        super(cause);
    }

    public ProgramArgsException(String message, Throwable cause)
    {
        super(message, cause);
    }
}