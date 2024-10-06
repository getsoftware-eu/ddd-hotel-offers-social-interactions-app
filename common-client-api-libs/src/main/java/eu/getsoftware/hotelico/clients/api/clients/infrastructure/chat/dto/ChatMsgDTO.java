package eu.getsoftware.hotelico.clients.api.clients.infrastructure.chat.dto;

import java.sql.Timestamp;
import java.util.Map;

public record ChatMsgDTO(
		long initId,
		String message,
		long senderId,
		long receiverId,
		boolean hotelStaff,
		boolean seenByReceiver,
		boolean deliveredToReceiver,
		long creationTime,
		Timestamp timestamp,
		Map<String, String> specialContent,
		boolean active
//		String specialChatContent
    ) /*implements IChatMessageView*/
{
	

//    Map specialContent = HashMap<String, String>();
//	
//	Date getSendTime() 
//    {
//        return if(timestamp != null) Date(timestamp?.time!!) else null
//    }
//	
//	String getSendTimeString() 
//    {
//        return if(timestamp != null) eu.getsoftware.hotelico.hotel.clients.chat.utils.ChatUtils.getTimeFormatted(timestamp) else null
//    }

}
