package eu.getsoftware.hotelico.hotelapp.application.hotel.domain.infrastructure.service;

import eu.getsoftware.hotelico.hotelapp.application.hotel.port.out.iPortService.LastMessagesService;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

/**
 *  * http://www.theotherian.com/2014/03/spring-boot-websockets-stomp-chat.html?m=1
 */
public class ActiveCustomerPinger
{
  private HotelRabbitMQProducer hotelRabbitMQProducer;
  private LastMessagesService lastMessagesService;

  public ActiveCustomerPinger(HotelRabbitMQProducer hotelRabbitMQProducer, LastMessagesService lastMessagesService) {
    this.hotelRabbitMQProducer = hotelRabbitMQProducer;
    this.lastMessagesService = lastMessagesService;
  }
  
  @Scheduled(fixedDelay = 2000)
  public void pingUsers() {
    List<Long> activeUsers = lastMessagesService.getOnlineCustomerIds();
    hotelRabbitMQProducer.produceSimpWebsocketMessage("/topic/active", activeUsers);
  }

}
