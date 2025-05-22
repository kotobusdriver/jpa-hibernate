package org.example.app.entity;

public class Student {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Set<Homework> homeworks = new HashSet<>();

    public void addHomework(final Homework homework) {
        // TODO implement this method
    }

    public void removeHomework(final Homework homework) {
        // TODO implement this method
    }
}
