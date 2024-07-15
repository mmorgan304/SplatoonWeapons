package dev.melissamorgan.splatoonweapons.dao;

import dev.melissamorgan.splatoonweapons.entities.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WeaponDAOImplementation implements WeaponDAO {

    @PersistenceContext
    private EntityManager entityManager;

    /**************************
     * Model population methods
     *************************/
    @Override
    public List<Subweapon> getAllSubweapons() {
        return entityManager.createQuery("from Subweapon", Subweapon.class).getResultList();
    }

    @Override
    public List<SpecialWeapon> getAllSpecialWeapons() {
        return entityManager.createQuery("from SpecialWeapon", SpecialWeapon.class).getResultList();
    }

    @Override
    public List<WeaponType> getAllWeaponTypes() {
        return entityManager.createQuery("from WeaponType", WeaponType.class).getResultList();
    }

    @Override
    public List<Weight> getAllWeights() {
        return entityManager.createQuery("from Weight", Weight.class).getResultList();
    }

    @Override
    public List<Weapon> getAllWeapons() {
        return entityManager.createQuery("from Weapon", Weapon.class).getResultList();
    }

    /*****************************
     * Object manipulation methods
     ****************************/
    @Override
    public void saveWeapon(Weapon newWeapon) {
        entityManager.persist(newWeapon);
    }

    /**************************
     * Object retrieval methods
     *************************/
    @Override
    public Subweapon getSubweaponById(Integer id) {
        return entityManager.find(Subweapon.class, id);
    }

    @Override
    public SpecialWeapon getSpecialWeaponById(Integer id) {
        return entityManager.find(SpecialWeapon.class, id);
    }

    @Override
    public WeaponType getWeaponTypeById(Integer id) {
        return entityManager.find(WeaponType.class, id);
    }

    @Override
    public Weight getWeightById(Integer id) {
        return entityManager.find(Weight.class, id);
    }

    @Override
    public Weapon getRandomWeapon() {
        List<Weapon> random = entityManager.createQuery("from Weapon order by rand() limit 1", Weapon.class).getResultList();
        return random.getFirst();
    }
}
