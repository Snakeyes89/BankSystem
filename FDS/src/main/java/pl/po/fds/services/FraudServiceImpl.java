package pl.po.fds.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.po.bslibs.dto.TransferDTO;

import java.util.Currency;
import java.util.Optional;

@Service
public class FraudServiceImpl implements FraudService {

    @Value("${core.service.address}")
    private String coreServiceAddress;

    @Value("${suspicious.transfers.minutes}")
    private int suspiciousTransfersMinutes;

    @Value("${suspicious.transfers.amount}")
    private int suspiciousTransfersAmount;

    @Override
    public boolean isSuspicious(TransferDTO transferDTO) {
        return (isSuspiciousAmount(transferDTO.getAmount()) ||
                isSuspiciousCurrency(transferDTO.getSource().getCurrency()) ||
                isSuspiciousFrequency(transferDTO.getSource().getOwner().getId()));
    }

    private boolean isSuspiciousAmount(double amount) {
        return amount > 10000;
    }

    private boolean isSuspiciousCurrency(Currency currency) {
        // Nigerian currency
        final Currency naira = Currency.getInstance("NGN");
        return currency.getCurrencyCode().equals(naira.getCurrencyCode());
    }

    private boolean isSuspiciousFrequency(Long clientId) {
        final RestTemplate restTemplate = new RestTemplate();
        final String url = coreServiceAddress + "transfer/clientstats?clientId=" + clientId
                + "&minutes=" + suspiciousTransfersMinutes;
        final Optional<Integer> num = Optional.ofNullable(restTemplate.getForObject(url, Integer.class));
        if (!num.isPresent()) {
            return true;
        }
        return num.get() > suspiciousTransfersAmount;
    }
}
