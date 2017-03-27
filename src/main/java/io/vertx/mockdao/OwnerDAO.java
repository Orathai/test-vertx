package io.vertx.mockdao;

import io.vertx.bean.Owner;

import java.util.ArrayList;
import java.util.List;

public class OwnerDAO {

    public Owner getByOwnerId(String id) {

        return new Owner(id, "https://psd2-api.openbankproject.com", "mai");
    }

    public List<Owner> getAllOwner() {

        ArrayList<Owner> ownerList = new ArrayList<>();
        String id = "owner";

        for (int i = 0; i <= 9; i++) {
            Owner owner = new Owner(id + i, "https://psd2-api.openbankproject.com", "mai");
            ownerList.add(owner);
        }
        return ownerList;
    }
}
