package pl.po.core.services;

import pl.po.core.domain.Transfer;

public interface TransferService {

    Transfer get(Long id);

    Transfer add(Transfer transfer);

    Transfer update(Transfer transfer);

    void delete(Long id);

    boolean execute(Long sourceAccountId, Long destinationAccountId, double amount);

    boolean isExecutable(Long sourceAccountId, Long destinationAccountId, double amount);

}
