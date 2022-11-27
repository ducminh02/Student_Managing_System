import Management.Logic;

import java.io.IOException;


public class Main {
    public static void main(String[] args) throws IOException {
        Logic logic = new Logic();
        // Path to the StudentData text file
        String studentDataFileYang = ".\\src\\main\\java\\Data\\Klasse Yang\\Students\\StudentData.txt";
        String studentDataFileYin = ".\\src\\main\\java\\Data\\Klasse Yin\\Students\\StudentData.txt";

        // Path to the Student_Names text file
        String StudentNamesYang = ".\\src\\main\\java\\Data\\Klasse Yang\\Students\\Student_Names.txt";
        String StudentNamesYin = ".\\src\\main\\java\\Data\\Klasse Yin\\Students\\Student_Names.txt";

        // Path to the Exam folder for Class Yang and Class Yin
        String filePathExamYang = ".\\src\\main\\java\\Data\\Klasse Yang\\Exams";
        String filePathExamYin = ".\\src\\main\\java\\Data\\Klasse Yin\\Exams";

//        String filePathExamYin = ".\\src\\main\\java\\Data\\Klasse Yin\\Exams";
        // Path to Exam 1
        String filePathExam1 = ".\\src\\main\\java\\Data\\Klasse Yang\\Exams\\Exam_2.txt";
        // Path to Exam 2
        String filePathExam2 = ".\\Data\\Klasse Yin\\Exams\\Exam_1.txt";




        logic.start(studentDataFileYang,studentDataFileYin,filePathExamYang,filePathExamYin, StudentNamesYang, StudentNamesYin);
    }
}
