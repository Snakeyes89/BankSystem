package pl.po.core.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    @Id
    @GeneratedValue
    private Long id;

    private String firstname;

    private String secondname;

    @OneToMany(mappedBy = "owner")
    private List<Account> accounts;

    public Client(String firstname, String secondname, List<Account> accounts) {
        this.firstname = firstname;
        this.secondname = secondname;
        this.accounts = accounts;
    }
}
