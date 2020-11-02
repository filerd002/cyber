package com.example.gateway.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Table(name = "REQUEST")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Request {

    public Request( String requestBody,String requestType) {
        this.requestType = requestType;
        this.requestBody = requestBody;
    }

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(name = "REQUEST_TYPE")
    private String requestType;
    @Column(name = "REQUEST_BODY")
    private String requestBody;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="event_id", nullable=false)
    private Event event;

}
