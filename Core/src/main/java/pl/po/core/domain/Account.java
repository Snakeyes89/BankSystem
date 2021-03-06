package pl.po.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Currency;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Account {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Client owner;

    private double amount;

    @Setter
    private Currency currency;

    public Account(Client owner, double amount, Currency currency) {
        this.owner = owner;
        this.amount = amount;
        this.currency = currency;
    }

    public double changeAmount(double amount) {
        return this.amount += amount;
    }
}
