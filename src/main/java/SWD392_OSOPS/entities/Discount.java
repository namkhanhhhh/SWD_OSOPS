package SWD392_OSOPS.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "discount")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@Data
public class Discount {
  @Id
  @Column(name = "discount_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int discountId;

  @Column(name = "create_on")
  private LocalDateTime createdOn;

  @Column(name = "modify_on")
  private LocalDateTime modifyOn;

  @OneToMany(mappedBy = "discount", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private List<Shoes> shoes;

  @Column(name = "quantity")
  private int quantity;

  @Column(name = "value")
  private int value;

  @Column(name = "exp_date")
  private LocalDateTime expDate;

  @Column(name = "discount_status")
  private Boolean discountStatus;

}
