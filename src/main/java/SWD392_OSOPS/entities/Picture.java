package SWD392_OSOPS.entities;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "picture")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@Data
public class Picture {

//    public Picture createPicture(String main, String front, String back, String site) {
//
//        return new Picture(pictureId,main,front,back,site);
//    }

    @Id
    @Column(name = "picture_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pictureId;

    @Column(name = "main")
    private String main;

    @Column(name = "front")
    private String front;

    @Column(name = "back")
    private String back;

    @Column(name = "side")
    private String site;

    @OneToOne
    @JoinColumn(name = "shoes_id", unique = true) // Unique đảm bảo 1-1
    private Shoes shoes;

}
