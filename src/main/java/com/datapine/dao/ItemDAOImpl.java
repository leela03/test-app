package com.datapine.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.datapine.domain.Item;

@Repository("itemDAO")
public class ItemDAOImpl implements ItemDAO {

	@PersistenceContext(unitName = "datapine")
	private EntityManager em;

	@Override
	@Transactional
	public Item save(Item item) {
		em.persist(item);
		em.flush();
		return item;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Item> findAll() {
		Query query = em.createQuery("SELECT i FROM Item i ");
		return (List<Item>) query.getResultList();
	}

	@Override
	@Transactional
	public Item update(Item item) {
		em.merge(item);
		return item;
	}

	@Override
	@Transactional
	public void delete(Item item) {
		em.remove(em.contains(item) ? item : em.merge(item));
	}

	@Override
	public Item findById(Long id) {
		return em.find(Item.class, id);
	}

}
