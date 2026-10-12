package com.demo.entity;

import com.demo.entity.jwt.Authority;
import com.demo.service.UserService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.sql.ClientInfoStatus;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "usuarios")
public class User implements Serializable, UserDetails {




    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String firstName;

    private String lastName;

    @Email
    private String email;

    private String username;

    private  String password;
    @Transient
    private String confirmPassword;

    private boolean estado;

    //codigo para el bloqueo de mas de 15 solicitudes, lo voy a dejarr po el momento
/*    @Column( nullable = false, columnDefinition = "int default 15")
    private int cantDiasVacaciones; //por defecto va a ser 15
    @Column( nullable = false, columnDefinition = "int default 0")
    private int diasSolicitados; // por defecto 0*/

    //no se puede tener el mismo role dos veces
    //laazy significa "solo los obtengo cuand"
    @ManyToMany(fetch=FetchType.EAGER)
    @JsonIgnore
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Solicitud> solicituds;

    public User() {

        //this.setCantDiasVacaciones(15);
        //this.setDiasSolicitados(0);

    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Authority> autoridades = new HashSet<>();
        this.roles.forEach(role -> {
            autoridades.add(new Authority(role.getName()));
        });
        return autoridades;
    }
    public void setUsername(String username) {
        this.username = username;
    }



    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public List<Solicitud> getSolicituds() {
        return solicituds;
    }

    public void setSolicituds(List<Solicitud> solicituds) {
        this.solicituds = solicituds;
    }

/*    public int getCantDiasVacaciones() {
        return cantDiasVacaciones;
    }

    public void setCantDiasVacaciones(int cantDiasVacaciones) {
        this.cantDiasVacaciones = cantDiasVacaciones;
    }

    public int getDiasSolicitados() {
        return diasSolicitados;
    }

    public void setDiasSolicitados(int diasSolicitados) {
        this.diasSolicitados = diasSolicitados;
    }*/
}
