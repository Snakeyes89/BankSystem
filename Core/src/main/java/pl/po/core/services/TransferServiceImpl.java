package pl.po.core.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.po.bslibs.dto.TransferDTO;
import pl.po.core.domain.Account;
import pl.po.core.domain.Transfer;
import pl.po.core.repositories.TransferRepository;

import javax.transaction.Transactional;
import java.util.Date;

@Service
public class TransferServiceImpl implements TransferService {

    @Value("${fds.service.address}")
    private String fdsServiceAddress;

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
        if (!isPositiveAmount(amount)) {
            throw new IllegalArgumentException("Transfer amount must be positive.");
        }
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

    @Override
    public boolean detectFraud(TransferDTO transferDTO) {
        final RestTemplate restTemplate = new RestTemplate();
        final HttpEntity<TransferDTO> request = new HttpEntity<>(transferDTO);
        final ResponseEntity<String> response = restTemplate.exchange(fdsServiceAddress + "fraud/detect",
                HttpMethod.POST, request, String.class);
        return Boolean.valueOf(response.getBody());
    }

    private boolean isPositiveAmount(double amount) {
        return amount > 0;
    }

    private boolean isSameSourceAndDestinationAccounts(Long sourceAccountId, Long destinationAccountId) {
        return sourceAccountId.equals(destinationAccountId);
    }

    private boolean isSameCurrency(Transfer transfer) {
        return transfer.getSource().getCurrency() == transfer.getDestination().getCurrency();
    }
}
