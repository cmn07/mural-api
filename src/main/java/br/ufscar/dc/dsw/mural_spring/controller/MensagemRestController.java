package br.ufscar.dc.dsw.mural_spring.controller;

import br.ufscar.dc.dsw.mural_spring.dto.CreateMessageRequest;
import br.ufscar.dc.dsw.mural_spring.dto.ListedMessage;
import br.ufscar.dc.dsw.mural_spring.dto.UpdateMessageRequest;
import br.ufscar.dc.dsw.mural_spring.services.MessageService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/mensagens")
public class MensagemRestController {

    private final MessageService messageService;

    public MensagemRestController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping
    public ResponseEntity<List<ListedMessage>> listar() {
        return ResponseEntity.ok(messageService.listMessages());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ListedMessage> buscar(
            @PathVariable Long id) {

        return ResponseEntity.ok(messageService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ListedMessage> criar(
            @Valid @RequestBody CreateMessageRequest request,
            Authentication authentication) {

        ListedMessage created = messageService.save(
                request,
                authentication.getName()
        );

        URI location = URI.create(
                "/api/mensagens/" + created.id()
        );

        return ResponseEntity
                .created(location)
                .body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ListedMessage> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody UpdateMessageRequest request,
            Authentication authentication) {

        ListedMessage updated = messageService.update(
                id,
                request,
                authentication.getName()
        );

        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(
            @PathVariable Long id,
            Authentication authentication) {

        messageService.delete(
                id,
                authentication.getName()
        );

        return ResponseEntity.noContent().build();
    }
}