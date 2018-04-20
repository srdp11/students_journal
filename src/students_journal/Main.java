package students_journal;

public class Main
{

    public static void main(String[] args) throws DBException
    {
        DBManager.initConnection("./foo.db");
    }
}
