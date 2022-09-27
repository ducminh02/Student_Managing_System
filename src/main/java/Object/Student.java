package Object;

import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Student {
    private List<Exam> exams;
    private String name;
    private int id;
    private String dob;
    private int randomseed;

    public Student(String name, String dob) {
        this.name = name;
        this.randomseed = getRandomNumber(Integer.MIN_VALUE, Integer.MAX_VALUE);
        this.dob = dob;
        this.exams = new ArrayList<>();
        this.idtranslate(name, dob, this.randomseed);
    }

    public int idtranslate(String name, String dob, int randomseed) {
        return name.hashCode() ^ dob.hashCode() ^ randomseed;
    }

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

}
