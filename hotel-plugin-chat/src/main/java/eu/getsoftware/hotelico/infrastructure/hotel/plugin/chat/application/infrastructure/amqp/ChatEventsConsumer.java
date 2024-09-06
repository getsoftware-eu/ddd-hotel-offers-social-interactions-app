package eu.getsoftware.hotelico.infrastructure.hotel.plugin.chat.application.infrastructure.amqp;

import eu.getsoftware.hotelico.clients.infrastructure.notification.ChatMessageRequest;
import eu.getsoftware.hotelico.clients.infrastructure.notification.CustomerUpdateRequest;
import eu.getsoftware.hotelico.infrastructure.hotel.plugin.chat.adapter.out.persistence.model.ChatMessageEntity;
import eu.getsoftware.hotelico.infrastructure.hotel.plugin.chat.adapter.out.persistence.model.ChatUserEntity;
import eu.getsoftware.hotelico.infrastructure.hotel.plugin.chat.application.infrastructure.service.ChatMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Slf4j
public class ChatEventsConsumer
{
	@Autowired
	private ChatMessageService chatMessageService;
	/**
	 * Listen for customer system update
	 * @param customerUpdateRequest
	 */
	@RabbitListener(queues = "${rabbitmq.queue.customer.update}")
	public void consumeCustomerUpdateNotification(CustomerUpdateRequest customerUpdateRequest){
		log.info("Consumed {} from queue", customerUpdateRequest);
		log.info(customerUpdateRequest.message());
		
		Optional<ChatUserEntity> updatedChatUserOptional = chatUserRepository.findById(customerUpdateRequest.customerId());
		
		ChatUserEntity entity;
		
		if(updatedChatUserOptional.isEmpty())
		{
			entity = new ChatUserEntity(customerUpdateRequest.customerId());
			entity.setFirstName(customerUpdateRequest.customerName());
		}
		else {
			ChatUserEntity updatedChatUser = updatedChatUserOptional.get();
			entity = chatUserRepository.findByUserId(updatedChatUser.getId());
			
			entity.setEmail(updatedChatUser.getEmail());
			entity.setFirstName(updatedChatUser.getFirstName());
		}
		
		ChatUserEntity persistedEntity = chatUserRepository.save(entity);
		
	}
	
	@RabbitListener(queues = "${rabbitmq.queue.chat.request}")
	public void consumeCustomerUpdateNotification(ChatMessageRequest chatMessageRequest){
		log.info("Consumed {} from queue", chatMessageRequest);
		log.info(chatMessageRequest.customMsg());
		
		Optional<ChatMessageEntity> chatMsgOpt;
		
		if(chatMessageRequest.lastMessage())
		{
			chatMsgOpt = chatMessageService.getLastChatMessage(chatMessageRequest.fromCustomerId(), chatMessageRequest.toCustomerId());
		}
		
		
		
	}
}
