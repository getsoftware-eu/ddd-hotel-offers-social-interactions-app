package eu.getsoftware.hotelico.clients.api.infrastructure.hotel.jpaTests;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:tc:postgresql:13.2-alpine://payment"
})
class HotelRepositoryTests
{
//    @Autowired
//    private CheckinRepository checkinRepository;    
//    @Autowired
//    private CustomerRepository customerRepository;
//    @Autowired
//    private TestEntityManager entityManager;

//    @Test
//    void existingPaymentCanBeFound() {
//        Customer customer = new Customer();
//        CustomerHotelCheckin checkin = new CustomerHotelCheckin();
//
//        Long customerId = entityManager.persist(customer).getId();
//        entityManager.persist(checkin);
//
//        Optional<Customer> savedCustomer = customerRepository.findById(customerId);
//
//        assertThat(savedCustomer).isPresent();
//        assertThat(savedCustomer.get().getFirstName().isBlank()).isFalse();
//    }

//    @Test
//    void paymentsAreUniquePerOrder() {
//        Customer order = new Customer();
//        CustomerHotelCheckin first = new CustomerHotelCheckin();
//        CustomerHotelCheckin second = new CustomerHotelCheckin();
//
//        entityManager.persist(order);
//        entityManager.persist(first);
//
//        assertThrows(PersistenceException.class, () -> entityManager.persistAndFlush(second));
//    }

//    @Test
//    @Sql("/multiple-payments.sql")
//    void findPaymentsAfterDate() {
//        List<Payment> payments = paymentRepository.findAllAfter(LocalDateTime.now().minusDays(1));
//
//        assertThat(payments).extracting("order.id").containsOnly(1L);
//    }

//    @Test
//    @Sql("/multiple-payments.sql")
//    void findPaymentsByCreditCard() {
//        List<Payment> payments = paymentRepository.findByCreditCardNumber("4532756279624064");
//
//        assertThat(payments).extracting("order.id").containsOnly(1L);
//    }
}
