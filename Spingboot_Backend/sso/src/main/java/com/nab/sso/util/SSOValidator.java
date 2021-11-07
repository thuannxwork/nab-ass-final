package com.nab.sso.util;

import com.nab.sso.model.Address;
import com.nab.sso.model.User;

public class SSOValidator {

    public static boolean isUserEmpty(User user) {
        if (user.getAge() == 0) {
            return true;
        }
        if (user.getPassword() == null || user.getPassword() == "") {
            return true;
        }
        if (user.getEmail() == null || user.getEmail() == "") {
            return true;
        }
        if (user.getUsername() == null || user.getUsername() == "") {
            return true;
        }
        return false;
    }

    public static boolean isAddressEmpty(Address address) {
        if (address.getAddress() == null || address.getAddress() == "") {
            return true;
        }
        if (address.getCity() == null || address.getCity() == "") {
            return true;
        }
        if (address.getState() == null || address.getState() == "") {
            return true;
        }
        if (address.getCountry() == null || address.getCountry() == "") {
            return true;
        }
        if (address.getPhoneNumber() == null || address.getPhoneNumber() == "") {
            return true;
        }
        if (address.getZipcode() == 0) {
            return true;
        }
        return false;
    }

}
