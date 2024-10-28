package dao;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import model.Movie;

public class MovieDao {
	public MovieDao() {
	}

	EntityManager create() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("movieproject_back-end-new");
		return factory.createEntityManager();
	}

	public List<Movie> getAll() {
		EntityManager mgr = create();
//		TypedQuery<Movie> query=mgr.createNamedQuery("Movie.findAll",Movie.class);
		return mgr.createQuery("select m from Movie m").getResultList();
//		return query.getResultList();
	}

	public List<Movie> findByMovieKinds(List<String> moviekinds) {
		EntityManager em = create();
		try {
			// 使用 JPQL 動態構建查詢
			String queryString = "SELECT m FROM Movie m WHERE ";

			// 構建每個條件
			for (int i = 0; i < moviekinds.size(); i++) {
				queryString += "m.moviekind LIKE :kind" + i; // 使用 LIKE 進行部分匹配
				if (i < moviekinds.size() - 1) {
					queryString += " OR "; // 使用 OR 來連接多個條件
				}
			}

			// 創建查詢
			TypedQuery<Movie> query = em.createQuery(queryString, Movie.class);

			// 設置每個參數
			for (int i = 0; i < moviekinds.size(); i++) {
				query.setParameter("kind" + i, "%" + moviekinds.get(i) + "%"); // 加上 % 進行部分匹配
			}

			return query.getResultList(); // 返回結果
		} catch (Exception e) {
			System.out.println("error:" + e.getMessage());
		} finally {
			em.close();
		}
		return null;
	}

	// 根据ID获取电影
	public Movie getMovieById(Integer movieid) {
		EntityManager em = create();
		try {
			return em.find(Movie.class, movieid);
		} catch (Exception e) {
			System.out.println("error:" + e.getMessage());
		} finally {
			em.close();
		}
		return null;
	}

	// 添加新电影到数据库
	public void addMovie(Movie movie) {
		EntityManager mgr = create();
		EntityTransaction transaction = mgr.getTransaction();
		try {
			transaction.begin();
			mgr.persist(movie);
			transaction.commit();
		} catch (Exception ex) {
			if (transaction.isActive()) {
				transaction.rollback(); // 出现错误时回滚事务
			}
			System.out.println("Add movie error: " + ex.getMessage());
		} finally {
			mgr.close();
		}
	}

	// 删除电影
	public void deleteMovies(Integer movieid) {
		EntityManager em = create();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			Movie movie = em.find(Movie.class, movieid);
			if (movie != null) {
				em.remove(movie);
				transaction.commit();
				System.out.println("Movie with ID: " + movieid + " deleted successfully.");
			} else {
				System.out.println("Movie with ID: " + movieid + " not found");
				transaction.rollback();
			}
		} catch (Exception e) {
			if (transaction.isActive()) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			em.close();
		}
	}

	// 更新电影
	public void updateMovies(Integer movieid, Movie newMovie) {
		EntityManager em = create();
		EntityTransaction transaction = em.getTransaction();
		try {
			transaction.begin();
			Movie existingMovie = em.find(Movie.class, movieid);
			if (existingMovie != null) {
				// 更新电影的字段
				if (newMovie.getMovieName() != null && !newMovie.getMovieName().isEmpty()) {
					existingMovie.setMovieName(newMovie.getMovieName());
				}
				if (newMovie.getCinemaRoom() != null && !newMovie.getCinemaRoom().isEmpty()) {
					existingMovie.setCinemaRoom(newMovie.getCinemaRoom());
				}
				if (newMovie.getMoviecast() != null && !newMovie.getMoviecast().isEmpty()) {
					existingMovie.setMoviecast(newMovie.getMoviecast());
				}
				if (newMovie.getMoviedirector() != null && !newMovie.getMoviedirector().isEmpty()) {
					existingMovie.setMoviedirector(newMovie.getMoviedirector());
				}
				if (newMovie.getMoviestartdate() != null) {
					existingMovie.setMoviestartdate(newMovie.getMoviestartdate());
				}
				if (newMovie.getMovieenddate() != null) {
					existingMovie.setMovieenddate(newMovie.getMovieenddate());
				}
				if (newMovie.getMoviegrading() != null && !newMovie.getMoviegrading().isEmpty()) {
					existingMovie.setMoviegrading(newMovie.getMoviegrading());
				}
				if (newMovie.getMovieintro() != null && !newMovie.getMovieintro().isEmpty()) {
					existingMovie.setMovieintro(newMovie.getMovieintro());
				}
				if (newMovie.getMoviekind() != null && !newMovie.getMoviekind().isEmpty()) {
					existingMovie.setMoviekind(newMovie.getMoviekind());
				}
				Optional.ofNullable(newMovie.getRuntime()).filter(runtime -> runtime > 0)
						.ifPresent(existingMovie::setRuntime);

				if (newMovie.getMovieType() != null && !newMovie.getMovieType().isEmpty()) {
					existingMovie.setMovieType(newMovie.getMovieType());
				}

				transaction.commit();
				System.out.println("Movie with ID: " + movieid + " updated successfully.");
			} else {
				System.out.println("Movie with ID: " + movieid + " not found");
			}
		} catch (Exception e) {
			if (transaction.isActive()) {
				transaction.rollback();
			}
			e.printStackTrace();
		} finally {
			em.close();
		}
	}

	public String getMovieName(int count) {
		return getAll().get(count).getMovieName();
	}

	public int getMovieid(int count) {
		return getAll().get(count).getMovieid();
	}

	public String getCinemaRoom(int count) {
		return getAll().get(count).getCinemaRoom();
	}

	public String getMoviecast(int count) {
		return getAll().get(count).getMoviecast();
	}

	public String getMoviedirector(int count) {
		return getAll().get(count).getMoviedirector();
	}

	public Date getMovieenddate(int count) {
		return getAll().get(count).getMovieenddate();
	}

	public String getMoviegrading(int count) {
		return getAll().get(count).getMoviegrading();
	}

	public String getMovieintro(int count) {
		return getAll().get(count).getMovieintro();
	}

	public String getMoviekind(int count) {
		return getAll().get(count).getMoviekind();
	}

	public Date getMoviestartdate(int count) {
		return getAll().get(count).getMoviestartdate();
	}

	public String getMovieType(int count) {
		return getAll().get(count).getMovieType();
	}

	public int getRuntime(int count) {
		return getAll().get(count).getRuntime();
	}

}
