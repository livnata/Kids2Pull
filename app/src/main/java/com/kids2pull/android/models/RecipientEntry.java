package com.kids2pull.android.models;

import java.util.Random;

public class RecipientEntry extends Contact {


    public RecipientEntry() {
        super();
        int randomId = new Random().nextInt();
        setId(randomId > 0 ? -randomId : randomId);
    }

    public RecipientEntry(Contact contact) {
        super(contact);
    }
}
