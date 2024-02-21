package cz.sedy.contacts.model

import java.util.*

data class ContactsUpdateCommand(
    val userId: UUID,
    val contacts: List<String>,
){

}