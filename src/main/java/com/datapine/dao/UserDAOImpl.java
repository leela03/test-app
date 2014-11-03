package com.datapine.dao;

import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.datapine.domain.User;

@Repository("userDAO")
@SuppressWarnings("unchecked")
public class UserDAOImpl implements UserDAO {

	@PersistenceContext(unitName = "datapine")
	private EntityManager em;

	@Override
	@Transactional
	public User save(User user) {
		em.persist(user);
		em.flush();
		return user;
	}

	@Override
	@Transactional
	public User update(User user) {
		em.merge(user);
		return user;
	}

	@Override
	@Transactional
	public void delete(User user) {
		em.remove(em.contains(user) ? user : em.merge(user));
	}

	@Override
	public User findById(Long id) {
		return em.find(User.class, id);
	}

	@Override
	public User findByEmail(String email) {
		Query q = em.createQuery("select u from User u where u.email=?1");
		q.setParameter(1, email);
		List<User> result = q.getResultList();
		if(!result.isEmpty()){
			return result.get(0);
		}
		return null;
	}

	@Override
	public Iterator<User> findAllOrderById() {
		return em.createQuery("SELECT u FROM User u order BY id")
				.getResultList().iterator();
	}

	@Override
	public List<User> findAll() {
		Query query = em.createQuery("SELECT u FROM User u ");
		return (List<User>) query.getResultList();
	}

}
