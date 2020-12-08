package com.vehicle.repair.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.vehicle.repair.model.dto.MaintenanceDto;
import com.vehicle.repair.model.form.MaintenanceCreateForm;
import com.vehicle.repair.model.form.MaintenanceUpdateForm;
import com.vehicle.repair.persistence.entity.Maintenance;
import com.vehicle.repair.persistence.entity.Vehicle;
import com.vehicle.repair.persistence.repository.MaintenanceRepository;
import com.vehicle.repair.persistence.repository.VehicleRepository;
import com.vehicle.repair.util.AuthenticationUtil;
import com.vehicle.repair.util.EntityDtoMapperUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MaintenanceService {

	private final String transactDateFormat = "yyyy-MM-dd HH:mm:ss";

	private final MaintenanceRepository maintenanceRepository;
	private final VehicleRepository vehicleRepository;
	private final EntityDtoMapperUtil modelMapper;
	private final AuthenticationUtil authenticationUtil;

	public MaintenanceDto create(MaintenanceCreateForm maintenanceCreateForm, long vehicleId) throws ParseException {
		Vehicle vehicle = vehicleRepository.getOne(vehicleId);

		if(!vehicle.getUser().getUsername().equals(authenticationUtil.getAuthenticatedUser().getUsername()))
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "only vehicle owner can create maintenance");

		SimpleDateFormat transactionSdf = new SimpleDateFormat(transactDateFormat);
		transactionSdf.setTimeZone(TimeZone.getTimeZone("UTC"));

		Maintenance maintenance = new Maintenance();
		maintenance.setTransactionDate(transactionSdf.parse(maintenanceCreateForm.getTransactionDate()));
		maintenance.setCost(maintenanceCreateForm.getCost());
		maintenance.setComments(maintenanceCreateForm.getComments());
		maintenance.setVehicle(vehicle);

		maintenanceRepository.save(maintenance);

		return modelMapper.map(maintenance, MaintenanceDto.class);
	}

	public List<MaintenanceDto> retreiveAll(){
		List<Maintenance> maintenances = maintenanceRepository.findAllOwned(authenticationUtil.getAuthenticatedUser());

		if(maintenances.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "no maintenance found");

		return modelMapper.mapAll(maintenances, MaintenanceDto.class);
	}

	public MaintenanceDto retrieve(long maintenanceId){
		Maintenance maintenance = maintenanceRepository.getOne(maintenanceId);

		if(maintenance.isDeleted())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "maintenance is deleted");

		return modelMapper.map(maintenance, MaintenanceDto.class);
	}

	public MaintenanceDto update(MaintenanceUpdateForm maintenanceUpdateForm, long maintenanceid) throws ParseException {
		Maintenance maintenance = maintenanceRepository.getOne(maintenanceid);

		if(maintenance.isDeleted())
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "maintenance is deleted");

		if(maintenance.getVehicle().getUser().getId() != authenticationUtil.getAuthenticatedUser().getId())
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "only maintenance owner can update");

		if(!StringUtils.isEmpty(maintenanceUpdateForm.getTransactionDate())){
			SimpleDateFormat transactionSdf = new SimpleDateFormat(transactDateFormat);
			transactionSdf.setTimeZone(TimeZone.getTimeZone("UTC"));
			maintenance.setTransactionDate(transactionSdf.parse(maintenanceUpdateForm.getTransactionDate()));
		}

		if(!StringUtils.isEmpty(maintenanceUpdateForm.getComments())) maintenance.setComments(maintenanceUpdateForm.getComments());

		if(maintenanceUpdateForm.getCost() != null) maintenance.setCost(maintenanceUpdateForm.getCost());

		return modelMapper.map(maintenanceRepository.save(maintenance), MaintenanceDto.class);
	}

	public MaintenanceDto delete(long maintenanceId){
		Maintenance maintenance = maintenanceRepository.getOne(maintenanceId);
		maintenance.setDeleted(true);

		return modelMapper.map(maintenanceRepository.save(maintenance), MaintenanceDto.class);
	}
}
