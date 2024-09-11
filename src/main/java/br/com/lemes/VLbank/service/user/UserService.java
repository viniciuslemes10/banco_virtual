package br.com.lemes.VLbank.service.user;

import br.com.lemes.VLbank.exceptions.account.AccountNotFoundException;
import br.com.lemes.VLbank.model.user.User;
import br.com.lemes.VLbank.record.account.AccountDTO;
import br.com.lemes.VLbank.record.user.UserDTO;
import br.com.lemes.VLbank.record.user.UserDetailsDTO;
import br.com.lemes.VLbank.repositories.user.UserRepository;
import br.com.lemes.VLbank.service.account.AccountService;
import br.com.lemes.VLbank.service.agency.AgencyService;
import br.com.lemes.VLbank.service.bank.BankService;
import br.com.lemes.VLbank.utils.DTOValidator;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private BankService bankService;

    @Autowired
    private AgencyService agencyService;

    @Transactional
    private User convertToEntity(UserDTO data) {
        checkIfAccountPresent(data);
        DTOValidator.validateUserDTO(data);

        List<AccountDTO> accountListDTO = data.accounts().stream().toList();

        var user = new User(data);

        checkIfBankAndAgencyAreValid(data.accounts());

        var userSaved = userRepository.save(user);

        var accountList = accountService.createAccountFromDTO(accountListDTO, userSaved);

        accountList.forEach(account -> account.setUser(userSaved));

        accountService.saveAll(accountList);

        return userSaved;
    }

    private void checkIfBankAndAgencyAreValid(List<AccountDTO> accounts) {
        accounts.forEach(account -> {
            bankService.findById(account.bankId());
            agencyService.findById(account.agencyId());
        });
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

    public List<UserDetailsDTO> findAll() {
        var users = userRepository.findAll();
        return users.stream().map(UserDetailsDTO::new).toList();
    }
}
