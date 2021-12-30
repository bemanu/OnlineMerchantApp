package com.beb.OnlineMerchant;

import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.hateoas.EntityModel;

import java.util.List;
import java.util.UUID;
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

    @GetMapping("/transaction/id")
    EntityModel<Transaction> one(@PathVariable Long id) {
        Transaction transaction =repository.findById(id).orElseThrow(() -> new TransactionNotFoundException(id));
        return assembler.toModel(transaction);
    }

    @GetMapping("/transactions")
    CollectionModel<EntityModel<Transaction>> all(){
        List<EntityModel<Transaction>> transaction = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(transaction,
                linkTo(methodOn(TransactionController.class).all()).withSelfRel());
    }
}
