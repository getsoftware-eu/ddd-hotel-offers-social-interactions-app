package eu.getsoftware.hotelico.hotelapp.application.chat.port.out;

import eu.getsoftware.hotelico.clients.api.clients.common.dto.CustomerDTO;

/**
 * only DTOs betwenn different domains!!!
 */
public interface IChatService {

     void sendFirstChatMessageOnDemand(CustomerDTO customerEntity, CustomerDTO staffSender, boolean isFullCheckin); 
        
}
