package br.ufscar.dc.dsw.mural_spring.controller;

import br.ufscar.dc.dsw.mural_spring.dto.SendMessageForm;
import br.ufscar.dc.dsw.mural_spring.repositories.Message;
import br.ufscar.dc.dsw.mural_spring.repositories.MessageRepository;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PostarController {

    private static final Logger logger = LoggerFactory.getLogger(PostarController.class);

    private final MessageRepository messageRepository;

    public PostarController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @GetMapping("/postar")
    public String get(Model model) {
        logger.info("Get /postar");
        model.addAttribute("sendMessageForm", new SendMessageForm());
        return "postar";
    }

    @PostMapping("/postar")
    public String post(@Valid @ModelAttribute SendMessageForm sendMessageForm,
                       BindingResult bindingResult,
                       RedirectAttributes redirectAttributes) {
        //Binding Result -> relacionado a validação
        logger.info("Post /postar - {}", sendMessageForm);

        if(bindingResult.hasErrors()) {
            return "postar";
        }

        var message = new Message();
        message.setFrom(sendMessageForm.getFrom());
        message.setTo(sendMessageForm.getTo());
        message.setMessage(sendMessageForm.getMessage());
        messageRepository.save(message);

        //model.addAttribute("sendMessageForm", new SendMessageForm());
        redirectAttributes.addFlashAttribute("success", "Mensagem enviada com sucesso");
        return "redirect:/mensagens";
    }

}
