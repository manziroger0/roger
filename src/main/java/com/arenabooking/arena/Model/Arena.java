package com.arenabooking.arena.Model;

import javax.persistence.*;

@Entity
public class Arena {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String clientName;

	private String phoneNumber;

	private String email;

	private String ticketclass;
	@Lob
	@Column(name = "profileImage", columnDefinition = "LONGBLOB")
	private byte[] profilePhoto;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTicketclass() {
		return ticketclass;
	}

	public void setTicketclass(String ticketclass) {
		this.ticketclass = ticketclass;
	}

	public byte[] getProfilePhoto() {
		return profilePhoto;
	}

	public void setProfilePhoto(byte[] profilePhoto) {
		this.profilePhoto = profilePhoto;
	}
}