package dev.melissamorgan.splatoonweapons.dao;

import dev.melissamorgan.splatoonweapons.entities.Users;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAOImplementation implements UserDAO{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Users findByName(String name) {
        Query query = entityManager.createQuery("SELECT u FROM Users u WHERE u.name = :name");
        query.setParameter("name", name);
        return (Users) query.getSingleResult();
    }
}
