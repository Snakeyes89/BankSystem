package pl.po.core.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Transfer {

    @Id
    @GeneratedValue
    private Long id;

    private double amount;

    @OneToOne
    private Account source;

    @OneToOne
    private Account destination;

    private Date date;

    public Transfer(double amount, Account source, Account destination, Date date) {
        this.amount = amount;
        this.source = source;
        this.destination = destination;
        this.date = date;
    }
}