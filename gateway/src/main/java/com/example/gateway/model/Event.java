package com.example.gateway.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Table(name = "EVENT")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Event {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Column(name = "IP")
    private String ip;
    @Column(name = "REQUESTS")
    @ElementCollection
    @OneToMany(mappedBy="event",  cascade = CascadeType.ALL)
    private Set<Request> requests = new HashSet<> ( );
    @Column(name = "STATUS")
    private String status;
    @Column(name = "FREQUENCY")
    private Long frequency;
    @Column(name = "DATE_START")
    private LocalDateTime dateStart;
    @Column(name = "DATE_END")
    private LocalDateTime dateEnd;

   public void frequencyIncrement(){
        this.frequency++;
    }

}


