package com.github.dperezcabrera.bank.architecture.auth.repositories;

import com.github.dperezcabrera.bank.architecture.auth.entities.Code;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface CodeRepository extends JpaRepository<Code, String> {

}
