package com.sarunas.Visitation.display.board.to.a.specialist.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "specialists")
public class SpecialistEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    private String password;
    @ElementCollection(targetClass = Role.class)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    private List<Role> roles;
    @Column(unique = true)
    @Email
    private String email;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "specialist_id")
    private List<ClientEntity> clients;
    @Column(name = "is_busy")
    private Boolean isBusy;
}
