package com.gopesh.transactionengine.service;

import com.gopesh.transactionengine.model.Account;
import com.gopesh.transactionengine.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class TransactionService {

    private final AccountRepository accountRepository;

    public TransactionService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    // ACID Transaction: Rollback on any failure
    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
    public void transferFunds(Long fromId, Long toId, BigDecimal amount) {
        Account sender = accountRepository.findById(fromId)
                .orElseThrow(() -> new IllegalArgumentException("Sender not found"));
        Account receiver = accountRepository.findById(toId)
                .orElseThrow(() -> new IllegalArgumentException("Receiver not found"));

        if (sender.getBalance().compareTo(amount) < 0) {
            throw new IllegalStateException("Insufficient funds");
        }

        // Atomic Updates
        sender.setBalance(sender.getBalance().subtract(amount));
        receiver.setBalance(receiver.getBalance().add(amount));

        accountRepository.save(sender);
        accountRepository.save(receiver);
    }
}
