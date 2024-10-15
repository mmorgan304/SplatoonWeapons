package dev.melissamorgan.splatoonweapons.dao;

import dev.melissamorgan.splatoonweapons.entities.Users;

public interface UserDAO {
    Users findByName(String name);
}
