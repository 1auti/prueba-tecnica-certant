package com.lautaro_cenizo.common;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass //Indica que esta clase no se mapea directamente a una tabla en la base de datos, pero sus propiedades serán heredadas por las subclases que sí sean entidades.
@EntityListeners(AuditingEntityListener.class) // Activa la funcionalidad de auditoría en esta clase, utilizando el listener AuditingEntityListener para gestionar los valores de las propiedades de auditoría como createdDate o lastModifiedBy.
@SuperBuilder
public class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    @Column(insertable = false)
    private LocalDateTime lastModifiedDate;

    @CreatedBy
    @Column(nullable = false, updatable = false)
    private Integer createdBy; // ID del usuario que creó la entidad

    @LastModifiedBy
    @Column(insertable = false)
    private Integer lastModifiedBy; // ID del usuario que modificó la entidad por última vez
}
