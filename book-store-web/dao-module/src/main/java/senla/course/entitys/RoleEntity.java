package senla.course.entitys;


import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "roles")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roleId;

    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<UserEntity> users;

    public Integer getRoleId() {
        return roleId;
    }

    public String getName() {
        return name;
    }

    public Set<UserEntity> getUsers() {
        return users;
    }
}
