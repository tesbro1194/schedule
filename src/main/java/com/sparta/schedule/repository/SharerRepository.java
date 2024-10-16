package com.sparta.schedule.repository;

import com.sparta.schedule.entity.Sharer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SharerRepository extends JpaRepository<Sharer, Long> {
}
