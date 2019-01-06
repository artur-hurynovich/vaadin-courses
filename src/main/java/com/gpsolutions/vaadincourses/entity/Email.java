package com.gpsolutions.vaadincourses.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Repository
public class Email {

    private String name;

    private String text;

    private List<String> recipients;

    private LocalDate date;

}
