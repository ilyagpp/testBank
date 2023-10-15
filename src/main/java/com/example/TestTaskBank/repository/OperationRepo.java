package com.example.TestTaskBank.repository;

import com.example.TestTaskBank.model.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepo extends JpaRepository<Operation, Long> {

}
