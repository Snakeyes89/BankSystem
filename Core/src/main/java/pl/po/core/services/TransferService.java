package pl.po.core.services;

import pl.po.core.domain.Transfer;

public interface TransferService {

    Transfer get(Long id);

    Transfer add(Transfer transfer);

    Transfer update(Transfer transfer);

    void delete(Long id);

    /**
     * Creates new Transfer object based on provided parameters, validates and executes.
     * @param sourceAccountId
     * @param destinationAccountId
     * @param amount
     * @return true if success
     */
    boolean execute(Long sourceAccountId, Long destinationAccountId, double amount);

    /**
     * Checks how many times specific client has successfully executed transfer on provided period of time.
     * @param clientId
     * @param minutes number of minutes from now in the past
     * @return number of executed transfers
     */
    int executedTransfersByClientInLastMinutes(Long clientId, int minutes);

    /**
     * Checks if provided parameters allows to execute transfer.
     * @param sourceAccountId
     * @param destinationAccountId
     * @param amount
     * @return true if passed validation
     */
    boolean isExecutable(Long sourceAccountId, Long destinationAccountId, double amount);

    /**
     * Creates new Transfer object based on provided parameters.
     * @param sourceAccountId
     * @param destinationAccountId
     * @param amount
     * @return new Transfer object
     */
    Transfer createTransfer(Long sourceAccountId, Long destinationAccountId, double amount);
}
