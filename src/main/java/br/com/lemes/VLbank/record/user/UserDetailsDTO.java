package br.com.lemes.VLbank.record.user;

import br.com.lemes.VLbank.model.user.User;
import br.com.lemes.VLbank.record.account.AccountDTO;
import br.com.lemes.VLbank.record.address.AddressDTO;

import java.util.Collections;
import java.util.List;

public record UserDetailsDTO(
        String name,
        String email,
        String password,
        AddressDTO address,
        List<AccountDTO> accounts,
        String cpfOrCnpj
) {
    public UserDetailsDTO(User user) {
        this(user.getName(), user.getEmail(), user.getPassword(),
                new AddressDTO(user.getAddress()),
                user.getAccount() == null ? Collections.emptyList() :
                        user.getAccount().stream().map(AccountDTO::new).toList(),
                user.getCpfOrCnpj());
    }
}
