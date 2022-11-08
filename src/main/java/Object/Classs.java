package Object;

import java.util.ArrayList;
import java.util.List;
import Object.Student;

public class Classs {
    private List<Student> students;
    private String name;
    private String level;

    public Classs (String name, String level) {
        this.name = name;
        this.level =level;
        this.students = new ArrayList<>();
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return "Class{" +
                "name='" + name + '\'' +
                ", level='" + level + '\'' +
                '}';
    }
}
