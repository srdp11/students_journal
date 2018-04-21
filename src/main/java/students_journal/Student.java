package students_journal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

public class Student
{
    private static final String TABLE_NAME = "students";

    public static final String ID = "id";
    private static final String NAME = "name";
    private static final String SECOND_NAME = "second_name";
    private static final String PATRONYMIC = "patronymic";
    private static final String GROUP = "study_group";
    private static final String BIRTHDAY = "birthday";

    private static ArrayList<String> fields_;
    private static ArrayList<String> nullable_fields_;

    static
    {
        fields_ = new ArrayList<>();
        fields_.add(ID);
        fields_.add(NAME);
        fields_.add(SECOND_NAME);
        fields_.add(PATRONYMIC);
        fields_.add(GROUP);
        fields_.add(BIRTHDAY);

        nullable_fields_ = new ArrayList<>();;
        nullable_fields_.add(PATRONYMIC);
    }

    private TreeMap<String, String> values_ = new TreeMap<>();

    public void setName(String name) throws InvalidDataException
    {
        if (name == null)
            throw new InvalidDataException("name can not be null");

        values_.put(NAME, name);
    }

    public String getName()
    {
        return values_.get(NAME);
    }

    public void setSecondName(String second_name) throws InvalidDataException
    {
        if (second_name == null)
            throw new InvalidDataException("second name can not be null");

        values_.put(SECOND_NAME, second_name);
    }

    public String getSecondName()
    {
        return values_.get(SECOND_NAME);
    }

    public void setPatronymic(String patronymic)
    {
        values_.put(PATRONYMIC, patronymic);
    }

    public String getPatronymic()
    {
        return values_.get(PATRONYMIC);
    }

    public void setGroup(String group) throws InvalidDataException
    {
        if (group == null)
            throw new InvalidDataException("group can not be null");

        values_.put(GROUP, group);
    }

    public String getGroup()
    {
        return values_.get(GROUP);
    }

    public void setBirthday(Date birthday) throws InvalidDataException
    {
        if (birthday == null)
            throw new InvalidDataException("birthday can not be null");

        values_.put(BIRTHDAY, new SimpleDateFormat("dd.MM.yyyy").format(birthday));
    }

    public Date getBirthday()
    {
        try
        {
            return new SimpleDateFormat("dd.MM.yyyy").parse(values_.get(BIRTHDAY));
        }
        catch (ParseException ex)
        {
            return null;
        }
    }

    public String getStrBirthday()
    {
        return values_.get(BIRTHDAY);
    }

    public void setID(int id) throws InvalidDataException
    {
        values_.put(ID, String.valueOf(id));
    }

    public Integer getID()
    {
        if (values_.get(ID) == null)
            return null;

        return Integer.valueOf(values_.get(ID));
    }

    public void setFieldByName(String field, String value) throws InvalidDataException
    {
        if (value.equals("null"))
            value = null;

        if (field == null)
            throw new InvalidDataException("field is null");

        if (!fields_.contains(field))
            throw new InvalidDataException("invalid field " + field);

        if (!nullable_fields_.contains(field) && value == null)
            throw new InvalidDataException("field " + field + " can't be nullable");

        if (field.equals(BIRTHDAY))
        {
            try
            {
                new SimpleDateFormat("dd.MM.yyyy").parse(value);
            }
            catch (ParseException ex)
            {
                throw new InvalidDataException("date must be in format dd.MM.yyyy", ex);
            }
        }

        values_.put(field, value);
    }

    @Override
    public boolean equals(Object other)
    {
        if (other == this)
            return false;

        if (!(other instanceof Student))
            return false;

        Student student = (Student)other;

        return Utils.equals(getName(), student.getName()) &&
                Utils.equals(getSecondName(), student.getSecondName()) &&
                Utils.equals(getPatronymic(), student.getPatronymic()) &&
                Utils.equals(getGroup(), student.getGroup()) &&
                Utils.equals(getBirthday(), student.getBirthday());
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        for (String curr_field : fields_)
        {
            final String curr_val = values_.get(curr_field);

            if (curr_val != null)
                sb.append(curr_field).append("=").append(curr_val).append(",");
        }

        sb.deleteCharAt(sb.length() - 1);

        return sb.toString();
    }

    static String getTableName()
    {
        return TABLE_NAME;
    }

    static ArrayList<String> getFields()
    {
        return fields_;
    }

    static int fieldsNum()
    {
        return fields_.size();
    }

    static boolean nullable(String field)
    {
        return nullable_fields_.contains(field);
    }

    TreeMap<String, String> getValues()
    {
        return values_;
    }
}
