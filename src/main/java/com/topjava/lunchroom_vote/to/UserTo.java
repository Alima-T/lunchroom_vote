package com.topjava.lunchroom_vote.to;

import com.fasterxml.jackson.annotation.JsonView;
import com.topjava.lunchroom_vote.json.View;
import com.topjava.lunchroom_vote.model.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.beans.ConstructorProperties;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Set;

/**
 * @Alima-T 12/09/2022
 */
@NoArgsConstructor
@Data
public class UserTo extends BaseTo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotBlank
    @Size(min = 2, max = 128)
    @JsonView(View.JsonREST.class)
    private String name;

    @Email
    @NotBlank
    @Size(max = 128)
    @JsonView(View.JsonREST.class)
    private String email;

    @NotBlank
    @Size(min = 5, max = 128)
    private String password;

    @JsonView(View.JsonREST.class)
    private boolean enabled = true;

    @JsonView(View.JsonREST.class)
    private LocalDate registered = LocalDate.now(ZoneId.systemDefault());

    @JsonView(View.JsonREST.class)
    private Set<Role> roles;

    @ConstructorProperties({"id", "name", "email", "password", "enabled", "registered", "roles"})
    public UserTo(Integer id, String name, String email, String password, boolean enabled, LocalDate registered, Set<Role> roles) {
        super(id);
        this.name = name;
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.registered = registered;
        this.roles = roles;
    }

}
