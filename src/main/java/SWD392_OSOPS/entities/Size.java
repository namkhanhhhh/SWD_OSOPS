package SWD392_OSOPS.entities;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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
    private List<Shoes> shoes;
}
