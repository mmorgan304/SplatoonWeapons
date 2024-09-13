package dev.melissamorgan.splatoonweapons.dao;

import dev.melissamorgan.splatoonweapons.entities.*;
import dev.melissamorgan.splatoonweapons.searchMethods.WeaponSearch;
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
    public Weapon getRandomWeapon(WeaponSearch weaponSearch) {
        List<Weapon> weaponList;
        StringBuilder queryBuilder;
        if (weaponSearch.isInclusionSearch()) {
            queryBuilder = inclusiveQuery(weaponSearch);
        } else {
            queryBuilder = exclusiveQuery(weaponSearch);
        }
        TypedQuery<Weapon> query = entityManager.createQuery(queryBuilder.toString(), Weapon.class);
        if (weaponSearch.getWeaponTypeIds() != null && !weaponSearch.getWeaponTypeIds().isEmpty()) {
            query.setParameter("weaponTypeIds", weaponSearch.getWeaponTypeIds());
        }
        if (weaponSearch.getSubweaponIds() != null && !weaponSearch.getSubweaponIds().isEmpty()) {
            query.setParameter("subweaponIds", weaponSearch.getSubweaponIds());
        }
        if (weaponSearch.getSpecialWeaponIds() != null && !weaponSearch.getSpecialWeaponIds().isEmpty()) {
            query.setParameter("specialWeaponIds", weaponSearch.getSpecialWeaponIds());
        }
        if (weaponSearch.getWeightClassIds() != null && !weaponSearch.getWeightClassIds().isEmpty()) {
            query.setParameter("weightClassIds", weaponSearch.getWeightClassIds());
        }
        if (weaponSearch.getWeaponIds() != null && !weaponSearch.getWeaponIds().isEmpty()) {
            query.setParameter("weaponIds", weaponSearch.getWeaponIds());
        }
        weaponList = query.getResultList();
        Collections.shuffle(weaponList);
        return weaponList.isEmpty() ? null : weaponList.getFirst();
    }

    private static StringBuilder exclusiveQuery(WeaponSearch weaponSearch) {
        StringBuilder queryBuilder = new StringBuilder("SELECT w FROM Weapon w WHERE 1=1");
        if (weaponSearch != null) {
            if (weaponSearch.getWeaponTypeIds() != null && !weaponSearch.getWeaponTypeIds().isEmpty()) {
                queryBuilder.append(" AND w.weaponType.id NOT IN :weaponTypeIds");
            }
            if (weaponSearch.getSubweaponIds() != null && !weaponSearch.getSubweaponIds().isEmpty()) {
                queryBuilder.append(" AND w.subweapon.id NOT IN :subweaponIds");
            }
            if (weaponSearch.getSpecialWeaponIds() != null && !weaponSearch.getSpecialWeaponIds().isEmpty()) {
                queryBuilder.append(" AND w.specialWeapon.id NOT IN :specialWeaponIds");
            }
            if (weaponSearch.getWeightClassIds() != null && !weaponSearch.getWeightClassIds().isEmpty()) {
                queryBuilder.append(" AND w.weight.id NOT IN :weightClassIds");
            }
            if (weaponSearch.isDuplicate()) {
                queryBuilder.append(" AND w.weaponName NOT LIKE '%replica%'");
            }
        }
        return queryBuilder;
    }

    private static StringBuilder inclusiveQuery(WeaponSearch weaponSearch) {
        StringBuilder queryBuilder = new StringBuilder("SELECT w FROM Weapon w WHERE 1=1");
        boolean hasCriteria = false;

        if (weaponSearch != null) {
            if ((weaponSearch.getWeaponTypeIds() != null && !weaponSearch.getWeaponTypeIds().isEmpty()) ||
                    (weaponSearch.getSubweaponIds() != null && !weaponSearch.getSubweaponIds().isEmpty()) ||
                    (weaponSearch.getSpecialWeaponIds() != null && !weaponSearch.getSpecialWeaponIds().isEmpty()) ||
                    (weaponSearch.getWeightClassIds() != null && !weaponSearch.getWeightClassIds().isEmpty()) ||
                    (weaponSearch.getWeaponIds() != null && !weaponSearch.getWeaponIds().isEmpty())) {
                queryBuilder.append(" AND (");
            }
            if (weaponSearch.getWeaponTypeIds() != null && !weaponSearch.getWeaponTypeIds().isEmpty()) {
                queryBuilder.append(" w.weaponType.id IN :weaponTypeIds");
                hasCriteria = true;
            }
            if (weaponSearch.getSubweaponIds() != null && !weaponSearch.getSubweaponIds().isEmpty()) {
                if (hasCriteria) queryBuilder.append(" AND");
                queryBuilder.append(" w.subweapon.id IN :subweaponIds");
                hasCriteria = true;
            }
            if (weaponSearch.getSpecialWeaponIds() != null && !weaponSearch.getSpecialWeaponIds().isEmpty()) {
                if (hasCriteria) queryBuilder.append(" AND");
                queryBuilder.append(" w.specialWeapon.id IN :specialWeaponIds");
                hasCriteria = true;
            }
            if (weaponSearch.getWeightClassIds() != null && !weaponSearch.getWeightClassIds().isEmpty()) {
                if (hasCriteria) queryBuilder.append(" AND");
                queryBuilder.append(" w.weight.id IN :weightClassIds");
                hasCriteria = true;
            }
            if (weaponSearch.getWeaponIds() != null && !weaponSearch.getWeaponIds().isEmpty()) {
                if (hasCriteria) queryBuilder.append(" AND");
                queryBuilder.append(" w.id IN :weaponIds");
                hasCriteria = true;
            }
            if (hasCriteria) {
                queryBuilder.append(" )");
            }
            if (weaponSearch.isDuplicate()) {
                queryBuilder.append(" AND w.weaponName NOT LIKE '%replica%'");
            }
        }
        return queryBuilder;
    }

}
