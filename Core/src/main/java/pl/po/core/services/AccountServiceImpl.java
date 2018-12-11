package pl.po.core.services;

import org.springframework.stereotype.Service;
import pl.po.core.domain.Account;
import pl.po.core.repositories.AccountRepository;

import javax.persistence.PersistenceException;
import javax.transaction.Transactional;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account get(Long id) {
        return accountRepository.getOne(id);
    }

    @Override
    public Account add(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Account update(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public void delete(Long id) {
        accountRepository.deleteById(id);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
    public boolean changeFundsAmount(Account account, double amount) {
        final Account actualAccount = get(account.getId());
        isChangeFundsAmountPossible(actualAccount, amount);
        actualAccount.changeAmount(amount);

        final Account changedAccount = update(actualAccount);
        if (changedAccount.getAmount() != actualAccount.getAmount()) {
            throw new PersistenceException("Couldn't update account.");
        }
        return true;
    }

    @Override
    public boolean isChangeFundsAmountPossible(Account account, double amount) {
        final Account actualAccount = get(account.getId());
        if (!isNonZeroAmount(amount)) {
            throw new IllegalArgumentException("There is no point to change founds by zero.");
        }
        if (!areSufficientFunds(actualAccount, amount)) {
            throw new IllegalArgumentException("There are no sufficient funds.");
        }
        return true;
    }

    private boolean isNonZeroAmount(double amount) {
        return amount != 0;
    }

    private boolean areSufficientFunds(Account account, double amount) {
        return account.getAmount() + amount > 0;
    }
}
