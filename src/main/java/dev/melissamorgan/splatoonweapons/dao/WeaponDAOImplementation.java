package dev.melissamorgan.splatoonweapons.dao;

import dev.melissamorgan.splatoonweapons.entities.*;
import dev.melissamorgan.splatoonweapons.searchMethods.WeaponCategoryExclusionSearch;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.Collections;
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
    public Weapon getRandomWeapon(WeaponCategoryExclusionSearch exclusionSearch) {
        List<Weapon> weaponList;
        StringBuilder queryBuilder = getStringBuilder(exclusionSearch);
        TypedQuery<Weapon> query = entityManager.createQuery(queryBuilder.toString(), Weapon.class);
        if (exclusionSearch != null) {
            if (exclusionSearch.getWeaponTypeIds() != null && !exclusionSearch.getWeaponTypeIds().isEmpty()) {
                query.setParameter("weaponTypeIds", exclusionSearch.getWeaponTypeIds());
            }
            if (exclusionSearch.getSubweaponIds() != null && !exclusionSearch.getSubweaponIds().isEmpty()) {
                query.setParameter("subweaponIds", exclusionSearch.getSubweaponIds());
            }
            if (exclusionSearch.getSpecialWeaponIds() != null && !exclusionSearch.getSpecialWeaponIds().isEmpty()) {
                query.setParameter("specialWeaponIds", exclusionSearch.getSpecialWeaponIds());
            }
            if (exclusionSearch.getWeightClassIds() != null && !exclusionSearch.getWeightClassIds().isEmpty()) {
                query.setParameter("weightClassIds", exclusionSearch.getWeightClassIds());
            }
        }
        weaponList = query.getResultList();
        Collections.shuffle(weaponList);
        return weaponList.isEmpty() ? null : weaponList.getFirst();
    }

    private static StringBuilder getStringBuilder(WeaponCategoryExclusionSearch exclusionSearch) {
        StringBuilder queryBuilder = new StringBuilder("SELECT w FROM Weapon w WHERE 1=1");
        if (exclusionSearch != null) {
            if (exclusionSearch.getWeaponTypeIds() != null && !exclusionSearch.getWeaponTypeIds().isEmpty()) {
                queryBuilder.append(" AND w.weaponType.id NOT IN :weaponTypeIds");
            }
            if (exclusionSearch.getSubweaponIds() != null && !exclusionSearch.getSubweaponIds().isEmpty()) {
                queryBuilder.append(" AND w.subweapon.id NOT IN :subweaponIds");
            }
            if (exclusionSearch.getSpecialWeaponIds() != null && !exclusionSearch.getSpecialWeaponIds().isEmpty()) {
                queryBuilder.append(" AND w.specialWeapon.id NOT IN :specialWeaponIds");
            }
            if (exclusionSearch.getWeightClassIds() != null && !exclusionSearch.getWeightClassIds().isEmpty()) {
                queryBuilder.append(" AND w.weight.id NOT IN :weightClassIds");
            }
            if (exclusionSearch.isDuplicate()) {
                queryBuilder.append(" AND w.weaponName NOT LIKE '%replica%'");
            }
        }
        return queryBuilder;
    }

}
