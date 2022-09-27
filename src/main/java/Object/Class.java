package Object;

import java.util.ArrayList;
import java.util.List;

public class Class {
    private List<Student> students;
    private String name;
    private String level;

    public Class (String name, String level) {
        this.name = name;
        this.level =level;
        this.students = new ArrayList<>();
    }
}
