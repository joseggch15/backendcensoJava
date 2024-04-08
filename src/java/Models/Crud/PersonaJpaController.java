/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models.Crud;

import Models.Crud.exceptions.NonexistentEntityException;
import Models.Crud.exceptions.RollbackFailureException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import Models.Entities.Situacionmilitar;
import Models.Entities.Lugar;
import Models.Entities.Persona;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author USUARIO
 */
public class PersonaJpaController implements Serializable {

    public PersonaJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public PersonaJpaController(EntityManagerFactory emf) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Persona persona) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Situacionmilitar situacionMilitarID = persona.getSituacionMilitarID();
            if (situacionMilitarID != null) {
                situacionMilitarID = em.getReference(situacionMilitarID.getClass(), situacionMilitarID.getSituacionMilitarID());
                persona.setSituacionMilitarID(situacionMilitarID);
            }
            Lugar lugarNacimientoID = persona.getLugarNacimientoID();
            if (lugarNacimientoID != null) {
                lugarNacimientoID = em.getReference(lugarNacimientoID.getClass(), lugarNacimientoID.getLugarID());
                persona.setLugarNacimientoID(lugarNacimientoID);
            }
            em.persist(persona);
            if (situacionMilitarID != null) {
                situacionMilitarID.getPersonaList().add(persona);
                situacionMilitarID = em.merge(situacionMilitarID);
            }
            if (lugarNacimientoID != null) {
                lugarNacimientoID.getPersonaList().add(persona);
                lugarNacimientoID = em.merge(lugarNacimientoID);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Persona persona) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Persona persistentPersona = em.find(Persona.class, persona.getPersonaID());
            Situacionmilitar situacionMilitarIDOld = persistentPersona.getSituacionMilitarID();
            Situacionmilitar situacionMilitarIDNew = persona.getSituacionMilitarID();
            Lugar lugarNacimientoIDOld = persistentPersona.getLugarNacimientoID();
            Lugar lugarNacimientoIDNew = persona.getLugarNacimientoID();
            if (situacionMilitarIDNew != null) {
                situacionMilitarIDNew = em.getReference(situacionMilitarIDNew.getClass(), situacionMilitarIDNew.getSituacionMilitarID());
                persona.setSituacionMilitarID(situacionMilitarIDNew);
            }
            if (lugarNacimientoIDNew != null) {
                lugarNacimientoIDNew = em.getReference(lugarNacimientoIDNew.getClass(), lugarNacimientoIDNew.getLugarID());
                persona.setLugarNacimientoID(lugarNacimientoIDNew);
            }
            persona = em.merge(persona);
            if (situacionMilitarIDOld != null && !situacionMilitarIDOld.equals(situacionMilitarIDNew)) {
                situacionMilitarIDOld.getPersonaList().remove(persona);
                situacionMilitarIDOld = em.merge(situacionMilitarIDOld);
            }
            if (situacionMilitarIDNew != null && !situacionMilitarIDNew.equals(situacionMilitarIDOld)) {
                situacionMilitarIDNew.getPersonaList().add(persona);
                situacionMilitarIDNew = em.merge(situacionMilitarIDNew);
            }
            if (lugarNacimientoIDOld != null && !lugarNacimientoIDOld.equals(lugarNacimientoIDNew)) {
                lugarNacimientoIDOld.getPersonaList().remove(persona);
                lugarNacimientoIDOld = em.merge(lugarNacimientoIDOld);
            }
            if (lugarNacimientoIDNew != null && !lugarNacimientoIDNew.equals(lugarNacimientoIDOld)) {
                lugarNacimientoIDNew.getPersonaList().add(persona);
                lugarNacimientoIDNew = em.merge(lugarNacimientoIDNew);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = persona.getPersonaID();
                if (findPersona(id) == null) {
                    throw new NonexistentEntityException("The persona with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Persona persona;
            try {
                persona = em.getReference(Persona.class, id);
                persona.getPersonaID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The persona with id " + id + " no longer exists.", enfe);
            }
            Situacionmilitar situacionMilitarID = persona.getSituacionMilitarID();
            if (situacionMilitarID != null) {
                situacionMilitarID.getPersonaList().remove(persona);
                situacionMilitarID = em.merge(situacionMilitarID);
            }
            Lugar lugarNacimientoID = persona.getLugarNacimientoID();
            if (lugarNacimientoID != null) {
                lugarNacimientoID.getPersonaList().remove(persona);
                lugarNacimientoID = em.merge(lugarNacimientoID);
            }
            em.remove(persona);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Persona> findPersonaEntities() {
        return findPersonaEntities(true, -1, -1);
    }

    public List<Persona> findPersonaEntities(int maxResults, int firstResult) {
        return findPersonaEntities(false, maxResults, firstResult);
    }

    private List<Persona> findPersonaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Persona.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Persona findPersona(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Persona.class, id);
        } finally {
            em.close();
        }
    }

    public int getPersonaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Persona> rt = cq.from(Persona.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
