package Management;

import Object.Exam;
import Object.Student;
import Object.Classs;

import java.util.List;
import java.util.Scanner;

public class MenuLogic {

    private List<Student> studentList;

    public MenuLogic(String StudentfilePathYang, String StudentfileYin, String filePathYang,
                     String filePathYin) {
        this.studentList = studentList;
    }

    public static void start (Menu startMenu) {
        Scanner scanner = new Scanner(System.in);
        Menu currentMenu = startMenu;
        int input;
        while (true) {
            System.out.println(currentMenu.content);
            input =scanner.nextInt();
            currentMenu = resolve(currentMenu, input);
        }
    }

    public static Menu resolve (Menu currentMenu, int option) {
        if (currentMenu.map.containsKey(option)) {
            Menu nextMenu = currentMenu.map.get(option);

            // do Stuff based on given input/ option
            if (currentMenu.map.containsKey(option)) {
                doMenuFuction(currentMenu, option);
                return nextMenu;
            }
        }
        return currentMenu;
    }

    private static void doMenuFuction (Menu currentMenu, int option ) {
        System.out.println(currentMenu.content);
        if (currentMenu.name.equalsIgnoreCase("main")) {
            // do main menu stuff here

            if (option == 4) {
                System.exit(0);
            }
        }

        if (currentMenu.name.equalsIgnoreCase("studentInfoYin")) {

        }
    }

    public static void initMenu () {
        Menu mainMenu = new Menu("main", """
                Enter 1 to see further information
                Enter 2 (Student name) (Class) to add new student
                Enter 3 to create a new Exam
                Enter 4 to exit Program""");

        Menu information = new Menu("information", """
                Which type of information would you like to see?
                1 Student
                2 Class
                3 Back to main menu""");

        Menu studentInformation = new Menu("studentInformation", """
                Enter the student's class
                1 Yin
                2 Yang
                """);

        Menu classInformation = new Menu("classInformation", """
                Enter Class Name
                1 Yin
                2 Yang""");


        Menu studentInfoYin = new Menu("studentInfoYin" , "Enter student's name");
        Menu studentInfoYang = new Menu("studentInfoYang", "Enter student's name");

        Menu classInfoYin = new Menu("classInfoYin", "");
        Menu classInfoYang = new Menu("classInfoYang", "");


        mainMenu.map.put(1, information);

        information.map.put(1, studentInformation);
        information.map.put(2, classInformation);

        classInformation.map.put(1, classInfoYin);
        classInformation.map.put(2, classInfoYang);

        studentInformation.map.put(1, studentInfoYin);
        studentInformation.map.put(2, studentInfoYang);



    }
}
