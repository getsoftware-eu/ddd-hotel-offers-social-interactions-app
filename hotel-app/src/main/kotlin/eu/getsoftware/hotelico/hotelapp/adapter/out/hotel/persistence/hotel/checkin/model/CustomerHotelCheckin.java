package eu.getsoftware.hotelico.hotelapp.adapter.out.hotel.persistence.hotel.checkin.model;

import eu.getsoftware.hotelico.clients.common.utils.HibernateUtils;
import eu.getsoftware.hotelico.hotelapp.adapter.out.hotel.persistence.hotel.customer.model.CustomerRootEntity;
import eu.getsoftware.hotelico.hotelapp.adapter.out.hotel.persistence.hotel.hotel.model.HotelRootEntity;
import eu.getsoftware.hotelico.hotelapp.application.checkin.domain.model.ICustomerHotelCheckinEntity;
import eu.getsoftware.hotelico.hotelapp.application.customer.domain.model.ICustomerRootEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;


@Data
@Entity
@Table(name = "customer_hotel_checkin", schema = "hotel")
@AssociationOverrides({
		@AssociationOverride(name = "pk.customer",
				joinColumns = @JoinColumn(name = "CUSTOMER_ID")),
		@AssociationOverride(name = "pk.hotel",
				joinColumns = @JoinColumn(name = "HOTEL_ID")) })
public class CustomerHotelCheckin implements ICustomerHotelCheckinEntity, java.io.Serializable {

	private static final long serialVersionUID = -2949611288215768311L;
	
	@Column(name = "active", columnDefinition = HibernateUtils.ColumnDefinition.BOOL_DEFAULT_TRUE)
	private boolean active = true;

	private CustomerHotelCheckinId pk = new CustomerHotelCheckinId();
	
	@Column(name = "fullCheckin", columnDefinition = HibernateUtils.ColumnDefinition.BOOL_DEFAULT_FALSE)
	private boolean fullCheckin = false;	
	
	@Column(name = "staffCheckin", columnDefinition = HibernateUtils.ColumnDefinition.BOOL_DEFAULT_FALSE)
	private boolean staffCheckin = false;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "validFrom", nullable = false, length = 10)
	private Date validFrom;

	@Temporal(TemporalType.DATE)
	@Column(name = "validTo", nullable = false, length = 10)
	private Date validTo;
	
	public CustomerHotelCheckin() {
		super();
	}

	@EmbeddedId
	public CustomerHotelCheckinId getPk() {
		return pk;
	}

	public void setPk(CustomerHotelCheckinId pk) {
		this.pk = pk;
	}
	
	public void setCustomer(CustomerRootEntity customerEntity) {
		getPk().setCustomer(customerEntity);
	}

	@Transient
	public HotelRootEntity getHotel() {
		return getPk().getHotel();
	}
	
	@Transient
	public ICustomerRootEntity getCustomer() {
		return getPk().getCustomer();
	}
	
	public void setHotel(HotelRootEntity category) {
		getPk().setHotel(category);
	}

	public int hashCode() {
		return (getPk() != null ? getPk().hashCode() : 0);
	}
	
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		
		CustomerHotelCheckin that = (CustomerHotelCheckin) o;
		
		if (getPk() != null ? !getPk().equals(that.getPk())
				: that.getPk() != null)
			return false;
		
		return true;
	}
}