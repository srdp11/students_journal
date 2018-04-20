package students_journal;

import java.util.Date;

public class Student
{
    private String name_;
    private String second_name_;
    private String patronymic_;
    private String group_;
    private Date birthday_;
    private Integer personal_id_;

    public void setName(String name) {
        this.name_ = name;
    }

    public String getName() {
        return name_;
    }

    public void setSecondName(String second_name) {
        this.second_name_ = second_name;
    }

    public String getSecondName() {
        return second_name_;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic_ = patronymic;
    }

    public String getPatronymic() {
        return patronymic_;
    }

    public void setGroup(String group) {
        this.group_ = group;
    }

    public String getGroup() {
        return group_;
    }

    public void setBirthday(Date birthday) {
        this.birthday_ = birthday;
    }

    public Date getBirthday() {
        return birthday_;
    }

    public void setPersonalID(Integer personal_id) {
        this.personal_id_ = personal_id;
    }

    public Integer getPersonalID() {
        return personal_id_;
    }
}
