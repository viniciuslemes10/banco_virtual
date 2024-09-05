package br.com.lemes.VLbank.record.user;

import br.com.lemes.VLbank.record.account.AccountDTO;
import br.com.lemes.VLbank.record.account.AddressDTO;

import java.util.List;

public record UserDTO(
        String name,
        String email,
        String password,
        AddressDTO address,
        List<AccountDTO> accounts,
        String cpfOrCnpj
) {
}
