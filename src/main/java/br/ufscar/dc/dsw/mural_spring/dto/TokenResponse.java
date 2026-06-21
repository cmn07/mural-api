package br.ufscar.dc.dsw.mural_spring.dto;

public record TokenResponse(
        String token,
        String type
) {}