package com.gpsolutions.vaadincourses.repository;

import com.gpsolutions.vaadincourses.entity.EmailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends JpaRepository<EmailEntity, Long> {
    EmailEntity findById(final long id);
}
