package pk.ok.pasir_orlowski_kacper.controller;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;
import jakarta.validation.Valid;
import pk.ok.pasir_orlowski_kacper.dto.GroupTransactionDTO;
import pk.ok.pasir_orlowski_kacper.model.User;
import pk.ok.pasir_orlowski_kacper.service.CurrentUserService;
import pk.ok.pasir_orlowski_kacper.service.GroupTransactionService;

@Controller
public class GroupTransactionGraphQLController {
    private final GroupTransactionService groupTransactionService;
    private final CurrentUserService currentUserService;

    public GroupTransactionGraphQLController(
            GroupTransactionService groupTransactionService,
            CurrentUserService currentUserService) {
        this.groupTransactionService = groupTransactionService;
        this.currentUserService = currentUserService;
    }

    @MutationMapping
    public Boolean addGroupTransaction(@Valid @Argument GroupTransactionDTO groupTransactionDTO) {

        User user = currentUserService.getCurrentUser();


        groupTransactionService.addGroupTransaction(groupTransactionDTO, user);


        return true;
    }
}