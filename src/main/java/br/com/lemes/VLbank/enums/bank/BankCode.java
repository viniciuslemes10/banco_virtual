package br.com.lemes.VLbank.enums.bank;

import lombok.Getter;

@Getter
public enum BankCode {
    ITAU("Itaú", "341"),
    BRADESCO("Bradesco", "237"),
    SANTANDER("Santander", "033"),
    NUBANK("Nubank", "260"),
    INTER("Inter", "077"),
    SICREDI("Sicredi", "748");

    private final String name;
    private final String code;

    BankCode(String name, String code) {
        this.name = name;
        this.code = code;
    }

}
