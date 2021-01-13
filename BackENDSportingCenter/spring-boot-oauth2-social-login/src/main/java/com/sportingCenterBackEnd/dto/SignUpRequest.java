package com.sportingCenterBackEnd.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.sportingCenterBackEnd.validator.PasswordMatches;

import lombok.Data;

/**
 * @author Chinna
 * @since 26/3/18
 */
@Data
@PasswordMatches
public class SignUpRequest {

	private Long userID;

	private String providerUserId;

	@NotEmpty
	private String displayName;

	@NotEmpty
	private String email;

	private boolean enabled;

	private String number;

	private String dataNascita;

	private String abbonamento;

	private String dataScadenza;

	private SocialProvider socialProvider;

	@Size(min = 6, message = "{Size.userDto.password}")
	private String password;

	@NotEmpty
	private String matchingPassword;

	public SignUpRequest(String providerUserId, String displayName, String email, String password, SocialProvider socialProvider,
						 boolean enabled, String number, String dataNascita,String abbonamento, String dataScadenza) {
		this.providerUserId = providerUserId;
		this.displayName = displayName;
		this.email = email;
		this.password = password;
		this.socialProvider = socialProvider;
		this.enabled = enabled;
		this.number = number;
		this.dataNascita = dataNascita;
		this.abbonamento= abbonamento;
		this.dataScadenza = dataScadenza;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public String getDataNascita() {
		return dataNascita;
	}

	public String getAbbonamento(){
		return abbonamento;
	}

	public String getDataScadenza() {
		return dataScadenza;
	}

	public static Builder getBuilder() {
		return new Builder();
	}

	public Long getUserID() {
		return userID;
	}

	public void setUserID(Long userID) {
		this.userID = userID;
	}

	public String getProviderUserId() {
		return providerUserId;
	}

	public void setProviderUserId(String providerUserId) {
		this.providerUserId = providerUserId;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public SocialProvider getSocialProvider() {
		return socialProvider;
	}

	public void setSocialProvider(SocialProvider socialProvider) {
		this.socialProvider = socialProvider;
	}

	public String getPassword() {
		return password;
	}

	public String getNumber() {
		return number;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMatchingPassword() {
		return matchingPassword;
	}

	public void setMatchingPassword(String matchingPassword) {
		this.matchingPassword = matchingPassword;
	}


	public static class Builder {
		private String providerUserID;
		private String displayName;
		private String email;
		private String password;
		private String number;
		private SocialProvider socialProvider;
		private boolean enabled;
		private String dataNascita;
		private String abbonamento;
		private String dataScadenza;

		public Builder addProviderUserID(final String userID) {
			this.providerUserID = userID;
			return this;
		}

		public Builder addDisplayName(final String displayName) {
			this.displayName = displayName;
			return this;
		}

		public Builder addEmail(final String email) {
			this.email = email;
			return this;
		}

		public Builder addPassword(final String password) {
			this.password = password;
			return this;
		}

		public Builder addSocialProvider(final SocialProvider socialProvider) {
			this.socialProvider = socialProvider;
			return this;
		}

		public Builder addNumber(final String number) {
			this.number = number;
			return this;
		}

		public Builder addDataNascita(final String dataNascita) {
			this.dataNascita = dataNascita;
			return this;
		}

		public Builder addAbbonamento(final String abbonamento) {
			this.abbonamento = abbonamento;
			return this;
		}

		public Builder addDataScadenza(final String dataScadenza) {
			this.dataScadenza = dataScadenza;
			return this;
		}

		public SignUpRequest build() {
			return new SignUpRequest(providerUserID, displayName, email, password, socialProvider, enabled, number, dataNascita, abbonamento,dataScadenza);
		}
	}
}
