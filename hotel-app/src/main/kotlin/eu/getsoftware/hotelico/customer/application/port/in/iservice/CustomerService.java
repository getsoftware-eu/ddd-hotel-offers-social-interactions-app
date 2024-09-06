package eu.getsoftware.hotelico.customer.application.port.in.iservice;

import eu.getsoftware.hotelico.clients.common.dto.CustomerDTO;
import eu.getsoftware.hotelico.customer.common.iEntity.ICustomerEntity;
import eu.getsoftware.hotelico.hotelCustomer.adapter.out.persistence.CustomerHotelCheckin;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * All methods of this class throws NullPoinrerException if a required argument is null
 */
public interface CustomerService
{
    List<CustomerDTO> getCustomers();
    
    long getCustomerHotelId(long customerId);
    
    Optional<CustomerDTO> getByEmail(String email);
    
    Set<CustomerDTO> getByHotelId(long customerId, long hotelId, boolean addStaff);
	
	CustomerDTO fillDtoWithHotelInfo(CustomerDTO dto, CustomerHotelCheckin validCheckin);
   
    Set<CustomerDTO> getByCity(long customerId, String city);
    
    Set<CustomerDTO> getCustomerCities(long customerId);

    CustomerDTO getById(long customerId, long requesterCustomerId);
    
    ICustomerEntity getEntityById(long customerId);
 
    CustomerDTO addCustomer(CustomerDTO customerDto, String password);
    
    CustomerDTO updateCustomer(CustomerDTO customerDto, int requesterId);
    
//    @Transactional
    boolean relocateGuestDealsToLoggedCustomer(ICustomerEntity customerEntity, Long guestCustomerId);

    CustomerDTO convertCustomerToDto(ICustomerEntity customerEntity, long hotelId);

    CustomerDTO convertCustomerToDto(ICustomerEntity customerEntity, boolean fullSerialization, CustomerHotelCheckin validCheckin);
    CustomerDTO convertMyCustomerToFullDto(ICustomerEntity customerEntity);
		
    CustomerDTO serializeCustomerHotelInfo(CustomerDTO dto, long hotelId, boolean fullSerialization, CustomerHotelCheckin validCheckin);
    
    CustomerDTO synchronizeCustomerToDto(CustomerDTO customerDto);
    
    @Transactional
    void deleteCustomer(CustomerDTO customerDto);
    
    String getCustomerAvatarUrl(ICustomerEntity customerEntity);

    /**
     * sometimes we need anonym customer entity
     * @return
     */
    ICustomerEntity addGetAnonymCustomer();

    boolean isStaffOrAdminId(long receiverId);
}
