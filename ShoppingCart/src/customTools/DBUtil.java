package customTools;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

public class DBUtil {
	private static final EntityManagerFactory emf = Persistence
			.createEntityManagerFactory("ShoppingCart");
	public static EntityManagerFactory getEmFactory() {
		return emf;
	}
	
	public static EntityManager createEm(){
		return emf.createEntityManager();
	}
	
	public static <T> void addToDB(Object T){
		EntityManager em = emf.createEntityManager();
		EntityTransaction trans = em.getTransaction();
		try{
			trans.begin();
			em.merge(T);
			trans.commit();
			}
			catch(Exception e){
			trans.rollback();
			}
		finally{
			em.close();
		}
		
	}
	
	public static <T> TypedQuery<T> createQuery(String q, Class<T> className){
		EntityManager em = emf.createEntityManager();
		TypedQuery<T> tQuery = em.createQuery(q,className);
	//	em.close();
		return tQuery;
	}
	
	
	
	public static <T> void updateDB(TypedQuery<T> tQuery){
		EntityManager em = emf.createEntityManager();
		EntityTransaction trans = em.getTransaction();	
		try{
			trans.begin();
			tQuery.executeUpdate();
			trans.commit();
		} catch(Exception e){
			trans.rollback();
		}finally{
			em.close();
		}
	}	
}