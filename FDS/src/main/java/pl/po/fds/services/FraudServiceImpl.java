package pl.po.fds.services;

import org.springframework.stereotype.Service;
import pl.po.bslibs.dto.TransferDTO;

import java.util.Currency;

@Service
public class FraudServiceImpl implements FraudService {

    @Override
    public boolean isSuspicious(TransferDTO transferDTO) {
        return (isSuspiciousAmount(transferDTO.getAmount()) ||
                isSuspiciousCurrency(transferDTO.getSource().getCurrency()));
    }

    private boolean isSuspiciousAmount(double amount) {
        return amount > 10000;
    }

    private boolean isSuspiciousCurrency(Currency currency) {
        // Nigerian currency
        final Currency naira = Currency.getInstance("NGN");
        return currency.getCurrencyCode().equals(naira.getCurrencyCode());
    }
}
