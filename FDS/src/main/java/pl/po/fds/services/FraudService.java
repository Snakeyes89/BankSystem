package pl.po.fds.services;

import pl.po.bslibs.dto.TransferDTO;

public interface FraudService {

    /**
     * Checks if provided transfer is suspicious in any of tested ways.
     * @param transferDTO
     * @return true if suspicious
     */
    boolean isSuspicious(TransferDTO transferDTO);
}
