package com.example.fundoonotemicroservice.repository;

import com.example.fundoonotemicroservice.model.LabelModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LabelRepository extends JpaRepository<LabelModel,Long> {
}
