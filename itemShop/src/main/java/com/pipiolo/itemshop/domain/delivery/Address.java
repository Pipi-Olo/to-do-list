package com.pipiolo.itemshop.domain.delivery;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Getter @NoArgsConstructor @AllArgsConstructor
@Embeddable
public class Address {

    private String city;
    private String street;
    private String zipcode;

    @Override
    public String toString() {
        return this.getCity() + " " + this.getStreet() + " " + this.getZipcode();
    }
}
