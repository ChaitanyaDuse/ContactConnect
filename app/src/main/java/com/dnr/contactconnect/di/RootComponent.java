package com.dnr.contactconnect.di;


import com.dnr.contactconnect.activity.AddContactActivity;
import com.dnr.contactconnect.activity.ContactDetailsActivity;
import com.dnr.contactconnect.activity.ContactListActivity;

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

