package com.dnr.contactconnect;


import com.dnr.contactconnect.Activity.AddContactActivity;
import com.dnr.contactconnect.Activity.ContactDetailsActivity;
import com.dnr.contactconnect.Activity.ContactListActivity;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = {
        ServiceModule.class
})
public interface RootComponent {
    void inject(ContactListActivity contactListActivity);
    void inject(ContactDetailsActivity contactDetailsActivity);
    void inject(AddContactActivity addContactActivity);
}

