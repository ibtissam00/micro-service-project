package org.sid.buillingservice.repository;

import org.sid.buillingservice.entities.ProductItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Collection;
@RepositoryRestResource
public interface ProductItemReposetory extends JpaRepository<ProductItem,Long> {
    //consulter la liste des productsItems
    public Collection<ProductItem> findAllByBillId(Long id);
}