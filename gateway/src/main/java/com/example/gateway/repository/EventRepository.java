package com.example.gateway.repository;

import com.example.gateway.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {


    List<Event>  findAllByIpAndDateStartBetweenOrderByFrequencyDesc(String ip, LocalDateTime from, LocalDateTime to);
    List<Event> findAllByIpAndStatus(String ip, String status);

}
