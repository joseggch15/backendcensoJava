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
import Models.Entities.Lugar;
import Models.Entities.Municipio;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.transaction.UserTransaction;

/**
 *
 * @author USUARIO
 */
public class MunicipioJpaController implements Serializable {

    public MunicipioJpaController(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Municipio municipio) throws RollbackFailureException, Exception {
        if (municipio.getLugarList() == null) {
            municipio.setLugarList(new ArrayList<Lugar>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            List<Lugar> attachedLugarList = new ArrayList<Lugar>();
            for (Lugar lugarListLugarToAttach : municipio.getLugarList()) {
                lugarListLugarToAttach = em.getReference(lugarListLugarToAttach.getClass(), lugarListLugarToAttach.getLugarID());
                attachedLugarList.add(lugarListLugarToAttach);
            }
            municipio.setLugarList(attachedLugarList);
            em.persist(municipio);
            for (Lugar lugarListLugar : municipio.getLugarList()) {
                Municipio oldMunicipioIDOfLugarListLugar = lugarListLugar.getMunicipioID();
                lugarListLugar.setMunicipioID(municipio);
                lugarListLugar = em.merge(lugarListLugar);
                if (oldMunicipioIDOfLugarListLugar != null) {
                    oldMunicipioIDOfLugarListLugar.getLugarList().remove(lugarListLugar);
                    oldMunicipioIDOfLugarListLugar = em.merge(oldMunicipioIDOfLugarListLugar);
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

    public void edit(Municipio municipio) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Municipio persistentMunicipio = em.find(Municipio.class, municipio.getId());
            List<Lugar> lugarListOld = persistentMunicipio.getLugarList();
            List<Lugar> lugarListNew = municipio.getLugarList();
            List<Lugar> attachedLugarListNew = new ArrayList<Lugar>();
            for (Lugar lugarListNewLugarToAttach : lugarListNew) {
                lugarListNewLugarToAttach = em.getReference(lugarListNewLugarToAttach.getClass(), lugarListNewLugarToAttach.getLugarID());
                attachedLugarListNew.add(lugarListNewLugarToAttach);
            }
            lugarListNew = attachedLugarListNew;
            municipio.setLugarList(lugarListNew);
            municipio = em.merge(municipio);
            for (Lugar lugarListOldLugar : lugarListOld) {
                if (!lugarListNew.contains(lugarListOldLugar)) {
                    lugarListOldLugar.setMunicipioID(null);
                    lugarListOldLugar = em.merge(lugarListOldLugar);
                }
            }
            for (Lugar lugarListNewLugar : lugarListNew) {
                if (!lugarListOld.contains(lugarListNewLugar)) {
                    Municipio oldMunicipioIDOfLugarListNewLugar = lugarListNewLugar.getMunicipioID();
                    lugarListNewLugar.setMunicipioID(municipio);
                    lugarListNewLugar = em.merge(lugarListNewLugar);
                    if (oldMunicipioIDOfLugarListNewLugar != null && !oldMunicipioIDOfLugarListNewLugar.equals(municipio)) {
                        oldMunicipioIDOfLugarListNewLugar.getLugarList().remove(lugarListNewLugar);
                        oldMunicipioIDOfLugarListNewLugar = em.merge(oldMunicipioIDOfLugarListNewLugar);
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
                Integer id = municipio.getId();
                if (findMunicipio(id) == null) {
                    throw new NonexistentEntityException("The municipio with id " + id + " no longer exists.");
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
            Municipio municipio;
            try {
                municipio = em.getReference(Municipio.class, id);
                municipio.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The municipio with id " + id + " no longer exists.", enfe);
            }
            List<Lugar> lugarList = municipio.getLugarList();
            for (Lugar lugarListLugar : lugarList) {
                lugarListLugar.setMunicipioID(null);
                lugarListLugar = em.merge(lugarListLugar);
            }
            em.remove(municipio);
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

    public List<Municipio> findMunicipioEntities() {
        return findMunicipioEntities(true, -1, -1);
    }

    public List<Municipio> findMunicipioEntities(int maxResults, int firstResult) {
        return findMunicipioEntities(false, maxResults, firstResult);
    }

    private List<Municipio> findMunicipioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Municipio.class));
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

    public Municipio findMunicipio(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Municipio.class, id);
        } finally {
            em.close();
        }
    }

    public int getMunicipioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Municipio> rt = cq.from(Municipio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
