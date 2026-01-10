package kg.attractor.exam_7.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private Integer id;
    private String phoneNumber;
    private String username;
    private String password;
    private Integer roleId;
    private Boolean enabled;
}
