package pl.po.fds.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.po.bslibs.dto.TransferDTO;
import pl.po.fds.services.FraudService;

@RestController
@RequestMapping("fraud/")
public class FraudController {

    private FraudService fraudService;

    public FraudController(FraudService fraudService) {
        this.fraudService = fraudService;
    }

    @RequestMapping(value = "/detect", method = RequestMethod.POST)
    public ResponseEntity fraudDetecting(@RequestBody TransferDTO transferDTO) {
        final boolean isSuspicious = fraudService.isSuspicious(transferDTO);
        return ResponseEntity.status(HttpStatus.OK).body(isSuspicious);
    }
}
