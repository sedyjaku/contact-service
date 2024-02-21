package cz.sedy.contacts.mapper

import cz.sedy.contacts.model.Contact
import java.util.UUID

object ContactFactory {

    fun create(
        userId: UUID,
        number: String,
        id: UUID = UUID.randomUUID()
    ) = Contact(
        userId = userId,
        number = number,
    )
}