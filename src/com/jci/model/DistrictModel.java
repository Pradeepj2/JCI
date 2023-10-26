package com.jci.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_districts_new", schema = "dbo")
public class DistrictModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	@Column(name = "dist_name")
	private String dist_name ;
	
	@Column(name = "new_dist_code")
	private String new_dist_code ;
	
	@Column(name = "area_code")
	private String area_code ;

	@Column(name = "state_code")
	private int state_code;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDist_name() {
		return dist_name;
	}

	public void setDist_name(String dist_name) {
		this.dist_name = dist_name;
	}

	public String getNew_dist_code() {
		return new_dist_code;
	}

	public void setNew_dist_code(String new_dist_code) {
		this.new_dist_code = new_dist_code;
	}

	public String getArea_code() {
		return area_code;
	}

	public void setArea_code(String area_code) {
		this.area_code = area_code;
	}

	public int getState_code() {
		return state_code;
	}

	public void setState_code(int state_code) {
		this.state_code = state_code;
	}

	@Override
	public String toString() {
		return "DistrictModel [id=" + id + ", dist_name=" + dist_name + ", new_dist_code=" + new_dist_code
				+ ", area_code=" + area_code + ", state_code=" + state_code + "]";
	}







}
