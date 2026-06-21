package br.ufscar.dc.dsw.mural_spring.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateMessageRequest(

        @NotBlank(message = "O destinatário é obrigatório")
        @Size(max = 100, message = "O destinatário deve ter no máximo 100 caracteres")
        String to,

        @NotBlank(message = "A mensagem é obrigatória")
        @Size(max = 1000, message = "A mensagem deve ter no máximo 1000 caracteres")
        String message

) {}