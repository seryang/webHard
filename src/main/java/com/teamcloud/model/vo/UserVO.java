package com.teamcloud.model.vo;

public class UserVO {
	private String email;
	private String name;
	private String image;
	private String teamName;

	public UserVO(String email, String name, String image, String teamName) {
		this.email = email;
		this.name = name;
		this.image = image;
		this.teamName = teamName;
	}

	

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}public UserVO(String email, String name, String profile_image) {
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserVO [email=").append(email).append(", name=").append(name).append(", image=")
				.append(image).append(", teamName=").append(teamName).append("]");
		return builder.toString();
	}
}