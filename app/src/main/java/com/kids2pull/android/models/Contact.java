package com.kids2pull.android.models;

import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Data;

import java.io.Serializable;
import java.util.ArrayList;

public class Contact implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -4140906895818969092L;

    public static Contact parse(Cursor c) {
        Contact contact = new Contact();
        contact.setId(c.getLong(c.getColumnIndex(Data.CONTACT_ID)));
        contact.setName(c.getString(c.getColumnIndex(Data.DISPLAY_NAME)));

        String photoId = c.getString(c.getColumnIndex(Data.PHOTO_ID));
        contact.setImage(photoId != null ? getContactPhotoUri(contact.getId()) : null);

        String data1 = c.getString(c.getColumnIndex(Data.DATA1));
        if(data1 != null) {
            contact.getContacts().add(new SelectableItem(data1, false));
        }
        return contact;
    }


    public static Uri getContactPhotoUri(long contactId) {
        return ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactId)
                .buildUpon().appendPath(ContactsContract.Contacts.Photo.CONTENT_DIRECTORY).build();
    }


    private long id;
    private long externalId;
    private String name;
    private ArrayList<SelectableItem> contacts;
    private String image;
    private boolean isOpened;


    public Contact() {
        setContacts(new ArrayList<SelectableItem>());
    }

    public Contact(Contact contact) {
        this();
        if(contact != null) {
            setId(contact.getId());
            setName(contact.getName());
            setExternalId(contact.getExternalId());
            setImage(contact.getImage());
            setOpened(contact.isOpened());
            contacts.addAll(contact.getContacts());
        }
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Uri getImage() {
        return image != null ? Uri.parse(image) : null;
    }

    public void setImage(Uri image) {
        this.image = image != null ? image.toString() : null;
    }


    public boolean isOpened() {
        return isOpened;
    }


    public void setOpened(boolean isOpened) {
        this.isOpened = isOpened;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Contact && ((Contact) o).getId() == id;
    }


    public ArrayList<SelectableItem> getContacts() {
        return contacts;
    }


    public void setContacts(ArrayList<SelectableItem> contacts) {
        this.contacts = contacts;
    }

    public boolean hasSelectedContacts() {
        for (SelectableItem contactItem : getContacts()) {
            if (contactItem.isSelected()) {
                return true;
            }
        }

        return false;
    }

    public void toggleOpened() {
        isOpened = !isOpened;
    }

    public long getExternalId() {
        return externalId;
    }

    public void setExternalId(long externalId) {
        this.externalId = externalId;
    }
}
