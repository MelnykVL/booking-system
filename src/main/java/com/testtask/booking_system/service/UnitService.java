package com.testtask.booking_system.service;

import com.testtask.booking_system.dto.PagingResultDto;
import com.testtask.booking_system.dto.UnitCreateDto;
import com.testtask.booking_system.dto.UnitPatchDto;
import com.testtask.booking_system.dto.UnitResponseDto;
import com.testtask.booking_system.entity.Unit;
import com.testtask.booking_system.entity.User;
import com.testtask.booking_system.exception.ResourceNotFountException;
import com.testtask.booking_system.mapper.PageMapper;
import com.testtask.booking_system.mapper.UnitMapper;
import com.testtask.booking_system.repository.UnitRepository;
import com.testtask.booking_system.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
  private final PageMapper pageMapper;

  @Transactional(readOnly = true)
  public ResponseEntity<UnitResponseDto> findUnit(Long unitId) {
    Unit unit = unitRepository.findById(unitId)
        .orElseThrow(() -> new ResourceNotFountException(Unit.class.getSimpleName(), unitId));
    UnitResponseDto unitResponseDto = unitMapper.toUnitResponseDto(unit);

    return ResponseEntity.ok(unitResponseDto);
  }

  @Transactional(readOnly = true)
  public ResponseEntity<PagingResultDto<UnitResponseDto>> findAllUnits(Pageable pageable) {
    Page<Unit> pageUnits = unitRepository.findAll(pageable);
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

  public ResponseEntity<UnitResponseDto> createOwnerUnit(Long ownerId, UnitCreateDto unitCreateDto) {
    User owner = userRepository.findById(ownerId).orElseThrow(() -> {
      String errorMessage = String.format("Owner with id '%d' does not exist", ownerId);
      return new ResourceNotFountException(errorMessage);
    });
    Unit unit = unitMapper.fromUnitCreateDto(unitCreateDto);
    unit.setOwner(owner);
    UnitResponseDto unitResponseDto = unitMapper.toUnitResponseDto(unitRepository.save(unit));

    return ResponseEntity.ok(unitResponseDto);
  }

  public ResponseEntity<UnitResponseDto> patchUnit(Long unitId, UnitPatchDto unitPatchDto) {
    Unit unit = unitRepository.findById(unitId)
        .orElseThrow(() -> new ResourceNotFountException(Unit.class.getSimpleName(), unitId));
    unit = unitMapper.fromUnitPatchDto(unitPatchDto, unit);
    UnitResponseDto unitResponseDto = unitMapper.toUnitResponseDto(unitRepository.save(unit));
    return ResponseEntity.ok(unitResponseDto);
  }

  public void deleteUnit(Long unitId) {
    if (!unitRepository.existsById(unitId)) {
      throw new ResourceNotFountException(Unit.class.getSimpleName(), unitId);
    }
    unitRepository.deleteById(unitId);
  }
}
