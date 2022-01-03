package com.beb.OnlineMerchant;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
public class TransactionController {

    private final TransactionRepository repository;
    private final TransactionAssembler assembler;

    public TransactionController(TransactionRepository repository, TransactionAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/transaction/{id}")
    EntityModel<Transaction> one(@PathVariable Long id) {
        Transaction transaction = repository.findById(id).orElseThrow(() -> new TransactionNotFoundException(id));
        return assembler.toModel(transaction);
    }

    @GetMapping("/transactions")
    CollectionModel<EntityModel<Transaction>> all() {
        List<EntityModel<Transaction>> transaction = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(transaction,
                linkTo(methodOn(TransactionController.class).all()).withSelfRel());
    }

    @PutMapping("transaction/{id}")
    ResponseEntity<?> updateTransaction(Transaction newTransaction, @PathVariable Long id) {

        Transaction updatedTransaction = repository.findById(id)
                .map(transaction -> {
                    transaction.setTransactionDate(newTransaction.getTransactionDate());
                    transaction.setTransactionStatus(newTransaction.getTransactionStatus());
                    transaction.setTransactionCurrency(newTransaction.getTransactionCurrency());
                    transaction.setAmount(newTransaction.getAmount());
                    transaction.setDescription(newTransaction.getDescription());
                    return repository.save(transaction);
                })
                .orElseThrow(() ->
                    new TransactionNotFoundException(id)
                );

                EntityModel<Transaction> entityModel = assembler.toModel(updatedTransaction);
                return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                        .body(entityModel);

    }


    @DeleteMapping("/transaction/{id}")
    ResponseEntity<?> deleteTransaction(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    private Transaction buildTransaction(Transaction transactionTO) {
        Transaction newCreatedTransaction = new Transaction();
        newCreatedTransaction.setTransactionDate(transactionTO.getTransactionDate());
        newCreatedTransaction.setTransactionStatus(transactionTO.getTransactionStatus());
        newCreatedTransaction.setTransactionCurrency(transactionTO.getTransactionCurrency());
        newCreatedTransaction.setAmount(transactionTO.getAmount());
        newCreatedTransaction.setDescription(transactionTO.getDescription());
        return newCreatedTransaction;
    }

}
