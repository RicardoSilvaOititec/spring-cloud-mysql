package com.ricardo.springcloudmysql.controller;

import com.ricardo.springcloudmysql.model.Contact;
import com.ricardo.springcloudmysql.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/contacts"})
public class ContactController {

    @Autowired
    private ContactRepository contactRepository;

    public ContactController(ContactRepository contactRepository) {

        this.contactRepository = contactRepository;
    }


    @GetMapping
    public List<Contact> findAll(){
        return contactRepository.findAll();
    }

    @GetMapping(path = {"/{id}"})
    public ResponseEntity findById(@PathVariable Long id){
        return contactRepository.findById(id)
                .map((Contact contact) -> ResponseEntity.ok().body(contact))
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping
    public Contact create(@RequestBody Contact contact){
        return contactRepository.save(contact);
    }

    @PutMapping(value="/{id}")
    public ResponseEntity update(@PathVariable("id") long id,
                                 @RequestBody Contact contact) {
        return contactRepository.findById(id)
                .map(record -> {
                    record.setName(contact.getName());
                    record.setEmail(contact.getEmail());
                    record.setPhone(contact.getPhone());
                    Contact updated = (Contact) contactRepository.save(record);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(path ={"/{id}"})
    public ResponseEntity <?> delete(@PathVariable long id) {
        return contactRepository.findById(id)
                .map(record -> {
                    contactRepository.deleteById(id);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }

}
