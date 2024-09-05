package br.com.lemes.VLbank.service;

import br.com.lemes.VLbank.enums.account.AccountType;
import br.com.lemes.VLbank.exceptions.AccountNotFoundException;
import br.com.lemes.VLbank.exceptions.InvalidArgumentForAccountTypeException;
import br.com.lemes.VLbank.model.user.User;
import br.com.lemes.VLbank.record.AccountDTO;
import br.com.lemes.VLbank.record.UserDTO;
import br.com.lemes.VLbank.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountService accountService;

    private User convertToEntity(UserDTO data) {
        checkIfAccountPresent(data);
        checkIfAccountTypeIsValid(data);
        List<AccountDTO> accountListDTO = data.accounts().stream().toList();
        var accountList = accountService.createAccountFromDTO(accountListDTO);
        var user = new User(data, accountList);
        userRepository.save(user);
        accountService.createAccount(accountList, user);
        return user;
    }

    private void checkIfAccountTypeIsValid(UserDTO data) {
        Set<AccountType> validAccountTypes = EnumSet.allOf(AccountType.class);

        boolean allTypesValid = data.accounts().stream()
                .map(AccountDTO::getAccountType)
                .allMatch(validAccountTypes::contains);

        if (!allTypesValid) {
            throw new InvalidArgumentForAccountTypeException("Um ou mais tipos de conta são inválidos.");
        }
    }

    private void checkIfAccountPresent(UserDTO data) {
        data.accounts()
                .forEach(accountDTO -> {
                    boolean existAccount = accountService.doesAccountExist(accountDTO.accountNumber());
                    if(!existAccount) {
                        throw new AccountNotFoundException("Account not found for this number!");
                    }
                });
    }
}
