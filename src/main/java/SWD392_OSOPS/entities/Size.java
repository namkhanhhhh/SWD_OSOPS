package SWD392_OSOPS.entities;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "size")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@Data
public class Size {
    @Id
    @Column(name = "size_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sizeId;

    @Column(name = "size_number")
    private String sizeName;

    @ManyToMany(mappedBy = "sizes")
    @ToString.Exclude
    private List<Shoes> shoes;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Size size = (Size) o;
        return Objects.equals(sizeId, size.sizeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sizeId);
    }
}
