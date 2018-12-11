package pl.po.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.po.core.domain.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
}
