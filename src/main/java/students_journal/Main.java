package students_journal;

import java.io.File;

public class Main
{

    public static void main(String[] args) throws DBException
    {
        File curr_dir = new File("");
        DBManager.initConnection(curr_dir.getAbsolutePath() + "/databases/foo.db");
    }
}
