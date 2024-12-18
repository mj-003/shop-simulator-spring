package com.example.studentlisth2lombok;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

@SpringBootTest
public class StudentRepositoryTests {
    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void allStudentTest(){
        List<Student> studentList=studentRepository.findAll();
        System.out.println("studentList = " + studentList);
    }

    @Test
    public void byFirstNameTest(){
        List<Student> studentList=studentRepository.findByFirstName("Ewa");
        System.out.println("studentList = " + studentList);
    }

    @Test
    public void byFirstNameContainingTest(){
        List<Student> studentList=studentRepository.findByFirstNameContaining("a");
        System.out.println("studentList = " + studentList);
    }

    @Test
    public void byIndexGreaterTest(){
        List<Student> studentList=studentRepository.findByIndexGreaterThanOrFirstNameContaining(12345,"a");
        System.out.println("studentList = " + studentList);
    }

    @Test
    public void getStudentWithFirstNameLengthLessThanTest(){
        List<Student> studentList=studentRepository.getStudentWithFirstNameLengthLessThan(4);
        System.out.println("studentList = " + studentList);
    }
    @Test
    public void getStudentNameWithFirstNameLengthLessThanTest(){
        List<String> namesList=studentRepository.getStudentNameWithFirstNameLengthLessThan(4);
        System.out.println("student names = " + namesList);
    }
    @Test
    public void getStudentNameWithFirstNameLengthLessThanNativeTest(){
        List<String> namesList=studentRepository.getStudentNameWithFirstNameLengthLessThanNative(4);
        System.out.println("student names = " + namesList);
    }

    @Test
    public void ChangeEwaLastNameTest(){
        int changes=studentRepository.setLastNameForFirstName("Crud","Ewa");
        System.out.println("changes = " + changes);
        List<Student> studentList=studentRepository.findByFirstName("Ewa");
        System.out.println("studentList = " + studentList);
        studentRepository.setLastNameForFirstName("Snake","Ewa");
    }

    @Test
    @Transactional
    public void ChangeEwaLastNameTransactionalTest(){
        int changes=studentRepository.setLastNameForFirstName("Crud","Ewa");
        System.out.println("changes = " + changes);
        List<Student> studentList=studentRepository.findByFirstName("Ewa");
        System.out.println("studentList = " + studentList);
    }
}
