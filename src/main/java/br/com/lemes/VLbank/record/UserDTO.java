package br.com.lemes.VLbank.record;

import java.time.LocalDateTime;
import java.util.List;

public record UserDTO(
        String name,
        String email,
        String password,
        AddressDTO address,
        List<AccountDTO> accounts,
        LocalDateTime createdDate,
        LocalDateTime updatedDate,
        String cpfOrCnpj,
        Boolean accountNotExpired,
        Boolean accountNotBlocked,
        Boolean credentialsNotExpired,
        Boolean enabled,
        String recoveryCode

) {
}
