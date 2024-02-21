package cz.sedy.contacts.service

import cz.sedy.contacts.mapper.ContactFactory
import cz.sedy.contacts.model.Contact
import cz.sedy.contacts.model.ContactsUpdateCommand
import cz.sedy.contacts.repository.ContactsRepository
import org.openjdk.jmh.annotations.Benchmark
import org.springframework.stereotype.Service

@Service
class InMemoryFilterContactsService(
    private val contactsRepository: ContactsRepository,
) : ContactsService {

    override fun updateContacts(
        updateCommand: ContactsUpdateCommand,
    ): List<Contact> {
        val existingContacts = contactsRepository.findAllByUserId(updateCommand.userId).map { it.number }
        return contactsRepository.saveAll(updateCommand.contacts
            .filter { existingContacts.contains(it) }
            .map { ContactFactory.create(updateCommand.userId, it) }
        ).toList()
    }
}