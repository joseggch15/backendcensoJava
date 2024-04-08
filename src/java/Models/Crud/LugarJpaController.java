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
import Models.Entities.Municipio;
import Models.Entities.Lugar;
import Models.Entities.Persona;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author USUARIO
 */
public class LugarJpaController implements Serializable {

    public LugarJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Lugar lugar) throws RollbackFailureException, Exception {
        if (lugar.getPersonaList() == null) {
            lugar.setPersonaList(new ArrayList<Persona>());
        }
        if (lugar.getLugarList() == null) {
            lugar.setLugarList(new ArrayList<Lugar>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Municipio municipioID = lugar.getMunicipioID();
            if (municipioID != null) {
                municipioID = em.getReference(municipioID.getClass(), municipioID.getId());
                lugar.setMunicipioID(municipioID);
            }
            Lugar municipioID1 = lugar.getMunicipioID1();
            if (municipioID1 != null) {
                municipioID1 = em.getReference(municipioID1.getClass(), municipioID1.getLugarID());
                lugar.setMunicipioID1(municipioID1);
            }
            List<Persona> attachedPersonaList = new ArrayList<Persona>();
            for (Persona personaListPersonaToAttach : lugar.getPersonaList()) {
                personaListPersonaToAttach = em.getReference(personaListPersonaToAttach.getClass(), personaListPersonaToAttach.getPersonaID());
                attachedPersonaList.add(personaListPersonaToAttach);
            }
            lugar.setPersonaList(attachedPersonaList);
            List<Lugar> attachedLugarList = new ArrayList<Lugar>();
            for (Lugar lugarListLugarToAttach : lugar.getLugarList()) {
                lugarListLugarToAttach = em.getReference(lugarListLugarToAttach.getClass(), lugarListLugarToAttach.getLugarID());
                attachedLugarList.add(lugarListLugarToAttach);
            }
            lugar.setLugarList(attachedLugarList);
            em.persist(lugar);
            if (municipioID != null) {
                municipioID.getLugarList().add(lugar);
                municipioID = em.merge(municipioID);
            }
            if (municipioID1 != null) {
                municipioID1.getLugarList().add(lugar);
                municipioID1 = em.merge(municipioID1);
            }
            for (Persona personaListPersona : lugar.getPersonaList()) {
                Lugar oldLugarNacimientoIDOfPersonaListPersona = personaListPersona.getLugarNacimientoID();
                personaListPersona.setLugarNacimientoID(lugar);
                personaListPersona = em.merge(personaListPersona);
                if (oldLugarNacimientoIDOfPersonaListPersona != null) {
                    oldLugarNacimientoIDOfPersonaListPersona.getPersonaList().remove(personaListPersona);
                    oldLugarNacimientoIDOfPersonaListPersona = em.merge(oldLugarNacimientoIDOfPersonaListPersona);
                }
            }
            for (Lugar lugarListLugar : lugar.getLugarList()) {
                Lugar oldMunicipioID1OfLugarListLugar = lugarListLugar.getMunicipioID1();
                lugarListLugar.setMunicipioID1(lugar);
                lugarListLugar = em.merge(lugarListLugar);
                if (oldMunicipioID1OfLugarListLugar != null) {
                    oldMunicipioID1OfLugarListLugar.getLugarList().remove(lugarListLugar);
                    oldMunicipioID1OfLugarListLugar = em.merge(oldMunicipioID1OfLugarListLugar);
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

    public void edit(Lugar lugar) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Lugar persistentLugar = em.find(Lugar.class, lugar.getLugarID());
            Municipio municipioIDOld = persistentLugar.getMunicipioID();
            Municipio municipioIDNew = lugar.getMunicipioID();
            Lugar municipioID1Old = persistentLugar.getMunicipioID1();
            Lugar municipioID1New = lugar.getMunicipioID1();
            List<Persona> personaListOld = persistentLugar.getPersonaList();
            List<Persona> personaListNew = lugar.getPersonaList();
            List<Lugar> lugarListOld = persistentLugar.getLugarList();
            List<Lugar> lugarListNew = lugar.getLugarList();
            if (municipioIDNew != null) {
                municipioIDNew = em.getReference(municipioIDNew.getClass(), municipioIDNew.getId());
                lugar.setMunicipioID(municipioIDNew);
            }
            if (municipioID1New != null) {
                municipioID1New = em.getReference(municipioID1New.getClass(), municipioID1New.getLugarID());
                lugar.setMunicipioID1(municipioID1New);
            }
            List<Persona> attachedPersonaListNew = new ArrayList<Persona>();
            for (Persona personaListNewPersonaToAttach : personaListNew) {
                personaListNewPersonaToAttach = em.getReference(personaListNewPersonaToAttach.getClass(), personaListNewPersonaToAttach.getPersonaID());
                attachedPersonaListNew.add(personaListNewPersonaToAttach);
            }
            personaListNew = attachedPersonaListNew;
            lugar.setPersonaList(personaListNew);
            List<Lugar> attachedLugarListNew = new ArrayList<Lugar>();
            for (Lugar lugarListNewLugarToAttach : lugarListNew) {
                lugarListNewLugarToAttach = em.getReference(lugarListNewLugarToAttach.getClass(), lugarListNewLugarToAttach.getLugarID());
                attachedLugarListNew.add(lugarListNewLugarToAttach);
            }
            lugarListNew = attachedLugarListNew;
            lugar.setLugarList(lugarListNew);
            lugar = em.merge(lugar);
            if (municipioIDOld != null && !municipioIDOld.equals(municipioIDNew)) {
                municipioIDOld.getLugarList().remove(lugar);
                municipioIDOld = em.merge(municipioIDOld);
            }
            if (municipioIDNew != null && !municipioIDNew.equals(municipioIDOld)) {
                municipioIDNew.getLugarList().add(lugar);
                municipioIDNew = em.merge(municipioIDNew);
            }
            if (municipioID1Old != null && !municipioID1Old.equals(municipioID1New)) {
                municipioID1Old.getLugarList().remove(lugar);
                municipioID1Old = em.merge(municipioID1Old);
            }
            if (municipioID1New != null && !municipioID1New.equals(municipioID1Old)) {
                municipioID1New.getLugarList().add(lugar);
                municipioID1New = em.merge(municipioID1New);
            }
            for (Persona personaListOldPersona : personaListOld) {
                if (!personaListNew.contains(personaListOldPersona)) {
                    personaListOldPersona.setLugarNacimientoID(null);
                    personaListOldPersona = em.merge(personaListOldPersona);
                }
            }
            for (Persona personaListNewPersona : personaListNew) {
                if (!personaListOld.contains(personaListNewPersona)) {
                    Lugar oldLugarNacimientoIDOfPersonaListNewPersona = personaListNewPersona.getLugarNacimientoID();
                    personaListNewPersona.setLugarNacimientoID(lugar);
                    personaListNewPersona = em.merge(personaListNewPersona);
                    if (oldLugarNacimientoIDOfPersonaListNewPersona != null && !oldLugarNacimientoIDOfPersonaListNewPersona.equals(lugar)) {
                        oldLugarNacimientoIDOfPersonaListNewPersona.getPersonaList().remove(personaListNewPersona);
                        oldLugarNacimientoIDOfPersonaListNewPersona = em.merge(oldLugarNacimientoIDOfPersonaListNewPersona);
                    }
                }
            }
            for (Lugar lugarListOldLugar : lugarListOld) {
                if (!lugarListNew.contains(lugarListOldLugar)) {
                    lugarListOldLugar.setMunicipioID1(null);
                    lugarListOldLugar = em.merge(lugarListOldLugar);
                }
            }
            for (Lugar lugarListNewLugar : lugarListNew) {
                if (!lugarListOld.contains(lugarListNewLugar)) {
                    Lugar oldMunicipioID1OfLugarListNewLugar = lugarListNewLugar.getMunicipioID1();
                    lugarListNewLugar.setMunicipioID1(lugar);
                    lugarListNewLugar = em.merge(lugarListNewLugar);
                    if (oldMunicipioID1OfLugarListNewLugar != null && !oldMunicipioID1OfLugarListNewLugar.equals(lugar)) {
                        oldMunicipioID1OfLugarListNewLugar.getLugarList().remove(lugarListNewLugar);
                        oldMunicipioID1OfLugarListNewLugar = em.merge(oldMunicipioID1OfLugarListNewLugar);
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
                Integer id = lugar.getLugarID();
                if (findLugar(id) == null) {
                    throw new NonexistentEntityException("The lugar with id " + id + " no longer exists.");
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
            Lugar lugar;
            try {
                lugar = em.getReference(Lugar.class, id);
                lugar.getLugarID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The lugar with id " + id + " no longer exists.", enfe);
            }
            Municipio municipioID = lugar.getMunicipioID();
            if (municipioID != null) {
                municipioID.getLugarList().remove(lugar);
                municipioID = em.merge(municipioID);
            }
            Lugar municipioID1 = lugar.getMunicipioID1();
            if (municipioID1 != null) {
                municipioID1.getLugarList().remove(lugar);
                municipioID1 = em.merge(municipioID1);
            }
            List<Persona> personaList = lugar.getPersonaList();
            for (Persona personaListPersona : personaList) {
                personaListPersona.setLugarNacimientoID(null);
                personaListPersona = em.merge(personaListPersona);
            }
            List<Lugar> lugarList = lugar.getLugarList();
            for (Lugar lugarListLugar : lugarList) {
                lugarListLugar.setMunicipioID1(null);
                lugarListLugar = em.merge(lugarListLugar);
            }
            em.remove(lugar);
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

    public List<Lugar> findLugarEntities() {
        return findLugarEntities(true, -1, -1);
    }

    public List<Lugar> findLugarEntities(int maxResults, int firstResult) {
        return findLugarEntities(false, maxResults, firstResult);
    }

    private List<Lugar> findLugarEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Lugar.class));
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

    public Lugar findLugar(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Lugar.class, id);
        } finally {
            em.close();
        }
    }

    public int getLugarCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Lugar> rt = cq.from(Lugar.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
