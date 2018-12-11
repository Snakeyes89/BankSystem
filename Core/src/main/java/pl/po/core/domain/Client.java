package pl.po.core.domain;

import lombok.AllArgsConstructor;
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
public class Client {

    @Id
    @GeneratedValue
    private Long id;

    @Size(min=3, max=20)
    private String firstname;

    @Size(min=3, max=30)
    private String secondname;

    @OneToMany(mappedBy = "owner")
    private List<Account> accounts;

    public Client(Long id, @Size(min = 3, max = 20) String firstname, @Size(min = 3, max = 30) String secondname) {
        this.id = id;
        this.firstname = firstname;
        this.secondname = secondname;
    }
}
