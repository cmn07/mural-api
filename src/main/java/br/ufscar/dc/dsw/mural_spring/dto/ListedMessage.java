package br.ufscar.dc.dsw.mural_spring.dto;

public record ListedMessage(
        Long id,
        String from,
        String to,
        String message,
        String timestamp
) {}

