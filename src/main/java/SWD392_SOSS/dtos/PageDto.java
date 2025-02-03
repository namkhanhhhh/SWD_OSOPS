package SWD392_SOSS.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import SWD392_SOSS.entities.Role;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class PageDto {
    private List resultList;
    private int totalPage;
    private int currentPage;
    private int size;
    private List<Role> roles;
}
