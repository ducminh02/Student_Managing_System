package Management;
import Object.Exam;
import Object.Student;
import Object.Classs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;


import java.io.*;
import java.lang.reflect.Type;
import java.util.*;


public class Logic {


    public static void saveStudent(List<Student> studentlist, String filePath) throws IOException {
        FileWriter tmp = new FileWriter(filePath);

        Gson gson = new GsonBuilder()
                .serializeSpecialFloatingPointValues()
                .enableComplexMapKeySerialization()
                .setPrettyPrinting()
                .create();
        String data = gson.toJson(studentlist);
        tmp.write(data);

//            new Gson().toJson(studentlist, tmp);

        tmp.close();
    }


    public static List<Student> loadStudent (String filePath) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        Type Studentlisttype = new TypeToken<ArrayList<Student>>(){}.getType();
        return new Gson().fromJson(br.readLine(), Studentlisttype);
    }

    public void takeExam(String filePath, String studentFilePath) throws IOException {
        BufferedReader exambr = new BufferedReader
                (new FileReader(filePath));
        List<Student> studentList = loadStudent(studentFilePath);

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
        saveStudent(studentList, studentFilePath);

    }

    public void takeallexam (String filePath, String StudentfilePath) throws IOException {
       int count = Objects.requireNonNull(new File(filePath).list()).length;
        for (int i = 1; i <= count; i++) {
            takeExam(filePath + "\\Exam_" + i + ".txt", StudentfilePath);
        }
    }





    public void initstudents (String filePathnames, String filePathdata) throws IOException {
        BufferedReader br = new BufferedReader( new FileReader(filePathnames));
        List<Student> studentList = new ArrayList<>();

        int numsofstudents =  Integer.parseInt(br.readLine());
        for (int i = 0; i < numsofstudents; i++) {
            String line = br.readLine();
            String[] both = line.split(":");

            Student student = new Student(both[0], both[1]);
            studentList.add(student);
        }
        saveStudent(studentList, filePathdata);
    }

    public void clearexams (String studentfilePath) throws IOException {
        List<Student> students = loadStudent(studentfilePath);
        for (Student s : students) {
            s.getExams().clear();
        }
    }

    public void start (String StudentfilePathYang, String StudentfilePathYin, String filePathYang, String filePathYin) throws IOException {
        System.out.println("Wellcome to Gfs Student Management System");
        System.out.println('\n');
        System.out.println("Enter 1 to see further information");
        System.out.println("Enter 2 (Student name) (Class) to add new student");
        System.out.println("Enter 3 to create a new Exam");
        System.out.println("Enter 4 to reload data");

        Scanner sc = new Scanner(System.in);
        int input = sc.nextInt();
        if (input == 1) {
            information(StudentfilePathYang, StudentfilePathYin);
        }
        if (input == 4) {
            reload(filePathYang, StudentfilePathYang, filePathYin, StudentfilePathYin);
        }

    }

    public void reload (String filePathYang, String StudentfilePathYang, String filePathYin, String StudentfilePathYin) throws IOException {
        clearexams(StudentfilePathYang);
        takeallexam(filePathYang,StudentfilePathYang);
        clearexams(StudentfilePathYin);
        takeallexam(filePathYin,StudentfilePathYin);
    }

    public void information ( String StudentfilePathYang, String StudentfilePathYin) throws IOException {
        System.out.println("Which type of information would you like to see?");
        System.out.println('\n');
        System.out.println("1 Student");
        System.out.println("2 Class");
        Scanner sc = new Scanner(System.in);

        int input = sc.nextInt();
        if (input == 1) {
            System.out.println("Enter the student's class");
            String clname = sc.next();

            if (clname.equalsIgnoreCase("yang")) {
                List<Student> studentList = loadStudent(StudentfilePathYang);
                String stname = sc.nextLine();
                for (Student s : studentList) {
                    if (stname.equalsIgnoreCase(s.getName())) {
                        System.out.println(s.toString());
                    }
                    else {
                        throw new InputMismatchException("No such name found");
                    }
                }
            }

            if (clname.equalsIgnoreCase("yin")) {
                List<Student> studentList = loadStudent(StudentfilePathYin);
                String stname = sc.nextLine();
                for (Student s : studentList) {
                    if (stname.equalsIgnoreCase(s.getName())) {
                        System.out.println(s.toString());
                    }
                    else {
                        throw new InputMismatchException("No such name found");
                    }
                }
            }

            else {
                throw new InputMismatchException("No such class found");
            }
        }
        if (input == 2) {
            System.out.println("Which Class?");
            System.out.println("1 Yin");
            System.out.println("2 Yang");
            int inp = sc.nextInt();

            if (inp == 1) {
                Classs yin = new Classs("Yin", "B1");
               yin.setStudents(loadStudent(StudentfilePathYin));
                System.out.println(yin.toString());
                System.out.println("Students:");
                for (int i = 1; i <= yin.getStudents().size(); i++) {
                    System.out.println(i + " " +yin.getStudents().get(i-1).getName());
                }
            }

            if (inp == 2) {
                Classs yang = new Classs("Yin", "B1");
                yang.setStudents(loadStudent(StudentfilePathYang));
                System.out.println(yang.toString());
                System.out.println("Students:");
                for (int i = 1; i <= yang.getStudents().size(); i++) {
                    System.out.println(i + " " +yang.getStudents().get(i-1).getName());
                }
            }

            else {
                throw new InputMismatchException("No such class found");
            }
        }
    }




    public static void main(String[] args) throws IOException {
        Logic logic = new Logic();
        // Path to the StudentData text file
        String StudentPathFileYang = "C:\\Users\\MSI\\Documents\\Projekt\\Minh\\Student_Managing_System\\src\\main\\java\\Data\\Klasse Yang\\Students\\StudentData.txt";
        String StudentPathFileYin = "C:\\Users\\MSI\\Documents\\Projekt\\Minh\\Student_Managing_System\\src\\main\\java\\Data\\Klasse Yin\\Students\\StudentData.txt";

        // Path to the Student_Names text file
        String StudentNamesYang = "C:\\Users\\MSI\\Documents\\Projekt\\Minh\\Student_Managing_System\\src\\main\\java\\Data\\Klasse Yang\\Students\\Student_Names.txt";
        String StudentNamesYin = "C:\\Users\\MSI\\Documents\\Projekt\\Minh\\Student_Managing_System\\src\\main\\java\\Data\\Klasse Yin\\Students\\Student_Names.txt";

        // Path to the folder for Class Yang and Class Yin
        String filePathYang = "C:\\Users\\MSI\\Documents\\Projekt\\Minh\\Student_Managing_System\\src\\main\\java\\Data\\Klasse Yang";
        String filePathYin = "C:\\Users\\MSI\\Documents\\Projekt\\Minh\\Student_Managing_System\\src\\main\\java\\Data\\Klasse Yin\\Students";

//        logic.start(StudentPathFileYang,StudentPathFileYin,filePathYang,filePathYin);
        logic.initstudents(StudentNamesYang,StudentPathFileYang);

    }
}
