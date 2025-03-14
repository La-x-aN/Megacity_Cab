package com.MegaCity_Cab.model;

public class User {
    public enum Role { USER, RIDER, ADMIN }

    private int id;
    private String name;
    private String nic;
    private String phone;
    private String email;
    private String passwordHash;
    private Role role;


    public User() {
        this.role = Role.USER; 
    }


    public int getId() { return id; }
    public String getName() { return name; }
    public String getNic() { return nic; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }
    public Role getRole() { return role; }


    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setNic(String nic) { this.nic = nic; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setEmail(String email) { this.email = email; }
    public void setPasswordHash(String passwordHash) { 
        this.passwordHash = passwordHash; 
    }


    public void setRole(Role role) {
        this.role = (role != null) ? role : Role.USER;
    }

    public void setRoleFromString(String roleStr) {
        if (roleStr == null || roleStr.trim().isEmpty()) {
            this.role = Role.USER;
            return;
        }

        try {
            this.role = Role.valueOf(roleStr.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            this.role = Role.USER;
        }
    }
}
