package com.kids2pull.android.fragments;

import com.kids2pull.android.models.Contact;

import java.util.List;

public interface IInviteContacts {
    void onContactSelectionComplete(List<Contact> contacts);

    void openAppSettings();
}
