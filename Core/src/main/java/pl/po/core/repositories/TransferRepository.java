package pl.po.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.po.core.domain.Transfer;

import java.util.Date;
import java.util.List;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {

    /**
     * Finds all transfers that are executed by one specific client
     * within time from now to provided date in the past.
     * @param id clients id
     * @param date date in the past
     * @return list of found transfers
     */
    List<Transfer> findBySourceOwnerIdAndDateGreaterThan(Long id, Date date);
}
