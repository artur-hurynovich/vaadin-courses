package com.gpsolutions.vaadincourses.entity;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class EmailGenerator {

    public List<Email> getEmailList() {
        final List<String> recipients = new ArrayList<>(3);
        recipients.add("Anton");
        recipients.add("Pavel");
        recipients.add("Vladimir");
        return new ArrayList<Email>(){
            {
                add(new Email("Artur", "What should I do next?",
                        Collections.singletonList("Maxim"), LocalDate.of(2019, 1, 2)));
                add(new Email("Maxim", "Keep on learning!",
                        Collections.singletonList("Artur"), LocalDate.of(2019, 1, 3)));
                add(new Email("Maxim", "All of you should keep on learning!",
                        recipients, LocalDate.of(2019, 1, 4)));
            }
        };
    }

}
