package dao;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import model.Eatmenu;
import model.Movie;
import model.Movierank;
import model.Order;

public class MovierankDAO {
	public EntityManager create() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("movieproject_back-end-new");
		return factory.createEntityManager();
	}

	public List<Movierank> getAll() {
		EntityManager mgr = create();
		TypedQuery<Movierank> query = mgr.createNamedQuery("Movierank.findAll", Movierank.class);
		TypedQuery<Eatmenu> query2 = mgr.createNamedQuery("Eatmenu.findAll", Eatmenu.class);
		return query.getResultList();
	}

	public List<Movierank> addMovieMovierankList() {
		OrderDAO orderDAO = new OrderDAO();
		MovieDao moveDao = new MovieDao();
		List<Movierank> movieRank = null;
		List<Order> orders = orderDAO.getAll();
		List<Movie> movies = moveDao.getAll();
		int[] countFromTicket = new int[moveDao.getAll().size()];
		if (orders.size() > 2) {
			for (int i = 0; i < moveDao.getAll().size(); i++) {
				countFromTicket[i] = 0;
				for (int j = 0; j < orders.size(); j++) {
					if (movies.get(i).getMovieName().equals(orders.get(j).getMovieName())) {
						countFromTicket[i]++;
					}
				}
				Movierank rank = new Movierank(movies.get(i).getMovieName(), countFromTicket[i]);

				addMovierank(rank);

			}
			List<Movierank> sortedMovieranks = getAll().stream().sorted(Comparator.comparingInt(Movierank::getCount))
					.collect(Collectors.toList());
			movieRank = sortedMovieranks.subList(0, getAll().size());
			Collections.reverse(movieRank);
		}

		return movieRank;
	}

	public List<Movierank> updateMovieMovierankList() {
		OrderDAO orderDAO = new OrderDAO();
		MovieDao moveDao = new MovieDao();
		List<Movierank> movieRank = null;
		List<Order> orders = orderDAO.getAll();
		List<Movie> movies = moveDao.getAll();
		int[] countFromTicket = new int[moveDao.getAll().size()];
		if (orders.size() > 2) {
			for (int i = 0; i < moveDao.getAll().size(); i++) {
				countFromTicket[i] = 0;
				for (int j = 0; j < orders.size(); j++) {
					if (movies.get(i).getMovieName().equals(orders.get(j).getMovieName())) {
						countFromTicket[i]++;
					}
				}
				Movierank rank = new Movierank(movies.get(i).getMovieName(), countFromTicket[i]);
				if(rank!=null) {
					deleteMovierank(rank);
				}
//				deleteMovierank(rank);
				addMovierank(rank);

			}
			List<Movierank> sortedMovieranks = getAll().stream().sorted(Comparator.comparingInt(Movierank::getCount))
					.collect(Collectors.toList());
			movieRank = sortedMovieranks.subList(0, getAll().size());
			Collections.reverse(movieRank);
		}

		return movieRank;
	}

	public void addMovierank(Movierank rank) {
		EntityManager mgr = create();
		try {
			mgr.getTransaction().begin();
			mgr.persist(rank);
			mgr.getTransaction().commit();
		} finally {
			mgr.close();
		}
	}

	public void updateMovierank(Movierank rank) {
		EntityManager mgr = create();
		Movierank tempMovierank = mgr.find(Movierank.class, rank.getMoviename());
		try {
			mgr.getTransaction().begin();
			mgr.persist(tempMovierank);
			mgr.getTransaction().commit();
		} finally {
			mgr.close();
		}
	}
	public void deleteMovierank(Movierank rank) {
		EntityManager mgr = create();
		Movierank tempMovierank = mgr.find(Movierank.class, rank.getMoviename());
		try {
			if(tempMovierank!=null) {
				mgr.getTransaction().begin();
				mgr.remove(tempMovierank);
				mgr.getTransaction().commit();
			}
			
		} finally {
			mgr.close();
		}
    }
}
