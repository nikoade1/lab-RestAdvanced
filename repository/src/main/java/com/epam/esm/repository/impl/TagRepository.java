package com.epam.esm.repository.impl;

import com.epam.esm.model.Tag;
import com.epam.esm.repository.TagDAO;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

@Repository
public class TagRepository implements TagDAO {

    private final EntityManager entityManager;
    private final EntityManagerFactory emf;

    public TagRepository() {
        this.emf = Persistence.createEntityManagerFactory("pu");
        this.entityManager = this.emf.createEntityManager();
    }

    @Override
    public List<Tag> findAll(int page, int size) {
        this.entityManager.getTransaction().begin();
        Query query = this.entityManager.createQuery("Select t from Tag t");
        query.setFirstResult((page - 1) * size);
        query.setMaxResults(size);
        this.entityManager.getTransaction().commit();
        return query.getResultList();
    }

    @Override
    public Tag add(Tag tag) {
        this.entityManager.getTransaction().begin();
        Tag merged = this.entityManager.merge(tag);
        this.entityManager.getTransaction().commit();
        return merged;
    }

    @Override
    public Tag find(Long id) {
        return this.entityManager.find(Tag.class, id);
    }

    @Override
    public List<Tag> findByName(String name) {
        this.entityManager.getTransaction().begin();
        Query query = this.entityManager.createQuery("Select t from Tag t where t.name = '" + name + "'");
        this.entityManager.getTransaction().commit();
        return query.getResultList();
    }


    @Override
    public void delete(Tag tag) {
        this.entityManager.getTransaction().begin();
        this.entityManager.remove(tag);
        tag.getGiftCertificates().forEach(gc -> {
            gc.removeTag(tag.getName());
            this.entityManager.merge(gc);
        });
        this.entityManager.flush();
        this.entityManager.getTransaction().commit();
    }

    @Override
    public void close() {
        this.entityManager.close();
        this.emf.close();
    }

    @Override
    public Tag getMostWidelyUsedTag() {
//        String querry = "SELECT t.id, t.name \n" +
//                " FROM tags t \n" +
//                " INNER JOIN giftcertificate_tag gct on t.id = gct.tag_id \n" +
//                " INNER JOIN orders o on o.giftcertificate_id = gct.giftcertificate_id \n" +
//                " WHERE o.user_id = " + id + " \n" +
//                " ORDER BY SUM(o.cost) DESC \n " +
//                " LIMIT 1 \n" +
//                " GROUP BY t.id, t.name \n" +
//                " ORDER BY COUNT(t.name) desc \n" +
//                " LIMIT 1";

        String queryString = "SELECT t.id, t.name\n" +
                "        from orders \n" +
                "        inner join users u on u.id = orders.user_id\n" +
                "        inner join giftcertificate_tag gct on orders.giftCertificate_id = gct.giftcertificate_id\n" +
                "        inner join tags t on t.id = gct.tag_id\n" +
                "        WHERE orders.user_id = (\n" +
                "        SELECT orders.user_id " +
                "        FROM orders \n" +
                "        GROUP BY orders.user_id\n" +
                "ORDER BY SUM(orders.cost) DESC \n" +
                "LIMIT 1\n" +
                ") \n" +
                "group by t.id, t.name \n" +
                "order by COUNT(t.name) desc \n" +
                "limit 1";

        Query query = entityManager.createNativeQuery(queryString, Tag.class);
        return (Tag) query.getSingleResult();
    }
}
