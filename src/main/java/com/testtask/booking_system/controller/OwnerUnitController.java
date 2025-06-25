package com.testtask.booking_system.controller;

import com.testtask.booking_system.dto.request.UnitCreateDto;
import com.testtask.booking_system.dto.response.PagingResultDto;
import com.testtask.booking_system.dto.response.UnitResponseDto;
import com.testtask.booking_system.service.UnitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/owners")
@RequiredArgsConstructor
public class OwnerUnitController {

  private final UnitService unitService;

  @GetMapping("/{ownerId}/units")
  public ResponseEntity<PagingResultDto<UnitResponseDto>> findOwnerUnits(@PathVariable Long ownerId,
      @RequestParam(required = false, defaultValue = "0") Integer page,
      @RequestParam(required = false, defaultValue = "10") Integer size) {
    Pageable pageable = PageRequest.of(page, size);
    return unitService.findOwnerUnits(ownerId, pageable);
  }

  @PostMapping("/{ownerId}/units")
  public ResponseEntity<UnitResponseDto> createOwnerUnit(@PathVariable Long ownerId,
      @Valid @RequestBody UnitCreateDto unitCreateDto) {
    return unitService.createUnit(ownerId, unitCreateDto);
  }
}
