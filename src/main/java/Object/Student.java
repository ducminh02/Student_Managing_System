package Object;

import java.util.ArrayList;
import java.util.List;


public class Student {
    private List<Exam> exams;
    private String name;
    private int id;
    private String dob;
    private int randomseed;
    private double average;
    private double durchschnitt;

    public Student(String name, String dob) {
        this.name = name;
        this.randomseed = getRandomNumber(-100000000, 100000000);
        this.dob = dob;
        this.exams = new ArrayList<>();
        this.id = Math.abs(idtranslate(name, dob, this.randomseed)) ;

        double aver = 0;
        for (Exam e: exams) {
            aver += e.getPercentage();
        }
        this.average = aver / exams.size();

        double durch = 0;
        for (Exam e : exams) {
            durch += e.getGermanscore();
        }
        this.durchschnitt = durch / exams.size();
    }

    public int idtranslate(String name, String dob, int randomseed) {
        return name.hashCode() ^ dob.hashCode() ^ randomseed;
    }

    public String getName() {
        return name;
    }

    public List<Exam> getExams() {
        return exams;
    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    public void setExams(List<Exam> exams) {
        this.exams = (List<Exam>) exams;
    }

    @Override
    public String toString() {
        return "Student{" +
                ", name='" + name + '\'' +
                ", id=" + id +
                ", dob='" + dob + '\'' +
                ", average=" + average +
                ", durchschnitt=" + durchschnitt +
                '}';
    }
}
