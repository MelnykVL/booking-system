package com.testtask.booking_system.service;

import com.testtask.booking_system.dto.request.UnitCreateDto;
import com.testtask.booking_system.dto.request.UnitPatchDto;
import com.testtask.booking_system.dto.request.UnitSearchCriteriaDto;
import com.testtask.booking_system.dto.response.PagingResultDto;
import com.testtask.booking_system.dto.response.UnitResponseDto;
import com.testtask.booking_system.entity.Unit;
import com.testtask.booking_system.entity.User;
import com.testtask.booking_system.enums.EventLogAction;
import com.testtask.booking_system.exception.ResourceNotFountException;
import com.testtask.booking_system.mapper.PageMapper;
import com.testtask.booking_system.mapper.UnitMapper;
import com.testtask.booking_system.repository.UnitRepository;
import com.testtask.booking_system.repository.UserRepository;
import com.testtask.booking_system.specification.UnitSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UnitService {

  private final UnitRepository unitRepository;
  private final UserRepository userRepository;
  private final UnitMapper unitMapper;
  private final AuditService auditService;
  private final PageMapper pageMapper;

  @Transactional(readOnly = true)
  public ResponseEntity<UnitResponseDto> findUnit(Long unitId) {
    Unit unit = unitRepository.findById(unitId)
        .orElseThrow(() -> new ResourceNotFountException(Unit.class.getSimpleName(), unitId));
    UnitResponseDto unitResponseDto = unitMapper.toUnitResponseDto(unit);

    return ResponseEntity.ok(unitResponseDto);
  }

  @Transactional(readOnly = true)
  public ResponseEntity<PagingResultDto<UnitResponseDto>> findUnits(UnitSearchCriteriaDto unitSearchCriteriaDto,
      Pageable pageable) {
    Specification<Unit> spec =
        UnitSpecification.availableBetween(unitSearchCriteriaDto.availableFrom(), unitSearchCriteriaDto.availableTo())
            .and(UnitSpecification.betweenPrice(unitSearchCriteriaDto.minTotalPrice(),
                unitSearchCriteriaDto.maxTotalPrice(), unitSearchCriteriaDto.availableFrom(),
                unitSearchCriteriaDto.availableTo()))
            .and(UnitSpecification.hasNumberOfRooms(unitSearchCriteriaDto.numberOfRooms()))
            .and(UnitSpecification.hasFloor(unitSearchCriteriaDto.floor()))
            .and(UnitSpecification.hasType(unitSearchCriteriaDto.type()));
    Page<Unit> pageUnits = unitRepository.findAll(spec, pageable);
    PagingResultDto<UnitResponseDto> pagingResultUnitsDto =
        pageMapper.toPagingResultDto(pageUnits, unitMapper::toUnitResponseDto);

    return ResponseEntity.ok(pagingResultUnitsDto);
  }

  @Transactional(readOnly = true)
  public ResponseEntity<PagingResultDto<UnitResponseDto>> findOwnerUnits(Long ownerId, Pageable pageable) {
    if (!userRepository.existsById(ownerId)) {
      String errorMessage = String.format("Owner with id '%d' does not exist", ownerId);
      throw new ResourceNotFountException(errorMessage);
    }
    Page<Unit> pageOwnerUnits = unitRepository.findAllByOwnerId(ownerId, pageable);
    PagingResultDto<UnitResponseDto> pagingResultUnitsDto =
        pageMapper.toPagingResultDto(pageOwnerUnits, unitMapper::toUnitResponseDto);

    return ResponseEntity.ok(pagingResultUnitsDto);
  }

  public ResponseEntity<UnitResponseDto> createUnit(Long ownerId, UnitCreateDto unitCreateDto) {
    User owner = userRepository.findById(ownerId).orElseThrow(() -> {
      String errorMessage = String.format("Owner with id '%d' does not exist", ownerId);
      return new ResourceNotFountException(errorMessage);
    });
    Unit unit = unitMapper.fromUnitCreateDto(unitCreateDto);
    unit.setOwner(owner);
    UnitResponseDto unitResponseDto = unitMapper.toUnitResponseDto(unitRepository.save(unit));
    auditService.log(Unit.class, unit.getId(), EventLogAction.CREATE_UNIT.name(), unit);

    return ResponseEntity.ok(unitResponseDto);
  }

  public ResponseEntity<UnitResponseDto> patchUnit(Long unitId, UnitPatchDto unitPatchDto) {
    Unit unit = unitRepository.findById(unitId)
        .orElseThrow(() -> new ResourceNotFountException(Unit.class.getSimpleName(), unitId));
    unit = unitMapper.fromUnitPatchDto(unitPatchDto, unit);
    UnitResponseDto unitResponseDto = unitMapper.toUnitResponseDto(unitRepository.save(unit));
    auditService.log(Unit.class, unit.getId(), EventLogAction.UPDATE_UNIT.name(), unit);

    return ResponseEntity.ok(unitResponseDto);
  }

  public void deleteUnit(Long unitId) {
    if (!unitRepository.existsById(unitId)) {
      throw new ResourceNotFountException(Unit.class.getSimpleName(), unitId);
    }
    unitRepository.deleteById(unitId);
    auditService.log(Unit.class, unitId, EventLogAction.DELETE_UNIT.name(), unitId);
  }
}
