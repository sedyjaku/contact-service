package cz.sedy.contacts.service

import cz.sedy.contacts.model.Contact
import cz.sedy.contacts.model.ContactsUpdateCommand

interface ContactsService {
    fun updateContacts(updateCommand: ContactsUpdateCommand): List<Contact>
}