package Management;
import Object.Exam;
import Object.Student;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Logic {


    public static void saveStudent(List<Student> studentlist, String filePath) throws IOException {
        FileWriter tmp = new FileWriter(filePath);

            new Gson().toJson(studentlist, tmp);

        tmp.close();
    }


    public static List<Student> loadStudent (String filePath) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        Type Studentlisttype = new TypeToken<ArrayList<Student>>(){}.getType();
        return new Gson().fromJson(br.readLine(), Studentlisttype);
    }

    public void takeexam (String filePath, String StudentfilePath) throws IOException {
        BufferedReader exambr = new BufferedReader
                (new FileReader(filePath));
        List<Student> studentList = loadStudent(StudentfilePath);

        String date = exambr.readLine();
        String thema = exambr.readLine();
        int count = 0;

        int totalscore = Integer.parseInt(exambr.readLine());


        while (exambr.readLine() != null ) {
            String nameandscore = exambr.readLine();
            if (nameandscore != null) {
                String[] both = nameandscore.split(":");
                Exam newexam = new Exam(totalscore, Double.parseDouble(both[1]), thema, date);
                for (Student s : studentList) {
                    if (s.getName().equals(both[0])) {
                        s.getExams().add(newexam);
                    }
                }
            }
        }
        saveStudent(studentList, StudentfilePath);

    }



    public void initstudentsyang () throws IOException {
        BufferedReader br = new BufferedReader( new FileReader("C:\\Users\\MSI\\Documents\\Projekt\\Minh\\Student_Managing_System\\src\\main\\java\\Data\\Klasse Yang\\Students\\Student_Names.txt"));
        List<Student> studentList = new ArrayList<>();

        int numsofstudents =  Integer.parseInt(br.readLine());
        for (int i = 0; i < numsofstudents; i++) {
            String line = br.readLine();
            String[] both = line.split(":");

            Student student = new Student(both[0], both[1]);
            studentList.add(student);
        }
        saveStudent(studentList, "C:\\Users\\MSI\\Documents\\Projekt\\Minh\\Student_Managing_System\\src\\main\\java\\Data\\Klasse Yang\\Students\\StudentData.txt");
    }

    public void clearexams (String studentfilePath) throws IOException {
        List<Student> students = loadStudent(studentfilePath);
        for (Student s : students) {
            s.getExams().clear();
        }
    }



    public static void main(String[] args) throws IOException {
        Logic logic = new Logic();
        logic.initstudentsyang();
        System.out.println(loadStudent("C:\\Users\\MSI\\Documents\\Projekt\\Minh\\Student_Managing_System\\src\\main\\java\\Data\\Klasse Yang\\Students\\StudentData.txt"));
        logic.takeexam("C:\\Users\\MSI\\Documents\\Projekt\\Minh\\Student_Managing_System\\src\\main\\java\\Data\\Klasse Yang\\Exams\\Exam_1.txt","C:\\Users\\MSI\\Documents\\Projekt\\Minh\\Student_Managing_System\\src\\main\\java\\Data\\Klasse Yang\\Students\\StudentData.txt");
    }
}
