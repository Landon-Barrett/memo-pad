package edu.jsu.mcis.cs408.memopad;

import androidx.annotation.NonNull;

public class Contact {

    private int id;
    private final String name, address;

    public Contact(int id, String name, String address) {

        this.id = id;
        this.name = name;
        this.address = address;

    }

    public Contact(String name, String address) {

        this.name = name;
        this.address = address;

    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    @NonNull
    @Override
    public String toString() {

        StringBuilder s = new StringBuilder();
        s.append("Name: ").append(name).append("\n");
        s.append("Address: ").append(address).append("\n");
        return s.toString();

    }

}