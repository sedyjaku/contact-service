package cz.sedy.contacts.repository

import cz.sedy.contacts.model.Contact
import org.springframework.data.repository.CrudRepository
import java.util.*


interface ContactsRepository : CrudRepository<Contact, UUID> {

    fun findAllByUserId(userId: UUID): Set<Contact>
    fun existsByUserIdAndNumber(userId: UUID, number: String): Boolean
}