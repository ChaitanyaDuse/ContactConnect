package com.dnr.contactconnect.model;


import android.os.Parcel;
import android.os.Parcelable;

public class Contact implements Comparable<Contact>, Parcelable {
    public static  final String IS_FAVORITE = "0_favorite";
    private int id;
    private String first_name;
    private String last_name;
    private String email;
    private String phone_number;
    private String profile_pic;
    private boolean favorite;
    private String created_at;
    private String updated_at;


    public Contact() {

    }

    public Contact(String first_name, String last_name, String number) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.phone_number = number;

    }


    public Contact(String first_name, String last_name, String number,boolean isFavorite) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.phone_number = number;
        this.favorite = isFavorite;

    }

    /**
     * @return The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return The firstName
     */
    public String getFirstName() {
        return first_name;
    }

    /**
     * @param firstName The first_name
     */
    public void setFirstName(String firstName) {
        this.first_name = firstName;
    }

    /**
     * @return The lastName
     */
    public String getLastName() {
        return last_name;
    }

    /**
     * @param lastName The last_name
     */
    public void setLastName(String lastName) {
        this.last_name = lastName;
    }

    /**
     * @return The email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return The phoneNumber
     */
    public String getPhoneNumber() {
        return phone_number;
    }

    /**
     * @param phoneNumber The phone_number
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phone_number = phoneNumber;
    }

    /**
     * @return The profilePic
     */
    public String getProfilePic() {
        return profile_pic;
    }

    /**
     * @param profilePic The profile_pic
     */
    public void setProfilePic(String profilePic) {
        this.profile_pic = profilePic;
    }

    /**
     * @return The favorite
     */
    public boolean getFavorite() {
        return favorite;
    }

    /**
     * @param favorite The favorite
     */
    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    /**
     * @return The createdAt
     */
    public String getCreatedAt() {
        return created_at;
    }

    /**
     * @param createdAt The created_at
     */
    public void setCreatedAt(String createdAt) {
        this.created_at = createdAt;
    }

    /**
     * @return The updatedAt
     */
    public String getUpdatedAt() {
        return updated_at;
    }

    /**
     * @param updatedAt The updated_at
     */
    public void setUpdatedAt(String updatedAt) {
        this.updated_at = updatedAt;
    }

    @Override
    public int compareTo(Contact another) {
        return this.first_name.compareToIgnoreCase(another.first_name);
    }

    protected Contact(Parcel in) {
        id = in.readInt();
        first_name = in.readString();
        last_name = in.readString();
        email = in.readString();
        phone_number = in.readString();
        profile_pic = in.readString();
        favorite = in.readByte() != 0x00;
        created_at = in.readString();
        updated_at = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(first_name);
        dest.writeString(last_name);
        dest.writeString(email);
        dest.writeString(phone_number);
        dest.writeString(profile_pic);
        dest.writeByte((byte) (favorite ? 0x01 : 0x00));
        dest.writeString(created_at);
        dest.writeString(updated_at);
    }


    public static final Parcelable.Creator<Contact> CREATOR = new Parcelable.Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };

    public String getFullName() {
        if (this.last_name != null && this.last_name.length() > 0) {
            return this.first_name + " " + this.last_name;
        } else
            return this.first_name;
    }
}