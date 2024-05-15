package com.kriscfoster.school.student;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kriscfoster.school.subject.Subject;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnTransformer;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Student {

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

    @JsonIgnore
    @ManyToMany(mappedBy = "enrolledStudents")
    private Set<Subject> subjects = new HashSet<>();

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Subject> getSubjects() {
        return subjects;
    }

}
