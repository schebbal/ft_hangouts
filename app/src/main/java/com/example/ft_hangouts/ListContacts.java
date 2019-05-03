package com.example.ft_hangouts;

public class ListContacts {

    public  int    _id;
    private String _name ;
    private String _phone;
    private String _email;
    private String _street;
    private String _place;

    public ListContacts(int _id, String _name, String _phone, String _email, String _street, String _place) {
        this._id = _id;
        this._name = _name;
        this._phone = _phone;
        this._email = _email;
        this._street = _street;
        this._place = _place;
    }

    public int get_id() {
        return _id;
    }

    public String get_name() {
        return _name;
    }

    public String get_phone() {
        return _phone;
    }

    public void set_phone(String _phone) {
        this._phone = _phone;
    }

    public String get_email() {
        return _email;
    }

    public void set_email(String _email) {
        this._email = _email;
    }

    public String get_street() {
        return _street;
    }

    public void set_street(String _street) {
        this._street = _street;
    }

    public String get_place() {
        return _place;
    }

    public void set_place(String _place) {
        this._place = _place;
    }

    //@androidx.annotation.NonNull
    @Override
    public String toString() {
        final String s = _name + " " + _phone + " " + _email;
        return s;
    }
}