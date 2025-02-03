package SWD392_SOSS.dtos;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhoneRevenueDTO {
    private String phoneName;
    private Long total;
}
