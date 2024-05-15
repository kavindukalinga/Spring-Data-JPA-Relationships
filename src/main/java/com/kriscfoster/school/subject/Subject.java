package com.kriscfoster.school.subject;

import com.kriscfoster.school.student.Student;
import com.kriscfoster.school.teacher.Teacher;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnTransformer;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private static final String symkey = "secret-key-12345";

    @Column(name = "name", columnDefinition = "bytea")
    @ColumnTransformer(
            read = "PGP_SYM_DECRYPT(name,'"+symkey+"')",
            write = "PGP_SYM_ENCRYPT (?, '"+symkey+"')"
    )
    private String name;

    @ManyToMany
    @JoinTable(
            name = "student_enrolled",
            joinColumns = @JoinColumn(name = "subject_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    Set<Student> enrolledStudents = new HashSet<>();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "teacher_id", referencedColumnName = "id")
    private Teacher teacher;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Student> getEnrolledStudents() {
        return enrolledStudents;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

}
