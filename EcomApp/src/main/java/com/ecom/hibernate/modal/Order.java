package com.ecom.hibernate.modal;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;

@Entity
public class Order implements Serializable{
	
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	private long id;
	private Date orderDate;
	private Date deliveryDate;
	private boolean isReturned=false;
	private String returnReason;
	private boolean isCanceled=false;
	private String canceledBy;
	private Date cancelDate;
	private String cancelReason;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private EcomUser ecomUser;
	
	@ManyToOne
	@JoinColumn(name="seller_id")
	private Seller seller;

	public long getId() {
		return id;
	}

	@NotNull
	public void setId(long id) {
		this.id = id;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public boolean isReturned() {
		return isReturned;
	}

	public void setReturned(boolean isReturned) {
		this.isReturned = isReturned;
	}

	public String getReturnReason() {
		return returnReason;
	}

	public void setReturnReason(String returnReason) {
		this.returnReason = returnReason;
	}

	public EcomUser getEcomUser() {
		return ecomUser;
	}

	public void setEcomUser(EcomUser ecomUser) {
		this.ecomUser = ecomUser;
	}

	public Seller getSeller() {
		return seller;
	}

	public void setSeller(Seller seller) {
		this.seller = seller;
	}

	public boolean isCanceled() {
		return isCanceled;
	}

	public void setCanceled(boolean isCanceled) {
		this.isCanceled = isCanceled;
	}

	public String getCanceledBy() {
		return canceledBy;
	}

	public void setCanceledBy(String canceledBy) {
		this.canceledBy = canceledBy;
	}

	public Date getCancelDate() {
		return cancelDate;
	}

	public void setCancelDate(Date cancelDate) {
		this.cancelDate = cancelDate;
	}
	

}
