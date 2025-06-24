package com.testtask.booking_system.controller;

import com.testtask.booking_system.dto.UnitPatchDto;
import com.testtask.booking_system.dto.UnitResponseDto;
import com.testtask.booking_system.service.UnitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
