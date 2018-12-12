package pl.po.core.services;

import org.springframework.stereotype.Service;
import pl.po.core.domain.Account;
import pl.po.core.domain.Transfer;
import pl.po.core.repositories.TransferRepository;

import javax.transaction.Transactional;
import java.util.Date;

@Service
public class TransferServiceImpl implements TransferService {

    private TransferRepository transferRepository;

    private AccountService accountService;

    public TransferServiceImpl(TransferRepository transferRepository, AccountService accountService) {
        this.transferRepository = transferRepository;
        this.accountService = accountService;
    }

    @Override
    public Transfer get(Long id) {
        return transferRepository.getOne(id);
    }

    @Override
    public Transfer add(Transfer transfer) {
        return transferRepository.save(transfer);
    }

    @Override
    public Transfer update(Transfer transfer) {
        return transferRepository.save(transfer);
    }

    @Override
    public void delete(Long id) {
        transferRepository.deleteById(id);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public boolean execute(Long sourceAccountId, Long destinationAccountId, double amount) {
        final Transfer transfer = createTransfer(sourceAccountId, destinationAccountId, amount);
        isExecutable(sourceAccountId, destinationAccountId, amount);
        accountService.changeFundsAmount(transfer.getSource(), -transfer.getAmount());
        accountService.changeFundsAmount(transfer.getDestination(), transfer.getAmount());
        add(transfer);
        return true;
    }

    @Override
    public int executedTransfersByClientInLastMinutes(Long clientId, int minutes) {
        final Date date = new Date(System.currentTimeMillis() - (minutes * 60 * 1000));
        return transferRepository.findBySourceOwnerIdAndDateGreaterThan(clientId, date).size();
    }

    @Override
    public boolean isExecutable(Long sourceAccountId, Long destinationAccountId, double amount) {
        final Transfer transfer = createTransfer(sourceAccountId, destinationAccountId, amount);
        if (isSameSourceAndDestinationAccounts(sourceAccountId, destinationAccountId)) {
            throw new IllegalArgumentException("Source account and destination account cannot be the same.");
        }
        if (!isSameCurrency(transfer)) {
            throw new IllegalArgumentException("Currency of source account and destination account are different.");
        }
        accountService.isChangeFundsAmountPossible(transfer.getSource(), transfer.getAmount());
        return true;
    }

    @Override
    public Transfer createTransfer(Long sourceAccountId, Long destinationAccountId, double amount) {
        final Account source = accountService.get(sourceAccountId);
        final Account destination = accountService.get(destinationAccountId);
        return new Transfer(amount, source, destination, new Date());
    }

    private boolean isSameSourceAndDestinationAccounts(Long sourceAccountId, Long destinationAccountId) {
        return sourceAccountId.equals(destinationAccountId);
    }

    private boolean isSameCurrency(Transfer transfer) {
        return transfer.getSource().getCurrency() == transfer.getDestination().getCurrency();
    }
}
