package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import model.Eatmenu;

public class EatmenuDAO {
	public EntityManager create() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("movieproject_back-end-new");
		return factory.createEntityManager();
	}

	public List<Eatmenu> getAll() {
		EntityManager mgr = create();
		TypedQuery<Eatmenu> query = mgr.createNamedQuery("Eatmenu.findAll", Eatmenu.class);
		return query.getResultList();
	}

	public Eatmenu getEatItemByName(String itemName) {
		/*EntityManager mgr = create();
		TypedQuery<Eatmenu> query = mgr.createNamedQuery("Eatmenu.findByName", Eatmenu.class);
		query.setParameter("itemName", itemName);
		List<Eatmenu> eats = query.getResultList();*/
		List<Eatmenu> eats = getAll();
		for(int i=0;i<eats.size();i++)
		{
			if(itemName.equals(eats.get(i).getItemName()))
				return eats.get(i);
		}
		return eats.get(eats.size() - 1);
	}
	

}
