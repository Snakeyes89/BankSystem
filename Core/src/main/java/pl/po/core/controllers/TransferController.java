package pl.po.core.controllers;

import org.springframework.web.bind.annotation.*;
import pl.po.core.services.TransferService;

@RestController
@RequestMapping("transfer/")
public class TransferController {

    private TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @RequestMapping(value = "/exec", method = RequestMethod.POST)
    public void execTransfer(@RequestParam Long sourceAccountId,
                             @RequestParam Long destinationAccountId,
                             @RequestParam double amount) {
        if (!transferService.isExecutable(sourceAccountId, destinationAccountId, amount)) {
            throw new IllegalArgumentException("Such a transfer cannot be executed.");
        }
        transferService.execute(sourceAccountId, destinationAccountId, amount);
    }

}
