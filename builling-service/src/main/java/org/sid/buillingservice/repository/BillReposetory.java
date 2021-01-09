package org.sid.buillingservice.repository;

import org.sid.buillingservice.entities.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface BillReposetory  extends JpaRepository<Bill,Long> {
}
