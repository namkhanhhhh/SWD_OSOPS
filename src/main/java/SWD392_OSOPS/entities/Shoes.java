package SWD392_OSOPS.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "shoes")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@Data
public class Shoes {
  @Id
  @Column(name = "shoes_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int shoesId;

  @Column(name = "shoes_name")
  private String productName;

  @Column(name = "price")
  private double price;

  @Column(name = "status")
  private Boolean status;

  @Column(name = "release_date")
  private LocalDate releaseDate;

  @Column(name = "created_on")
  private LocalDate createdOn;

  @Column(name = "modified_on")
  private LocalDate modifiedOn;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "brand_id", referencedColumnName = "brand_id")

  @ToString.Exclude
  private Brand brand;

  @OneToOne(mappedBy = "shoes", cascade = CascadeType.ALL)
  @ToString.Exclude
  private Picture picture;

  @OneToMany(mappedBy = "shoes", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private List<CartItem> items;

  @OneToMany(mappedBy = "shoes", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private List<OrderItem> orderItems;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "discount_id", referencedColumnName = "discount_id")
  @JsonIgnore
  private Discount discount;

  @ManyToMany
  @ToString.Exclude
  @JoinTable(
          name = "shoes_size",
          joinColumns = @JoinColumn(name = "shoes_id"),
          inverseJoinColumns = @JoinColumn(name = "size_id")
  )
  private List<Size> sizes;
  @Override
  public String toString() {
    return "Shoe{" +
            "id=" + shoesId +
            ", name='" + productName + '\'' +
            ", category=" + (brand != null ? brand.getBrandId() : "null") + // Chỉ in ID
            '}';
  }



//  public Shoes createShoes(String productName, double price, LocalDate releaseDate, Brand brand, Picture picture, List<Size> sizes) {
//    return Shoes.builder()
//            .productName(productName)
//            .price(price)
//            .releaseDate(releaseDate)
//            .brand(brand)
//            .picture(picture)
//            .sizes(List<Size> sizes)
//            .build();
//  }
}
