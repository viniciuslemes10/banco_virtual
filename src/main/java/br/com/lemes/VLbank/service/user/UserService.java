package br.com.lemes.VLbank.service.user;

import br.com.lemes.VLbank.enums.account.AccountType;
import br.com.lemes.VLbank.exceptions.account.AccountNotFoundException;
import br.com.lemes.VLbank.exceptions.account.InvalidArgumentForAccountTypeException;
import br.com.lemes.VLbank.model.user.User;
import br.com.lemes.VLbank.record.account.AccountDTO;
import br.com.lemes.VLbank.record.user.UserDTO;
import br.com.lemes.VLbank.repositories.user.UserRepository;
import br.com.lemes.VLbank.service.account.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountService accountService;

    private User convertToEntity(UserDTO data) {
        checkIfAccountTypeIsValid(data);
        checkIfAccountPresent(data);

        List<AccountDTO> accountListDTO = data.accounts().stream().toList();

        var user = new User(data);

        var userSaved = userRepository.save(user);

        var accountList = accountService.createAccountFromDTO(accountListDTO, userSaved);

        accountList.forEach(account -> account.setUser(userSaved));

        accountService.saveAll(accountList);

        return userSaved;
    }

    private void checkIfAccountTypeIsValid(UserDTO data) {
        Set<AccountType> validAccountTypes = EnumSet.allOf(AccountType.class);

        boolean allTypesValid = data.accounts().stream()
                .map(AccountDTO::getAccountType)
                .allMatch(validAccountTypes::contains);

        log.info("***************** {}", allTypesValid);

        if (!allTypesValid) {
            throw new InvalidArgumentForAccountTypeException("One or more account types are invalid.");
        }
    }

    public User createUser(UserDTO data) {
        return convertToEntity(data);
    }

    private void checkIfAccountPresent(UserDTO data) {
        data.accounts()
                .forEach(accountDTO -> {
                    boolean existAccount = accountService.doesAccountExist(accountDTO.accountNumber());
                    if(existAccount) {
                        throw new AccountNotFoundException("Existing account!");
                    }
                });
    }
}
