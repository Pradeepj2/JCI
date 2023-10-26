package com.jci.model; 
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.jetbrains.annotations.NotNull;

@Table(name = "jcicredit_note" , schema = "dbo")
public class CreditNotes {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "Crn_id")
	    private BigInteger crnId; // Big int PK, AI

		@Column(name = "Credit_note_date", length = 50)
		@NotNull
		private String crnDate;

	    @Column(name = "Credit_note_no",unique = true,length = 50)
	    @NotNull
	    private String crnNo; 

	    @Column(name = "Shipment_details",length = 250)
	    @NotNull
	    private String shipmentDetails; 
	    
	    @Column(name = "BOS_qty")
	    @NotNull
	    private Double bosQty; 
	    
	    @Column(name = "Actual_qty")
	    @NotNull
	    private Double actualQty;
	    
	    @Column(name = "Short_qty")
	    @NotNull
	    private Double shortQty; 
	    
	    @Column(name = "Credit_note_amount")
	    @NotNull
	    private Double crnAmount; 
	    
	    @Column(name = "Ro_id")
	    @NotNull
	    private String RoId;

	    @Column(name = "Crn_status")
	    private int crnStatus = 0;

	    @Column(name = "Created_by")
	    private String Created_by;

	    @Column(name = "Creation_date")
	    @NotNull
	    private Date creationDate;

		public BigInteger getCrnId() {
			return crnId;
		}

		public void setCrnId(BigInteger crnId) {
			this.crnId = crnId;
		}

		public String getCrnDate() {
			return crnDate;
		}

		public void setCrnDate(String crnDate) {
			this.crnDate = crnDate;
		}

		public String getCrnNo() {
			return crnNo;
		}

		public void setCrnNo(String crnNo) {
			this.crnNo = crnNo;
		}

		public String getShipmentDetails() {
			return shipmentDetails;
		}

		public void setShipmentDetails(String shipmentDetails) {
			this.shipmentDetails = shipmentDetails;
		}

		public Double getBosQty() {
			return bosQty;
		}

		public void setBosQty(Double bosQty) {
			this.bosQty = bosQty;
		}

		public Double getActualQty() {
			return actualQty;
		}

		public void setActualQty(Double actualQty) {
			this.actualQty = actualQty;
		}

		public Double getShortQty() {
			return shortQty;
		}

		public void setShortQty(Double shortQty) {
			this.shortQty = shortQty;
		}

		public Double getCrnAmount() {
			return crnAmount;
		}

		public void setCrnAmount(Double crnAmount) {
			this.crnAmount = crnAmount;
		}

		public String getRoId() {
			return RoId;
		}

		public void setRoId(String roId) {
			RoId = roId;
		}

		public int getCrnStatus() {
			return crnStatus;
		}

		public void setCrnStatus(int crnStatus) {
			this.crnStatus = crnStatus;
		}

		public String getCreated_by() {
			return Created_by;
		}

		public void setCreated_by(String created_by) {
			Created_by = created_by;
		}

		public Date getCreationDate() {
			return creationDate;
		}

		public void setCreationDate(Date creationDate) {
			this.creationDate = creationDate;
		}

		@Override
		public String toString() {
			return "CreditNotes [crnId=" + crnId + ", crnDate=" + crnDate + ", crnNo=" + crnNo + ", shipmentDetails="
					+ shipmentDetails + ", bosQty=" + bosQty + ", actualQty=" + actualQty + ", shortQty=" + shortQty
					+ ", crnAmount=" + crnAmount + ", RoId=" + RoId + ", crnStatus=" + crnStatus + ", Created_by="
					+ Created_by + ", creationDate=" + creationDate + "]";
		}

		public CreditNotes(BigInteger crnId, String crnDate, String crnNo, String shipmentDetails, Double bosQty,
				Double actualQty, Double shortQty, Double crnAmount, String roId, int crnStatus, String created_by,
				Date creationDate) {
			super();
			this.crnId = crnId;
			this.crnDate = crnDate;
			this.crnNo = crnNo;
			this.shipmentDetails = shipmentDetails;
			this.bosQty = bosQty;
			this.actualQty = actualQty;
			this.shortQty = shortQty;
			this.crnAmount = crnAmount;
			RoId = roId;
			this.crnStatus = crnStatus;
			Created_by = created_by;
			this.creationDate = creationDate;
		}

		public CreditNotes() {
			super();
			// TODO Auto-generated constructor stub
		}
	    
	    
	    

}
