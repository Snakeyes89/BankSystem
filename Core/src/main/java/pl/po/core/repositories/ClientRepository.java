package pl.po.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.po.core.domain.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
}
