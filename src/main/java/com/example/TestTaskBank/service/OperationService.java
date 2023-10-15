package com.example.TestTaskBank.service;

import com.example.TestTaskBank.exception.AppNotFoundException;
import com.example.TestTaskBank.model.Operation;
import com.example.TestTaskBank.repository.OperationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OperationService {

    @Autowired
    private OperationRepo operationRepo;


    public Operation findById(Long id){

        return operationRepo.findById(id)
                .orElseThrow(()-> {throw new AppNotFoundException("Operation not found");});
    }

    @Transactional
    public Operation save(Operation operation) {
        return operationRepo.save(operation);
    }
}
