package Management;
import Object.Exam;
import Object.Student;
import Object.Classs;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;


import java.awt.*;
import java.io.*;
import java.lang.reflect.Type;
import java.util.*;
import java.util.List;

import static java.lang.System.exit;


public class Logic {

    private int count = 2;

    // save object students as Json

    public static void saveStudent(List<Student> studentlist, String filePath) throws IOException {
        FileWriter tmp = new FileWriter(filePath);

        Gson gson = new GsonBuilder()
                .serializeSpecialFloatingPointValues()
                .create();
        String data = gson.toJson(studentlist);
        tmp.write(data);
        tmp.close();
    }

    // load Json to student object

    public static List<Student> loadStudent (String filePath) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        Type Studentlisttype = new TypeToken<ArrayList<Student>>(){}.getType();
        return new Gson().fromJson(br.readLine(), Studentlisttype);
    }

    // students take 1 exam

    public void takeExam(String examFilePath, String studentFilePath) throws IOException {
        BufferedReader exambr = new BufferedReader
                (new FileReader(examFilePath));
        List<Student> studentList = loadStudent(studentFilePath);

        String date = exambr.readLine();
        String thema = exambr.readLine();

        int totalscore = Integer.parseInt(exambr.readLine());

        String nameandscore = exambr.readLine();
        while (nameandscore != null && !nameandscore.equals("")) {
                String[] both = nameandscore.split(":");
                Exam newexam = new Exam(totalscore, Double.parseDouble(both[1]), thema, date);
                for (Student s : studentList) {
                    if (s.getName().equals(both[0])) {
                        s.getExams().add(newexam);
                        break;
                    }
                }
                nameandscore = exambr.readLine();

        }
        saveStudent(studentList, studentFilePath);

    }

    // students take all exams in the Exam folder

    public void takeallexam (String filePathExamYang, String studentDataYang, String filePathExamYin,
                             String studentDataYin) throws IOException {
        // take all exams Yang
       int count = Objects.requireNonNull(new File(filePathExamYang).list()).length;
        for (int i = 1; i <= count; i++) {
            takeExam(filePathExamYang + "\\Exam_" + i + ".txt", studentDataYang);
        }

        // take all exams Yin
        int count1 = Objects.requireNonNull(new File(filePathExamYin).list()).length;
        for (int i = 1; i <= count1; i++) {
            takeExam(filePathExamYin + "\\Exam_" + i + ".txt", studentDataYin);
        }
    }

    // initialize a list of students to data file which can be stored

    public void initstudents (String filePathnames, String filePathdata) throws IOException {
        BufferedReader br = new BufferedReader( new FileReader(filePathnames));
        List<Student> studentList = new ArrayList<>();

        String line = br.readLine();

        while (line != null && line.length() > 0) {
            String[] both = line.split(":");

            Student student = new Student(both[0], both[1]);
            studentList.add(student);
            line = br.readLine();
        }
        saveStudent(studentList, filePathdata);
    }

    // clear all exams from students from a class

    public void clearexams (String studentDataFile) throws IOException {
        List<Student> students = loadStudent(studentDataFile);
        for (Student s : students) {
            s.getExams().clear();
        }
    }

    // Menu at the start of program

    public void start (String StudentDataYang, String StudentDataYin, String filePathExamYang, String filePathExamYin,
                       String StudentNamesYang, String StudentNamesYin) throws IOException {

        while (true) {
            System.out.println("Welcome to Gfs Student Management System");
            System.out.println('\n');
            System.out.println("Enter 1 to see further information");
            System.out.println("Enter 2 to add new student");
            System.out.println("Enter 3 to create a new Exam");
            System.out.println("Enter 4 to reload data");
            System.out.println("Enter 5 to exit Program");

            Scanner sc = new Scanner(System.in);
            int input = sc.nextInt();
            if (input == 1) {
                information(StudentDataYang, StudentDataYin, filePathExamYang, filePathExamYin, StudentNamesYang, StudentNamesYin);
            }
            if (input == 2) {
                addNewStudent(StudentNamesYang, StudentNamesYin, StudentDataYang, StudentDataYin, filePathExamYang, filePathExamYin);
            }
            if (input == 3) {
                addExam(filePathExamYang, StudentDataYang, filePathExamYin, StudentDataYin, StudentNamesYang, StudentNamesYin);
            }
            if (input == 4) {
                reload(filePathExamYang, StudentDataYang, filePathExamYin, StudentDataYin);
            }

            if (input == 5) {
                exit(0);
            }
        }
    }

    public void reload (String filePathExamYang, String StudentDataYang, String filePathExamYin, String StudentDataYin) throws IOException {

        // clear exams from Data file
        clearexams(StudentDataYang);
        clearexams(StudentDataYin);

        // take all exams
        takeallexam(filePathExamYang, StudentDataYang, filePathExamYin, StudentDataYin);

        // update Grades
        List<Student> studentListYang = loadStudent(StudentDataYang);
        for (Student student : studentListYang) {
            student.updateGrade();
        }
        saveStudent(studentListYang, StudentDataYang);

        List<Student> studentListYin = loadStudent(StudentDataYin);
        for (Student student : studentListYin) {
            student.updateGrade();
        }
        saveStudent(studentListYin, StudentDataYin);
    }

    // Menu for after completing a task

    public void endOfTask (String StudentDataYang, String StudentDataYin, String filePathExamYang, String filePathExamYin,
                           String StudentNamesYang, String StudentNamesYin) throws IOException {
        while (true) {
            Scanner scc = new Scanner(System.in);
            System.out.println("\n");
            System.out.println("==========================");
            System.out.println("\n");
            System.out.println("What would you like to do?");
            System.out.println("\n");
            System.out.println("1 Back to main menu");
            System.out.println("2 Exit Program");

            int inputLast = scc.nextInt();
            if (inputLast == 1) {
                start(StudentDataYang, StudentDataYin, filePathExamYang, filePathExamYin, StudentNamesYang, StudentNamesYin);
            }
            if (inputLast == 2) {
                exit (0);
            }
        }
    }

    // Menu for checking for information

    public void information ( String StudentDataYang, String StudentDataYin , String filePathExamYang, String filePathExamYin,
                              String StudentNamesYang, String StudentNamesYin) throws IOException {

        while (true) {

            // Display messages
            System.out.println("Which type of information would you like to see?");
            System.out.println('\n');
            System.out.println("1 Student");
            System.out.println("2 Class");
            System.out.println("3 Go back to main menu");
            Scanner sc = new Scanner(System.in);

            int input = sc.nextInt();
            if (input == 1) {

                    //  Student information
                    System.out.println("Enter the student's class (Yin/ Yang)");
                    String clname = sc.next();

                    // Student in Yang class
                    if (clname.equalsIgnoreCase("yang")) {
                        List<Student> studentList = loadStudent(StudentDataYang);
                        System.out.println("Enter the student's name");
                        String stname = sc.next();
                        for (Student s : studentList) {
                            if (stname.equalsIgnoreCase(s.getName())) {
                                System.out.println(s.toString());

                                // end Menu
                                endOfTask(StudentDataYang, StudentDataYin , filePathExamYang, filePathExamYin,
                                        StudentNamesYang, StudentNamesYin);
                            }
                        }
                    }

                    // Student in Yin class
                    if (clname.equalsIgnoreCase("yin")) {
                        List<Student> studentList = loadStudent(StudentDataYin);
                        System.out.println("Enter the student's name");

                        String stname = sc.next();
                        for (Student s : studentList) {
                            if (stname.equalsIgnoreCase(s.getName())) {
                                System.out.println(s.toString());

                                // end Menu
                                endOfTask(StudentDataYang, StudentDataYin , filePathExamYang, filePathExamYin, StudentNamesYang,
                                        StudentNamesYin);
                            }
                        }
                    }

                    // Error class
                    else {
                        throw new InputMismatchException("No such class found");
                    }

            }

            // Class information
            if (input == 2) {
                System.out.println("Which Class?");
                System.out.println("1 Yin");
                System.out.println("2 Yang");
                int inp = sc.nextInt();

                // Class Yin
                if (inp == 1) {
                    Classs yin = new Classs("Yin", "B1");
                    yin.setStudents(loadStudent(StudentDataYin));
                    System.out.println(yin.toString());
                    System.out.println("Students:");
                    for (int i = 1; i <= yin.getStudents().size(); i++) {
                        System.out.println(i + " " +yin.getStudents().get(i-1).getName());
                    }

                    // end Menu
                    endOfTask(StudentDataYang,StudentDataYin,filePathExamYang,filePathExamYin, StudentNamesYang,
                            StudentNamesYin);
                }

                // Class Yang
                if (inp == 2) {
                    Classs yang = new Classs("Yin", "B1");
                    yang.setStudents(loadStudent(StudentDataYang));
                    System.out.println(yang.toString());
                    System.out.println("Students:");
                    for (int i = 1; i <= yang.getStudents().size(); i++) {
                        System.out.println(i + " " +yang.getStudents().get(i-1).getName());
                    }

                    // end Menu
                    endOfTask(StudentDataYang,StudentDataYin,filePathExamYang,filePathExamYin, StudentNamesYang,
                            StudentNamesYin);
                }

                // Error class
                else {
                    throw new InputMismatchException("No such class found");
                }

            }

            // return to start menu
            if (input == 3) {
                start(StudentDataYang, StudentDataYin, filePathExamYang, filePathExamYin, StudentNamesYang, StudentNamesYin);
            }
        }


    }

    // delete data in a file
    public void clearData (String studentDataFile) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter (studentDataFile);
        writer.print("");
        writer.close();

    }

    public void addNewStudent ( String studentNameYang, String studentNameYin, String studentDataYang, String studentDataYin,
                                String filePathExamYang, String filePathExamYin) throws IOException {

        // get the student name, dob and class
        System.out.println("Enter the name of the new student (Menu to return to Main Menu)");
        Scanner sc = new Scanner(System.in);
        String inpSName = sc.nextLine();

        // exit to start menu
        if (inpSName.equalsIgnoreCase("Menu")) {
            start(studentDataYang, studentDataYin, filePathExamYang, filePathExamYin, studentNameYang, studentNameYin);
        }

        System.out.println("Enter the date of birth of the new student (dd/mm/yyyy)");
        String dob = sc.next();
        System.out.println("Enter the class name of the new student");
        System.out.println("1 Yang");
        System.out.println("2 Yin");
        int inpCName = sc.nextInt();

        // add new student to class Yang
        if (inpCName == 1) {

            System.out.println("Is the new student: " + inpSName + " (" + dob + ") Class Yang?\n" +
                    "1. Yes\n" +
                    "2. No"  );

            int confirm = sc.nextInt();

            // not confirm
            if (confirm == 2) {
                addNewStudent(studentNameYang, studentNameYin, studentDataYang, studentDataYin, filePathExamYang, filePathExamYin);
            }

            // confirm
            if (confirm == 1) {
                // write student's name to Student_Names file
                try {
                    FileWriter fw = new FileWriter(studentNameYang, true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    PrintWriter print = new PrintWriter (bw);

                    print.println(inpSName + ":" + dob);
                    print.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                // delete data in the StudentData file
                clearData (studentDataYang);

                // initialize students Yang
                initstudents(studentNameYang, studentDataYang);
                reload(filePathExamYang,studentDataYang, filePathExamYin, studentDataYin);

                // endOfTask
                endOfTask(studentDataYang, studentDataYin, filePathExamYang, filePathExamYin, studentNameYang, studentNameYin);
            }

            // Error confirm
            else {
                throw new InputMismatchException("Failed by confirm");
            }

        }

        // add students to class Yin
        else if (inpCName == 2) {
            System.out.println("Is the new student: " + inpSName + " (" + dob + ") Class Yin?\n" +
                    "1. Yes\n" +
                    "2. No"  );

            int confirm = sc.nextInt();

            //not confirm
            if (confirm == 2) {
                addNewStudent(studentNameYang, studentNameYin, studentDataYang, studentDataYin, filePathExamYang, filePathExamYin);
            }

            // confirm
            if (confirm == 1) {
                // write student's name to Student_Names file
                try {
                    FileWriter fw = new FileWriter(studentNameYin, true);
                    BufferedWriter bw = new BufferedWriter(fw);
                    PrintWriter print = new PrintWriter (bw);

                    print.println(inpSName + ":" + dob);
                    print.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                // delete data in the StudentData file
                clearData (studentDataYin);

                // initialize students Yang
                initstudents(studentNameYin, studentDataYin);
                reload(filePathExamYang,studentDataYang, filePathExamYin, studentDataYin);

                // endOfTask
                endOfTask(studentDataYang, studentDataYin, filePathExamYang, filePathExamYin, studentNameYang, studentNameYin);
            }

            //Error confirm
            else {
                throw new InputMismatchException("Failed by confirm");
            }

        }

        // Error class
        else {
            throw new InputMismatchException("No such class found");
        }

    }

    public void addExam(String filePathExamYang, String studentDataYang, String filePathExamYin, String studentDataYin,
                        String studentNameYang, String studentNameYin) throws IOException {

        System.out.println("""
                Which class is the exam for?
                1. Yang
                2. Yin
                3. Start Menu""");

        Scanner sc = new Scanner(System.in);
        int inpC = sc.nextInt();

        if (inpC ==1) {
            int count = Objects.requireNonNull(new File(filePathExamYang).list()).length + 1;
            File examFile = new File(filePathExamYang +  "\\Exam_" + count + ".txt");
            boolean isExamCreated = examFile.createNewFile();

            if (!Desktop.isDesktopSupported()) {
                System.out.println("not supported on this Device");
            }
            Desktop desktop = Desktop.getDesktop();
            if (isExamCreated) {
                desktop.open(examFile);
            }

            System.out.println("""
                    Fill in the Textfile as the format below
                    Date (dd/MM/yyyy)
                    Topic of the Exam (LK Länder)
                    max score (10)
                    StudentName1:Score (Linh:9)
                    StudentName2:Score (Duc:8)
                    StudentName3:Score (Hiep:10)
                    ...
                    
                    After fill in and save the Exams
                    Press 1""");

            int done = sc.nextInt();

            if (done == 1) {
                reload(filePathExamYang, studentDataYang, filePathExamYin, studentDataYin);
                endOfTask(studentDataYang, studentDataYin, filePathExamYang, filePathExamYin, studentNameYang, studentNameYin);
            }
        }

        if (inpC ==2) {
            int count = Objects.requireNonNull(new File(filePathExamYin).list()).length;
            File examFile = new File(filePathExamYin +  "\\Exam_" + count +1 + ".txt");

            if (!Desktop.isDesktopSupported()) {
                System.out.println("not supported on this Device");
            }
            Desktop desktop = Desktop.getDesktop();
            if (examFile.exists()) {
                desktop.open(examFile);
            }

            System.out.println("""
                    Fill in the Textfile as the format below
                    Date (dd/MM/yyyy)
                    Topic of the Exam (LK Länder)
                    max score (10)
                    StudentName1:Score (Manh:8)
                    StudentName2:Score (Huy:7)
                    StudentName3:Score (Phuong:9)
                    ...
                    
                    After fill in and save the Exams
                    Press 1""");

            int done = sc.nextInt();

            if (done == 1) {
                reload(filePathExamYang, studentDataYang, filePathExamYin, studentDataYin);
                endOfTask(studentDataYang, studentDataYin, filePathExamYang, filePathExamYin, studentNameYang, studentNameYin);
            }
        }
    }





    public static void main(String[] args) throws IOException {
        Logic logic = new Logic();
        // Path to the StudentData text file
        String studentDataFileYang = "C:\\Users\\MSI\\Documents\\Projekt\\Minh\\Student_Managing_System\\src\\main\\java\\Data\\Klasse Yang\\Students\\StudentData.txt";
        String studentDataFileYin = "C:\\Users\\MSI\\Documents\\Projekt\\Minh\\Student_Managing_System\\src\\main\\java\\Data\\Klasse Yin\\Students\\StudentData.txt";

        // Path to the Student_Names text file
        String StudentNamesYang = "C:\\Users\\MSI\\Documents\\Projekt\\Minh\\Student_Managing_System\\src\\main\\java\\Data\\Klasse Yang\\Students\\Student_Names.txt";
        String StudentNamesYin = "C:\\Users\\MSI\\Documents\\Projekt\\Minh\\Student_Managing_System\\src\\main\\java\\Data\\Klasse Yin\\Students\\Student_Names.txt";

        // Path to the Exam folder for Class Yang and Class Yin
        String filePathExamYang = "C:\\Users\\MSI\\Documents\\Projekt\\Minh\\Student_Managing_System\\src\\main\\java\\Data\\Klasse Yang\\Exams";
        String filePathExamYin = "C:\\Users\\MSI\\Documents\\Projekt\\Minh\\Student_Managing_System\\src\\main\\java\\Data\\Klasse Yin\\Exams";

        // Path to Exam 1
        String filePathExam1 = "C:\\Users\\MSI\\Documents\\Projekt\\Minh\\Student_Managing_System\\src\\main\\java\\Data\\Klasse Yang\\Exams\\Exam_2.txt";
        // Path to Exam 2
        String filePathExam2 = "C:\\Users\\MSI\\Documents\\Projekt\\Minh\\Student_Managing_System\\src\\main\\java\\Data\\Klasse Yin\\Exams\\Exam_1.txt";




        logic.start(studentDataFileYang,studentDataFileYin,filePathExamYang,filePathExamYin, StudentNamesYang, StudentNamesYin);


    }
}
