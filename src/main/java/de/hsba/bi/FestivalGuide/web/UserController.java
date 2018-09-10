package de.hsba.bi.FestivalGuide.web;

import de.hsba.bi.FestivalGuide.user.User;
import de.hsba.bi.FestivalGuide.user.UserService;
import de.hsba.bi.FestivalGuide.web.form.UserForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class UserController {

    private final UserService userService;
    private final FormAssembler formAssembler;

    public UserController(UserService userService, FormAssembler formAssembler) {
        this.userService = userService;
        this.formAssembler = formAssembler;
    }

    //Aufruf der Registrierungsseite und Bereitstellung eines Registrierungsformulares
    @GetMapping("/registration")
    public String registration(Model model){
        model.addAttribute("userForm", new UserForm());
        return "registration";
    }

    //Neuen User erstellen
    @PostMapping("/registration")
    public String createUser(@ModelAttribute("userForm") @Valid UserForm userForm, BindingResult userBinding, Model model) {
        if (userBinding.hasErrors()) {
            return "registration";
        }
        if (userService.existsUsername(userForm.getName())) {
            model.addAttribute("usernameExists","Der gewählte Username existiert bereits!");
            return "registration";
        } else {
            userService.createUser(formAssembler.update(new User(), userForm));
            return "redirect:/";
        }
    }
}
