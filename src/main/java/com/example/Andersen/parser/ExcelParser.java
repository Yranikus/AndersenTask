package com.example.Andersen.parser;

import com.example.Andersen.entity.Student;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

@Component
public class ExcelParser {


    public ArrayList<Student> parse(InputStream inputStream) throws IOException {
        ArrayList<Student> students = new ArrayList<>();
        Workbook book =  WorkbookFactory.create(inputStream);
        Sheet sheet = book.getSheetAt(0);
        int rowIndex = 1;
        while (sheet.getRow(rowIndex + 1) != null){
            Student student = new Student(sheet.getRow(rowIndex).getCell(0).getStringCellValue(), (int) sheet.getRow(rowIndex).getCell(1).getNumericCellValue(), 0);
            students.add(student);
            rowIndex++;
        }

        return students;
    }



}
