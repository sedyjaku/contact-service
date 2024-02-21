package cz.sedy.contacts.mapper

import cz.sedy.contacts.model.ContactRequest
import cz.sedy.contacts.model.ContactsUpdateCommand
import java.util.*

object ContactsUpdateCommandMapper {

    fun createFromRequests(userId: UUID, contacts: List<ContactRequest>) =
        ContactsUpdateCommand(
            contacts = contacts.map { it.number },
            userId = userId,
        )
}