package com.gopesh.transactionengine.controller;

import com.gopesh.transactionengine.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/transfer")
    public ResponseEntity<?> transfer(@RequestBody Map<String, Object> payload) {
        try {
            Long fromId = Long.valueOf(payload.get("fromId").toString());
            Long toId = Long.valueOf(payload.get("toId").toString());
            BigDecimal amount = new BigDecimal(payload.get("amount").toString());

            transactionService.transferFunds(fromId, toId, amount);
            return ResponseEntity.ok("Transfer successful");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Transfer failed: " + e.getMessage());
        }
    }
}
