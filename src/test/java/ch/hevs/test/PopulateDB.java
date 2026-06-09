package ch.hevs.test;

import java.sql.SQLException;
import org.junit.Test;
import ch.hevs.businessobject.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import junit.framework.TestCase;

public class PopulateDB extends TestCase {

	@Test
	public void test() throws SQLException, ClassNotFoundException {

		EntityManagerFactory emf = null;
		EntityManager em = null;
		EntityTransaction tx = null;
		try {
			emf = Persistence.createEntityManagerFactory("seriesPU_unitTest");
			em = emf.createEntityManager();
			tx = em.getTransaction();
			tx.begin();

			// Vérifier si les données existent déjà
            // Vérifier si les données existent déjà
            Long count = em.createQuery("SELECT COUNT(a) FROM Account a", Long.class)
                    .getSingleResult();
            if (count > 0) {
                System.out.println("DB déjà peuplée (" + count + " comptes), suppression en cours...");

                // Supprimer dans l'ordre pour respecter les FK
                em.createQuery("DELETE FROM EpisodeProgress").executeUpdate();
                em.createQuery("DELETE FROM SeriesProgress").executeUpdate();
                em.createQuery("DELETE FROM Review").executeUpdate();
                em.createQuery("DELETE FROM Episode").executeUpdate();
                em.createQuery("DELETE FROM Season").executeUpdate();
                em.createQuery("DELETE FROM Series").executeUpdate();
                em.createQuery("DELETE FROM Account").executeUpdate();

                System.out.println("Suppression terminée, re-peuplement...");
            }

			// Comptes
			Administrator admin = new Administrator("admin", "admin123", "admin@series.ch", "Administrateur");
			Viewer alice = new Viewer("alice", "alice123", "alice@series.ch", "Alice");
			Viewer bob = new Viewer("bob", "bob123", "bob@series.ch", "Bob");

			em.persist(admin);
			em.persist(alice);
			em.persist(bob);

			// Série 1 : Breaking Bad
			Series bb = new Series("Breaking Bad", "Un professeur de chimie devient fabricant de drogue.", 2008);
			bb.setPosterUrl("https://i.ebayimg.com/images/g/HZ0AAOSwolNkPYqj/s-l1200.jpg");
			em.persist(bb);

			Season s1bb = new Season(1, "Saison 1", "Les débuts de Walter White.", bb);
			em.persist(s1bb);

			Episode e1 = new Episode(1, "Pilot", s1bb);
			Episode e2 = new Episode(2, "Cat's in the Bag", s1bb);
			Episode e3 = new Episode(3, "And the Bag's in the River", s1bb);
			em.persist(e1);
			em.persist(e2);
			em.persist(e3);

			// Série 2 : Game of Thrones
			Series got = new Series("Game of Thrones", "Des familles nobles se disputent le trône de fer.", 2011);
			got.setPosterUrl("https://media.desenio.com/site_images/68631b7825436f8361d76848_1909015694_WB0026-5.jpg");
			em.persist(got);

			Season s1got = new Season(1, "Saison 1", "Le début des guerres.", got);
			em.persist(s1got);

			Episode e4 = new Episode(1, "Winter Is Coming", s1got);
			Episode e5 = new Episode(2, "The Kingsroad", s1got);
			em.persist(e4);
			em.persist(e5);

			// Série 3 : Stranger Things
			Series st = new Series("Stranger Things", "Des enfants affrontent des forces surnaturelles.", 2016);
			st.setPosterUrl("https://image.tmdb.org/t/p/original/uOOtwVbSr4QDjAGIifLDwpb2Pdl.jpg");
			em.persist(st);
			Season s1st = new Season(1, "Saison 1", "La disparition de Will.", st);
			em.persist(s1st);

			Episode e6 = new Episode(1, "The Vanishing of Will Byers", s1st);
			Episode e7 = new Episode(2, "The Weirdo on Maple Street", s1st);
			em.persist(e6);
			em.persist(e7);

			// Reviews
			Review r1 = new Review(5, "Chef d'oeuvre absolu !", alice, bb);
			Review r2 = new Review(4, "Très bon mais fin décevante.", bob, got);
			Review r3 = new Review(5, "Addictif dès le premier épisode !", alice, st);
			em.persist(r1);
			em.persist(r2);
			em.persist(r3);

			// Progressions
			SeriesProgress sp1 = new SeriesProgress(alice, bb);
			sp1.setStatus(ProgressStatus.IN_PROGRESS);
			em.persist(sp1);

			EpisodeProgress ep1 = new EpisodeProgress(alice, e1);
			ep1.setStatus(WatchStatus.WATCHED);
			em.persist(ep1);

			tx.commit();
			System.out.println("DB peuplée avec succès.");

		} catch (Exception e) {
			if (tx != null && tx.isActive()) tx.rollback();
			e.printStackTrace();
			throw new RuntimeException("PopulateDB failed", e);
		} finally {
			if (em != null) em.close();
			if (emf != null) emf.close();
		}
	}
}