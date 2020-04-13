package com.grocery.userMicroservice.dtos;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.context.support.BeanDefinitionDsl.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "users", schema = "grocery")
public class Users implements UserDetails {
	private static final long serialVersionUID = 1L;
	
	@Transient
	private Users user;
	
	@Transient
	private Collection<GrantedAuthority> authorities;

	@Transient
	private List<Role> roles;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "id", updatable = false, nullable = false)
	private long id;

	@Column(name = "firstName")
	private String firstName;

	@Column(name = "lastName")
	private String lastName;

	@Column(name = "email", unique = true)
	private String email;

	@Column(name = "userName", unique = true)
	private String userName;

	@Column(name = "password", length = 60)
	private String password;

	@Column(name = "admin")
	private boolean admin;


	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}
	
	public void setAuthorities(Set<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	@Override
	public String getUsername() {
		if (this.user == null) {
			return null;
		}
		return this.user.getUserName();
	}

	@Override
	public boolean isAccountNonExpired() {
		return this.user.isAccountNonExpired();
	}

	@Override
	public boolean isAccountNonLocked() {
		return this.user.isAccountNonLocked();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return this.user.isCredentialsNonExpired();
	}

	@Override
	public boolean isEnabled() {
		return this.user.isEnabled();
	}

	public Users getUser() {
		return user;
	}

    @OneToMany(mappedBy = "users")
    public List<Role> getRoles() {
        return this.roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
    
	@Override
	public String toString() {
		return "CustomUserDetails [user=" + user + "]";
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

	public void setLastName(String lasttName) {
		this.lastName = lasttName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean getAdmin() {
		return admin;
	}

	public void setAdmin(boolean isAdmin) {
		this.admin = isAdmin;
	}
}
