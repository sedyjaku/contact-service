package cz.sedy.contacts.controller

import cz.sedy.contacts.model.ContactRequest
import cz.sedy.contacts.service.InMemoryFilterContactsService
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class ContactsController(
    private val contactsService: InMemoryFilterContactsService,
) {

    @PutMapping("/users/{userId}/contacts")
    fun updateContacts(
        @PathVariable userId: UUID,
        @RequestBody contacts: List<ContactRequest>){
        contactsService
    }
}