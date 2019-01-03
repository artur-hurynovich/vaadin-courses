package com.gpsolutions.vaadincourses.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Email {

    private String name;

    private String text;

    private List<String> recipients;

}
