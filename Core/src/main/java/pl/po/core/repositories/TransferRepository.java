package pl.po.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.po.core.domain.Transfer;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {
}
