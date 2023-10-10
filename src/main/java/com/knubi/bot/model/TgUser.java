package com.knubi.bot.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tg_user")
public class TgUser {

    @Column(name = "id", unique = true, nullable = false)
    @NotNull
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "tg_user_seq", sequenceName = "tg_user_seq", initialValue = 1)
    protected Long id;


    @Column(name = "name", nullable = false)
    @NotBlank
    private String name;

    //todo: other fields
}
