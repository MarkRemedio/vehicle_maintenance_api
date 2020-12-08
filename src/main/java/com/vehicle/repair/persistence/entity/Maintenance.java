package com.vehicle.repair.persistence.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "maintenance")
@DynamicUpdate
public class Maintenance implements Serializable {
	private static final long serialVersionUID = 6237381894735815723L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(nullable = false)
	private Date transactionDate;

	@Column(nullable = false)
	private BigDecimal cost;

	@Column
	private String comments;

	@Column(columnDefinition="tinyint(1) default 0")
	private boolean isDeleted;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "vehicle_id", referencedColumnName = "id", nullable = false)
	private Vehicle vehicle;
}
