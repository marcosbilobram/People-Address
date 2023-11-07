package br.com.fiap.epictask.address;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.fiap.epictask.user.User;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/address")
public class AddressController {

    @Autowired
    AddressService service;

    @Autowired
    MessageSource message;

    @GetMapping
    public String index(Model model, @AuthenticationPrincipal OAuth2User user){
        model.addAttribute("avatar_url", user.getAttribute("avatar_url"));
        model.addAttribute("username", user.getAttribute("name"));
        model.addAttribute("addresses", service.findAll());
        return "address/index";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirect){
        service.delete(id);
        redirect.addFlashAttribute("success", message.getMessage("task.delete.success", null, LocaleContextHolder.getLocale()));
        return "redirect:/address";
    }

    @GetMapping("new")
    public String form(Address address, Model model, @AuthenticationPrincipal OAuth2User user){
         model.addAttribute("avatar_url", user.getAttribute("avatar_url"));
        model.addAttribute("username", user.getAttribute("name"));
        return "address/form";
    }

    @PostMapping
    public String create(@Valid Address address, BindingResult result , RedirectAttributes redirect){
        if (result.hasErrors()) return "address/form";
        service.create(address);
        redirect.addFlashAttribute("success", "Endereço cadastrado com sucesso");
        return "redirect:/task";
    }

    @GetMapping("catch/{id}")
    public String catchAddress(@PathVariable Long id, @AuthenticationPrincipal OAuth2User user){
        service.catchAddress(id, User.convert(user));
        return "redirect:/address";
    }

    @GetMapping("drop/{id}")
    public String dropAddress(@PathVariable Long id, @AuthenticationPrincipal OAuth2User user){
        service.dropAddress(id, User.convert(user));
        return "redirect:/address";
    }
    
}
