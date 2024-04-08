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
import Models.Entities.Persona;
import Models.Entities.Situacionmilitar;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author USUARIO
 */
public class SituacionmilitarJpaController implements Serializable {

    public SituacionmilitarJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Situacionmilitar situacionmilitar) throws RollbackFailureException, Exception {
        if (situacionmilitar.getPersonaList() == null) {
            situacionmilitar.setPersonaList(new ArrayList<Persona>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Persona> attachedPersonaList = new ArrayList<Persona>();
            for (Persona personaListPersonaToAttach : situacionmilitar.getPersonaList()) {
                personaListPersonaToAttach = em.getReference(personaListPersonaToAttach.getClass(), personaListPersonaToAttach.getPersonaID());
                attachedPersonaList.add(personaListPersonaToAttach);
            }
            situacionmilitar.setPersonaList(attachedPersonaList);
            em.persist(situacionmilitar);
            for (Persona personaListPersona : situacionmilitar.getPersonaList()) {
                Situacionmilitar oldSituacionMilitarIDOfPersonaListPersona = personaListPersona.getSituacionMilitarID();
                personaListPersona.setSituacionMilitarID(situacionmilitar);
                personaListPersona = em.merge(personaListPersona);
                if (oldSituacionMilitarIDOfPersonaListPersona != null) {
                    oldSituacionMilitarIDOfPersonaListPersona.getPersonaList().remove(personaListPersona);
                    oldSituacionMilitarIDOfPersonaListPersona = em.merge(oldSituacionMilitarIDOfPersonaListPersona);
                }
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

    public void edit(Situacionmilitar situacionmilitar) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Situacionmilitar persistentSituacionmilitar = em.find(Situacionmilitar.class, situacionmilitar.getSituacionMilitarID());
            List<Persona> personaListOld = persistentSituacionmilitar.getPersonaList();
            List<Persona> personaListNew = situacionmilitar.getPersonaList();
            List<Persona> attachedPersonaListNew = new ArrayList<Persona>();
            for (Persona personaListNewPersonaToAttach : personaListNew) {
                personaListNewPersonaToAttach = em.getReference(personaListNewPersonaToAttach.getClass(), personaListNewPersonaToAttach.getPersonaID());
                attachedPersonaListNew.add(personaListNewPersonaToAttach);
            }
            personaListNew = attachedPersonaListNew;
            situacionmilitar.setPersonaList(personaListNew);
            situacionmilitar = em.merge(situacionmilitar);
            for (Persona personaListOldPersona : personaListOld) {
                if (!personaListNew.contains(personaListOldPersona)) {
                    personaListOldPersona.setSituacionMilitarID(null);
                    personaListOldPersona = em.merge(personaListOldPersona);
                }
            }
            for (Persona personaListNewPersona : personaListNew) {
                if (!personaListOld.contains(personaListNewPersona)) {
                    Situacionmilitar oldSituacionMilitarIDOfPersonaListNewPersona = personaListNewPersona.getSituacionMilitarID();
                    personaListNewPersona.setSituacionMilitarID(situacionmilitar);
                    personaListNewPersona = em.merge(personaListNewPersona);
                    if (oldSituacionMilitarIDOfPersonaListNewPersona != null && !oldSituacionMilitarIDOfPersonaListNewPersona.equals(situacionmilitar)) {
                        oldSituacionMilitarIDOfPersonaListNewPersona.getPersonaList().remove(personaListNewPersona);
                        oldSituacionMilitarIDOfPersonaListNewPersona = em.merge(oldSituacionMilitarIDOfPersonaListNewPersona);
                    }
                }
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
                Integer id = situacionmilitar.getSituacionMilitarID();
                if (findSituacionmilitar(id) == null) {
                    throw new NonexistentEntityException("The situacionmilitar with id " + id + " no longer exists.");
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
            Situacionmilitar situacionmilitar;
            try {
                situacionmilitar = em.getReference(Situacionmilitar.class, id);
                situacionmilitar.getSituacionMilitarID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The situacionmilitar with id " + id + " no longer exists.", enfe);
            }
            List<Persona> personaList = situacionmilitar.getPersonaList();
            for (Persona personaListPersona : personaList) {
                personaListPersona.setSituacionMilitarID(null);
                personaListPersona = em.merge(personaListPersona);
            }
            em.remove(situacionmilitar);
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

    public List<Situacionmilitar> findSituacionmilitarEntities() {
        return findSituacionmilitarEntities(true, -1, -1);
    }

    public List<Situacionmilitar> findSituacionmilitarEntities(int maxResults, int firstResult) {
        return findSituacionmilitarEntities(false, maxResults, firstResult);
    }

    private List<Situacionmilitar> findSituacionmilitarEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Situacionmilitar.class));
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

    public Situacionmilitar findSituacionmilitar(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Situacionmilitar.class, id);
        } finally {
            em.close();
        }
    }

    public int getSituacionmilitarCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Situacionmilitar> rt = cq.from(Situacionmilitar.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
