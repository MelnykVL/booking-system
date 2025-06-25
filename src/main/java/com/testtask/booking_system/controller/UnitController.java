package com.testtask.booking_system.controller;

import com.testtask.booking_system.dto.response.PagingResultDto;
import com.testtask.booking_system.dto.request.UnitPatchDto;
import com.testtask.booking_system.dto.response.UnitResponseDto;
import com.testtask.booking_system.service.UnitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/units")
@RequiredArgsConstructor
public class UnitController {

  private final UnitService unitService;

  @GetMapping("/{unitId}")
  public ResponseEntity<UnitResponseDto> findUnit(@PathVariable Long unitId) {
    return unitService.findUnit(unitId);
  }

  @GetMapping
  public ResponseEntity<PagingResultDto<UnitResponseDto>> findAllUnits(
      @RequestParam(required = false, defaultValue = "0") Integer page,
      @RequestParam(required = false, defaultValue = "10") Integer size) {
    Pageable pageable = PageRequest.of(page, size);
    return unitService.findAllUnits(pageable);
  }

  @PatchMapping("/{unitId}")
  public ResponseEntity<UnitResponseDto> patchUnit(@PathVariable Long unitId,
      @Valid @RequestBody UnitPatchDto unitPatchDto) {
    return unitService.patchUnit(unitId, unitPatchDto);
  }

  @DeleteMapping("/{unitId}")
  public ResponseEntity<Void> deleteUnit(@PathVariable Long unitId) {
    unitService.deleteUnit(unitId);
    return ResponseEntity.noContent().build();
  }
}
