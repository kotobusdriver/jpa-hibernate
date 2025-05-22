package org.example.app.repository;

import org.example.app.entity.Student;

import javax.persistence.*;
import java.util.List;

public class StudentDaoImpl implements GenericDao <Student, Long> {
    private final EntityManagerFactory emf;

    public StudentDaoImpl() {
        this.emf = Persistence.createEntityManagerFactory("my-persistence-unit");
    }

    private EntityManager em () { // the jpa "session" for a series of operations; must be closed after use
        return emf.createEntityManager();
    }

    @Override
    public void save(Student student) {
        EntityManager em = em(); // get a fresh entity manager
        EntityTransaction tx = em.getTransaction(); // get and try perfoming transations
        try {
            tx.begin();
            em.persist(student); // schedule for insertion
            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close(); // always close
        }
    }

    @Override
    public Student findById(Long id) {
        EntityManager em = em();
        try {
            return em.find(Student.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public Student findByEmail(String email) {
        EntityManager em = em();
        try {
            TypedQuery<Student> query = em.createQuery("Select s FROM Student s WHERE s.email = :email", Student.class);
            query.setParameter("email", email);
            return query.getResultStream().findFirst().orElse(null);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Student> findAll() {
        EntityManager em = em();
        try {
            return em.createQuery("SELECT s FROM Student s", Student.class).getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Student update(Student student) {
        EntityManager em = em();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Student studentMerged = em.merge(student);
            tx.commit();
            return studentMerged;
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    @Override
    public boolean deleteById(Long id) {
        EntityManager em = em();
        EntityTransaction tx = em.getTransaction();

        tx.begin();
        Student studentToRemove = em.find(Student.class, id);
        if (studentToRemove == null) {
            tx.rollback();
            return false;
        }
        em.remove(studentToRemove);
        tx.commit();
        return true;
    }
}
