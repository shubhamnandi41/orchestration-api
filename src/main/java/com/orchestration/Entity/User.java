package com.orchestration.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;


@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Indexed
public class User {

    @Id
    @NonNull
    private Long id;

    @FullTextField
    private String firstName;

    @FullTextField
    private String lastName;


    private String email;

    @FullTextField
    private String ssn;

     private String username;
     private String password;

     private String role;

}
