package com.jci.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_states_new", schema = "dbo")
public class StateList {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String state_code;
	private String state_name;
	private String state_type;
	private String gov_state_code;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getState_code() {
		return state_code;
	}

	public void setState_code(String state_code) {
		this.state_code = state_code;
	}

	public String getState_name() {
		return state_name;
	}

	public void setState_name(String state_name) {
		this.state_name = state_name;
	}

	public String getState_type() {
		return state_type;
	}

	public void setState_type(String state_type) {
		this.state_type = state_type;
	}

	public String getGov_state_code() {
		return gov_state_code;
	}

	public void setGov_state_code(String gov_state_code) {
		this.gov_state_code = gov_state_code;
	}

	@Override
	public String toString() {
		return "StateList [id=" + id + ", state_code=" + state_code + ", state_name=" + state_name + ", state_type="
				+ state_type + ", gov_state_code=" + gov_state_code + "]";
	}

}
