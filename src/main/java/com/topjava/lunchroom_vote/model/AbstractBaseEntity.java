package com.topjava.lunchroom_vote.model;

import com.topjava.lunchroom_vote.HasId;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;

/**
 * @Alima-T 11/27/2022
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@AllArgsConstructor
@NoArgsConstructor
@Data
public abstract class AbstractBaseEntity implements HasId {
    public static final int START_SEQ = 100000;

    @Id
    @SequenceGenerator(name = "global_seq", sequenceName = "global_seq", allocationSize = 1, initialValue = START_SEQ)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_seq")
    protected Integer id;

    @Override
    public String toString() {
        return getClass().getSimpleName() + ":" + id;
    }

}