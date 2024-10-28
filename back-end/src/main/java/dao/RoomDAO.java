	package dao;
	
	import java.util.List;

import javax.json.JsonString;
import javax.persistence.EntityManager;
	import javax.persistence.EntityManagerFactory;
	import javax.persistence.Persistence;
	import javax.persistence.TypedQuery;

import model.Room;

import javax.persistence.Query;
	
	
	public class RoomDAO {
	
	
	    
	    EntityManager create() {
	        EntityManagerFactory factory = Persistence.createEntityManagerFactory("movieproject_back-end-new");
	        return factory.createEntityManager();
	    }
	
	  
	    public List<Room> getAll() {
	        EntityManager mgr = create();
	        TypedQuery<Room> query = mgr.createNamedQuery("Room.findAll", Room.class);
	        return query.getResultList();
	    }
	
	  
	    public void addRoom(Room room) {
	        EntityManager mgr = create();
	        try {
	            mgr.getTransaction().begin();
	            mgr.persist(room);  
	            mgr.getTransaction().commit();
	        } finally {
	            mgr.close();
	        }
	    }
	
	  
	    public Room getRoomBySession(String sessionID) {
	        EntityManager mgr = create();
	        TypedQuery<Room> query = mgr.createQuery("SELECT r FROM Room r WHERE r.session = :sessionID", Room.class);
	        query.setParameter("sessionID", sessionID);
	        Room room = null;
	        try {
	            room = query.getSingleResult();  
	        } catch (Exception e) {
	            System.out.println("No room found with session ID: " + sessionID);
	        } finally {
	            mgr.close();
	        }
	        return room;
	    }
	
	
	    public void updateRoom(Room updatedRoom) {
	        EntityManager mgr = create();
	        try {
	            mgr.getTransaction().begin();
	            Room room = mgr.find(Room.class, updatedRoom.getSession());  
	            if (room != null) {
	                room.setRoomname(updatedRoom.getRoomname());
	                room.setRoomusing(updatedRoom.getRoomusing());
	                room.setMovieid(updatedRoom.getMovieid());
	                room.setRoomname(updatedRoom.getRoomname());
	                room.setPlaydate(updatedRoom.getPlaydate());
	                room.setPlaytime(updatedRoom.getPlaytime());
	                room.setSession(updatedRoom.getSession());
	                room.setSeats(updatedRoom.getSeats());
	                mgr.merge(room);  
	            }
	            mgr.getTransaction().commit();
	        } finally {
	            mgr.close();
	        }
	    }
	   
	    
	    public void updateRoomFields(int id, String roomname, String roomusing, String movieid, String roommovie) {
	        EntityManager mgr = create();
	        try {
	            mgr.getTransaction().begin();
	            Room room = mgr.find(Room.class, id);  // 使用ID尋找房間
	            if (room != null) {
	                room.setRoomname(roomname);
	                room.setRoomusing(roomusing);
	                room.setMovieid(movieid);
	                room.setRoommovie(roommovie);
	                mgr.merge(room);  // 更新資料
	            }
	            mgr.getTransaction().commit();
	        } finally {
	            mgr.close();
	        }
	    }
	    
	    public void deleteRoomById(int id) {
	        EntityManager mgr = create();
	        try {
	            mgr.getTransaction().begin();
	            Query query = mgr.createQuery("DELETE FROM Room r WHERE r.id = :id"); // 使用 JPQL 根據 ID 刪除房間
	            query.setParameter("id", id); // 設置參數
	            query.executeUpdate(); // 執行刪除操作
	            mgr.getTransaction().commit();
	        } catch (Exception e) {
	            System.out.println("刪除房間失敗: " + e.getMessage());
	            if (mgr.getTransaction().isActive()) {
	                mgr.getTransaction().rollback(); // 回滾交易
	            }
	        } finally {
	            mgr.close(); // 確保關閉 EntityManager
	        }
	    }


	    public void deleteRoomBySession(String sessionID) {
	        EntityManager mgr = create();
	        try {
	            mgr.getTransaction().begin();
	            Query query = mgr.createQuery("DELETE FROM Room r WHERE r.session = :sessionID");
	            query.setParameter("sessionID", sessionID);
	            query.executeUpdate();  
	            mgr.getTransaction().commit();
	        } finally {
	            mgr.close();
	        }
	    }
	    
	    
	 // 新增 exists 方法
	    public boolean exists(String sessionID) {
	        EntityManager mgr = create();
	        try {
	            TypedQuery<Room> query = mgr.createQuery("SELECT r FROM Room r WHERE r.session = :sessionID", Room.class);
	            query.setParameter("sessionID", sessionID);
	            return !query.getResultList().isEmpty(); // 如果結果不為空，則房間存在
	        } finally {
	            mgr.close(); // 確保關閉 EntityManager
	        }
	    }

	
	}
