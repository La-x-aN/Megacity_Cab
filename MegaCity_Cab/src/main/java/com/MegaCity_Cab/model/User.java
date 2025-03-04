package com.MegaCity_Cab.model;

public class User {
    public enum Role { USER, RIDER, ADMIN;

	public boolean equalsIgnoreCase(String string) {
		// TODO Auto-generated method stub
		return false;
	} } 

    private int id;
    private String name;
    private String nic;
    private String phone;
    private String email;
    private String passwordHash;
    private Role role;

    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getNic() { return nic; }
    public void setNic(String nic) { this.nic = nic; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { 
        this.passwordHash = passwordHash; 
    }

    public Role getRole() { return role; }
    public void setRole(Role role) { 
        this.role = role; 
    }

    // Optional: Keep this ONLY if you need to set roles from strings
    public void setRole(String roleStr) {
        try {
            this.role = Role.valueOf(roleStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            this.role = Role.USER; // Or throw an error
        }
    }
}