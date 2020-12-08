package com.vehicle.repair.persistence.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vehicle.repair.persistence.entity.Maintenance;
import com.vehicle.repair.persistence.entity.User;

@Repository
public interface MaintenanceRepository extends JpaRepository<Maintenance,Long> {

	@Query("SELECT m FROM Maintenance m LEFT JOIN m.vehicle v WHERE v.user = :userid AND m.isDeleted = 0")
	List<Maintenance> findAllOwned(@Param("userid") User userid);
}
