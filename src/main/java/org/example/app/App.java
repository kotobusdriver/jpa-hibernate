package org.example.app;


import org.example.app.entity.Student;
import org.example.app.entity.Homework;
import org.example.app.repository.StudentDaoImpl;

import java.time.LocalDate;
import java.util.List;

public class App {
    public static void main(String[] args) {
        StudentDaoImpl studentDao = new StudentDaoImpl();

        // create student
        Student alice = new Student();
        alice.setFirstName("Alice");
        alice.setLastName("Smith");
        alice.setEmail("alice.smith@example.com");

        // create homeworks
        Homework math = new Homework();
        math.setDescription("Math assignment");
        math.setDeadline(LocalDate.of(2025, 6, 22));
        alice.addHomework(math);

        Homework history = new Homework();
        history.setDescription("History essay");
        history.setDeadline(LocalDate.of(2025, 6, 22));
        alice.addHomework(history);

        // saving
        studentDao.save(alice);
        System.out.println("Saved student with ID: " + alice.getId());

        // find all
        List<Student> all = studentDao.findAll();
        System.out.println("All students:");
        all.forEach(System.out::println);

        //update homework
        Student fetched = studentDao.findById(alice.getId());
        System.out.println("Before update: " + fetched);
        Homework firstHw = fetched.getHomeworks().iterator().next();
        firstHw.setDeadline(LocalDate.of(2025, 6, 25));
        // persist the change
        studentDao.update(fetched);
        System.out.println("After update:");
        System.out.println(studentDao.findById(alice.getId()));

        // delete
        boolean deleted = studentDao.deleteById(alice.getId());
        System.out.println("Deleted? " + deleted);
        System.out.println("Remaining students: " + studentDao.findAll().size());
    }
}
