package com.lautaro_cenizo.entity.persona;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lautaro_cenizo.common.BaseEntity;
import com.lautaro_cenizo.entity.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "Personas")
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@SuperBuilder
@EntityListeners(AuditingEntityListener.class)
public class Persona extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String apellido;
    @Email
    @Column(unique = true ,nullable = false)
    private String email;
    @Column(unique = true,nullable = false)
    private String dni;
    @Column(unique = true)
    private String telefono;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnore
    private User user;

}
