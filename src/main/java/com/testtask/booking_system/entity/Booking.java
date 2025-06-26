package com.testtask.booking_system.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.testtask.booking_system.enums.BookingStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.UtilityClass;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

@Entity
@Table(name = "bookings", schema = "booking_system")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "unit_id")
  @JsonBackReference
  private Unit unit;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  @JsonBackReference
  private User user;

  @Column(nullable = false)
  private LocalDate checkInOn;

  @Column(nullable = false)
  private LocalDate checkOutOn;

  @Column(nullable = false, precision = 10, scale = 2)
  private BigDecimal totalCost;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  @JdbcTypeCode(SqlTypes.NAMED_ENUM)
  private BookingStatus status = BookingStatus.RESERVED;

  @Column(nullable = false)
  private Instant expiresAt;

  @CreationTimestamp
  private Instant createdAt;

  @UpdateTimestamp
  private Instant updatedAt;

  @OneToOne(mappedBy = "booking")
  @JsonManagedReference
  private Payment payment;

  @UtilityClass
  public static class Fields {

    public final String CHECK_IN_ON = "checkInOn";
    public final String CHECK_OUT_ON = "checkOutOn";
    public final String TOTAL_COST = "totalCost";
    public final String STATUS = "status";
    public final String EXPIRES_AT = "expiresAt";
    public final String CREATED_AT = "createdAt";
  }
}
