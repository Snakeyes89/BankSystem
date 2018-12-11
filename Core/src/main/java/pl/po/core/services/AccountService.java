package pl.po.core.services;

import pl.po.core.domain.Account;

public interface AccountService {

    Account get(Long id);

    Account add(Account account);

    Account update(Account account);

    void delete(Long id);

    boolean changeFundsAmount(Account account, double amount);

    boolean isChangeFundsAmountPossible(Account account, double amount);

}
