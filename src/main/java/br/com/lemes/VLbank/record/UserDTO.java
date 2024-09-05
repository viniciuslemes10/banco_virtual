package br.com.lemes.VLbank.record;

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
