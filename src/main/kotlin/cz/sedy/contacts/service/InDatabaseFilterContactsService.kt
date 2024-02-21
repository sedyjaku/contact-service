package cz.sedy.contacts.service

import cz.sedy.contacts.mapper.ContactFactory
import cz.sedy.contacts.model.Contact
import cz.sedy.contacts.model.ContactsUpdateCommand
import cz.sedy.contacts.repository.ContactsRepository
import org.springframework.stereotype.Service

@Service
class InDatabaseFilterContactsService(
    private val contactsRepository: ContactsRepository,
) : ContactsService {

    override fun updateContacts(
        updateCommand: ContactsUpdateCommand,
    ): List<Contact> =
        contactsRepository.saveAll(updateCommand.contacts
            .filter { contactsRepository.existsByUserIdAndNumber(updateCommand.userId, it) }
            .map { ContactFactory.create(updateCommand.userId, it) }
        ).toList()
    }
