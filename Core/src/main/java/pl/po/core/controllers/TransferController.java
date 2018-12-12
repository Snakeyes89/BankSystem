package pl.po.core.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.po.bslibs.dto.TransferDTO;
import pl.po.core.domain.Transfer;
import pl.po.core.services.TransferService;

@RestController
@RequestMapping("transfer/")
public class TransferController {

    private TransferService transferService;

    private ModelMapper modelMapper;

    public TransferController(TransferService transferService,
                              ModelMapper modelMapper) {
        this.transferService = transferService;
        this.modelMapper = modelMapper;
    }

    @RequestMapping(value = "/clientstats", method = RequestMethod.GET)
    public int executedTransfersByClientInLastMinutes(@RequestParam Long clientId, @RequestParam int minutes) {
        return transferService.executedTransfersByClientInLastMinutes(clientId, minutes);
    }

    @RequestMapping(value = "/exec", method = RequestMethod.POST)
    public ResponseEntity execTransfer(@RequestParam Long sourceAccountId,
                                       @RequestParam Long destinationAccountId,
                                       @RequestParam double amount) {
        if (!transferService.isExecutable(sourceAccountId, destinationAccountId, amount)) {
            throw new IllegalArgumentException("Such a transfer cannot be executed.");
        }
        final Transfer transfer = transferService.createTransfer(sourceAccountId, destinationAccountId, amount);
        if (transferService.detectFraud(convertEntityToDto(transfer))) {
            throw new IllegalArgumentException("Transfer parameters are suspicious.");
        }
        final boolean status = transferService.execute(sourceAccountId, destinationAccountId, amount);
        return ResponseEntity.status(HttpStatus.OK).body(status);
    }

    private TransferDTO convertEntityToDto(Transfer transfer) {
        return modelMapper.map(transfer, TransferDTO.class);
    }

    private Transfer convertDtoToEntity(TransferDTO transferDTO) {
        return modelMapper.map(transferDTO, Transfer.class);
    }
}
