package Management;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import Object.Student;


public class Logic {

    public static void saveStudent(Student student, String filePath) throws IOException {
        FileWriter tmp = new FileWriter(filePath);
        new Gson().toJson(student, tmp);
        tmp.close();
    }

    public static Student loadStudent(String filePath) throws FileNotFoundException {
        Gson gson = new Gson();
        return gson.fromJson(new FileReader(filePath), Student.class);
    }



    public static void main(String[] args) throws IOException {
        Student student = new Student("Minh", "2306");
        saveStudent(student, "C:\\Users\\MSI\\Documents\\Projekt\\Minh\\Output\\abc.json");
    }
}
