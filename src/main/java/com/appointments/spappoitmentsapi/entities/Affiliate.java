package com.appointments.spappoitmentsapi.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jdk.dynalink.linker.LinkerServices;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Affiliate {

    //attributes
    @Id
    @Getter @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Getter @Setter
    private String name;
    @Getter @Setter
    private Integer age;
    @Getter @Setter
    private String mail;

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "id_affiliate")
    @JsonIgnore
    private List<Appointment> appointments;
}
