package com.arenabooking.arena.Controller;

import com.arenabooking.arena.Model.Arena;
import com.arenabooking.arena.Repository.ArenaRepository;
import com.arenabooking.arena.Service.ArenaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
public class ArenaController {

    @Autowired
    private JavaMailSender emailSender;
    @Autowired
    private ArenaRepository arenaRepository;

    @Autowired
    ArenaService arenaService;

    @RequestMapping(value = {"/view"}, method = RequestMethod.GET)
    public String showList(Model model) {
        System.out.println("I am inside the view controller");
        List<Arena> viewclient = arenaService.listAll();
        model.addAttribute("viewclient", viewclient);
        return "admin/view-client";
    }

    @GetMapping("/query")
    public String showQueries(Model model) {
        List<Arena> viewclient = arenaRepository.findAll();
        model.addAttribute("viewclient", viewclient);
        return "admin/client-queries-reply";
    }

    @PostMapping("/sendEmail")
    public String sendEmail(@RequestParam String email, @RequestParam String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Meeting Request");
        mailMessage.setText(message);
        emailSender.send(mailMessage);
        return "admin/dashboard";
    }

    @GetMapping("/edit/{id}")
    public String editArena(@PathVariable("id") Long id, Model model) {
        System.out.println("im inside edit controller");
        Arena viewClient= arenaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid booking Id:" + id));
        model.addAttribute("viewClient", viewClient);
        return "admin/update";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable(value = "id") long id, @Valid Arena viewClient, BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()) {
            viewClient.setId(id);
            return "admin/dashboard";
        }
       arenaRepository.save(viewClient);
        return "redirect:/view";
    }


    @RequestMapping(value = "/image/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public ResponseEntity<byte[]> getImage(@PathVariable("id") Long id) {
        Optional<Arena> booking = arenaService.findClientById(id);
        byte[] imageBytes = booking.get().getProfilePhoto();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.setContentLength(imageBytes.length);
        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }


    @RequestMapping(value = {"/client"}, method = RequestMethod.GET)
    public String showForm(Model model) {
        model.addAttribute("booking", new Arena());
        System.out.println("I am inside client form");
        return "booking";
    }



    @PostMapping("/clientform")
    public String submitForm(@ModelAttribute("booking") @Valid Arena booking,
                             BindingResult bindingResult,
                             @RequestParam(value = "file", required = false) MultipartFile profilePhoto) throws IOException {
        if (bindingResult.hasErrors()) {
            System.out.println("Binding result has errors");
        }

        if (!profilePhoto.isEmpty()) {
            String contentType = profilePhoto.getContentType();
            if (contentType.equals("image/jpeg") || contentType.equals("image/png")) {
                byte[] imageBytes = profilePhoto.getBytes();
                booking.setProfilePhoto(imageBytes);
            } else {
                bindingResult.rejectValue("profilePhoto", "error.profilePhoto", "Invalid file type");
                return "homepage";
            }
        }

        arenaRepository.save(booking);

        return "homepage";
    }


    @GetMapping("/delete/{id}")
    public String deleteClient(@PathVariable("id") Long id) {
       Arena client = arenaService.findClientById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid client Id:" + id));

        arenaRepository.delete(client);

        return "redirect:/view";
    }



}



