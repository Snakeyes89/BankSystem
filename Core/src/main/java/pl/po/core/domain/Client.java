package pl.po.core.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Client {

    @Id
    @GeneratedValue
    private Long id;

    @Size(min=3, max=20)
    private String firstName;

    @Size(min=3, max=30)
    private String surname;

    @OneToMany(mappedBy = "owner")
    private List<Account> accounts;

    public Client(@Size(min = 3, max = 20) String firstName, @Size(min = 3, max = 30) String surname) {
        this.firstName = firstName;
        this.surname = surname;
    }

    public Client(Long id, @Size(min = 3, max = 20) String firstName, @Size(min = 3, max = 30) String surname) {
        this.id = id;
        this.firstName = firstName;
        this.surname = surname;
    }
}
