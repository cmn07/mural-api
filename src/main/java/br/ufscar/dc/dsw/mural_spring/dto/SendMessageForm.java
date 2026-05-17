package br.ufscar.dc.dsw.mural_spring.dto;

import jakarta.validation.constraints.NotBlank;

public class SendMessageForm {
    @NotBlank(message = "{campo.from.obrigatorio}")
    private String from;
    @NotBlank(message = "{campo.to.obrigatorio}")
    private String to;
    @NotBlank(message = "{campo.message.obrigatorio}")
    private String message;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return String.format("SendMessageForm[from='%s', to='%s', message='%s']", from, to, message);
    }
}
