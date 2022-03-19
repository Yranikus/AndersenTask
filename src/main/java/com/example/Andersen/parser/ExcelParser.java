package com.example.Andersen.parser;

import com.example.Andersen.entity.Student;
import com.example.Andersen.entity.Teams;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

@Component
public class ExcelParser {


    public ArrayList<Student> parseListOfUsers(InputStream inputStream) throws IOException {
        ArrayList<Student> students = new ArrayList<>();
        Workbook book =  WorkbookFactory.create(inputStream);
        Sheet sheet = book.getSheetAt(0);
        int rowIndex = 0;
        while (sheet.getRow(rowIndex) != null){
            Student student = new Student(sheet.getRow(rowIndex).getCell(0).getStringCellValue(),
                    (int) sheet.getRow(rowIndex).getCell(1).getNumericCellValue(), 0);
            students.add(student);
            rowIndex++;
        }

        return students;
    }

    public ArrayList<Teams> parseListOfTeams(InputStream inputStream) throws IOException {
        ArrayList<Teams> teams = new ArrayList<>();
        Workbook book =  WorkbookFactory.create(inputStream);
        Sheet sheet = book.getSheetAt(0);
        int rowIndex = 0;
        int cellIndex = 0;
        int indexOfTeam = 1;
        while (sheet.getRow(rowIndex).getCell(cellIndex) != null) {
            teams.add(new Teams(indexOfTeam));
            indexOfTeam++;
            cellIndex = cellIndex + 3;
        }
        cellIndex = 0;
        rowIndex++;
        indexOfTeam = 0;
        while (sheet.getRow(rowIndex) != null){
                while (sheet.getRow(rowIndex).getCell(cellIndex) != null){
                    if (sheet.getRow(rowIndex).getCell(cellIndex) != null) {
                        Student student = new Student(sheet.getRow(rowIndex).getCell(cellIndex).getStringCellValue(),
                                (int) sheet.getRow(rowIndex).getCell(cellIndex + 1).getNumericCellValue(), 0);
                        teams.get(indexOfTeam).addStudent(student);
                        if (sheet.getRow(rowIndex).getCell(cellIndex + 2) != null) {
                            System.out.println("puk");
                            teams.get(indexOfTeam).setLeader(student.getName());
                        }
                        teams.get(indexOfTeam).setRepo("repo");
                    }
                    indexOfTeam++;
                    cellIndex = cellIndex + 3;
                }
                indexOfTeam = 0;
                rowIndex++;
                cellIndex = 0;
        }

        return teams;
    }




}
