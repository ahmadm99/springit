package com.ahmad.springit.repository;

import com.ahmad.springit.domain.Link;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkRepository extends JpaRepository<Link,Long> {
}
