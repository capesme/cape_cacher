package com.github.voxxin.cape_cacher.task.util;

import java.util.ArrayList;

public class UserObjectList {
    private final ArrayList<UserObject> userObjectArrayList = new ArrayList<>();

    public void add(UserObject userObject) {
        userObjectArrayList.add(userObject);
    }
    public void remove(UserObject userObject) {
        userObjectArrayList.remove(userObject);
    }
    public boolean contains(UserObject userObject) {
        for (UserObject obj : userObjectArrayList) {
            if (obj.compare(userObject)) return true;
        }
        return false;
    }

    public int size() {
        return userObjectArrayList.size();
    }
}
