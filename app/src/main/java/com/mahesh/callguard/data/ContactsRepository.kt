package com.mahesh.callguard.data

import android.content.Context
import android.provider.ContactsContract
import com.mahesh.callguard.model.Contact
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object ContactsRepository {

    suspend fun loadContacts(context: Context): List<Contact> = withContext(Dispatchers.IO) {
        val contactList = mutableListOf<Contact>()
        val seenIds = mutableSetOf<String>()

        val cursor = context.contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            arrayOf(
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER
            ),
            null,
            null,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
        )

        cursor?.use {
            val idIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID)
            val nameIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
            val numberIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)

            while (it.moveToNext()) {
                val id = it.getString(idIndex)
                // One entry per contact — we take the first number the cursor returns for each id
                if (id !in seenIds) {
                    seenIds.add(id)
                    contactList.add(
                        Contact(
                            id = id,
                            name = it.getString(nameIndex),
                            phoneNumber = it.getString(numberIndex)
                        )
                    )
                }
            }
        }

        contactList
    }
}
