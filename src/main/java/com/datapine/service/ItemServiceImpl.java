package com.datapine.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.Sid;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.datapine.dao.ItemDAO;
import com.datapine.domain.Item;
import com.datapine.exception.ResourceNotFoundException;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private ItemDAO itemDAO;

	@Autowired
	private JdbcMutableAclService aclService;

	@Autowired
	private DataSourceTransactionManager transactionManager;

	@Override
	public Item save(Item item) {
		item = itemDAO.save(item);
		System.out.println();
		final Long itemId = item.getId();
		final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		TransactionTemplate tt = new TransactionTemplate(transactionManager);
		tt.execute(new TransactionCallbackWithoutResult() {
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				Sid sid = new PrincipalSid(principal.toString());
				final ObjectIdentity objectIdentity = new ObjectIdentityImpl(Item.class, itemId);
				Permission p = BasePermission.ADMINISTRATION;

				MutableAcl acl = null;
				try {
					acl = (MutableAcl) aclService.readAclById(objectIdentity);
				} catch (NotFoundException nfe) {
					acl = aclService.createAcl(objectIdentity);
				}

				acl.insertAce(acl.getEntries().size(), p, sid, true);
				aclService.updateAcl(acl);
			}
		});
		return item;
	}

	@Override
	public List<Item> getAll() {
		return itemDAO.findAll();
	}

	@Override
	public Item fetchById(Long id) throws ResourceNotFoundException {
		Item item = itemDAO.findById(id);
		if (item == null) {
			throw new ResourceNotFoundException("Item Not found");
		}
		return item;
	}

	@Override
	public void delete(Long id) {
		Item item = itemDAO.findById(id);
		if (item != null) {
			itemDAO.delete(item);
		}
	}

	@Override
	public Item update(Long id, Item item) throws ResourceNotFoundException {
		Item savedItem = itemDAO.findById(id);
		if (savedItem == null) {
			throw new ResourceNotFoundException("Item Not found");
		}
		savedItem.setMessage(item.getMessage());
		itemDAO.update(savedItem);
		return savedItem;
	}

}
