package com.movie.booking.app.service;

import com.movie.booking.app.models.Booking;
import com.movie.booking.app.repository.BookingRepository;
import com.razorpay.Order;
import com.razorpay.Payment;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
@Service
public class BookingService {

    private static final Logger logger = LogManager.getLogger(BookingService.class);

    @Autowired
    private BookingRepository bookingRepository;

    @Value("${razorpay.key.id}")
    private String razorPayId;

    @Value("${razorpay.key.secret}")
    private String razorPaySecret;

    private RazorpayClient razorpayClient;

    @PostConstruct
    public void init() throws RazorpayException {
        this.razorpayClient = new RazorpayClient(razorPayId, razorPaySecret);
    }

    public Booking createBooking(Booking booking) throws RazorpayException {
        JSONObject orderJson = new JSONObject();

        // Amount will be paise , so multiple the amount by 100
        orderJson.put("amount", booking.getAmount() * 100);
        orderJson.put("currency", "INR");
        orderJson.put("receipt", booking.getEmail());

        // Create Razorpay Order
        Order razorpayOrder = razorpayClient.orders.create(orderJson);
        logger.info("Razorpay Order : " + razorpayOrder);

        // Set razor pay details into our order object
        booking.setRazorpayOrderId(razorpayOrder.get("id"));
        booking.setBookingStatus(razorpayOrder.get("status"));

        // Save and return booking
        return bookingRepository.save(booking);
    }

    public Booking updateStatus(Map<String, String> map) {
        Booking booking = null;

        try {
            String razorpayId = map.get("razorpay_order_id");
            logger.info("Razor Payment ID : " + razorpayId);

            // Get the order by razor pay id
            Booking order = bookingRepository.findByRazorpayOrderId(razorpayId);
            order.setBookingStatus("paid");

            // get the payment details from Razor pay
            Map<String, String> paymentDetails = getPaymentDetailsByOrderId(razorpayId);
            String paymentId = paymentDetails.get("paymentId");
            String paymentStatus = paymentDetails.get("paymentStatus");
            logger.info("Razor Payment ID : " + razorpayId+
                    " , Payment Id : "+paymentId+ " , Payment Status : "+paymentStatus);

            // Add payment details to our order
            order.setPaymentId(paymentId);
            order.setPaymentStatus(paymentStatus);

            // Save Order
            booking = bookingRepository.save(order);
        } catch(Exception e) {
            logger.info("Error in Razor payment update : "+e.getMessage());
        }
        return booking;
    }

    public Map<String, String> getPaymentDetailsByOrderId(String orderId) {
        Map<String, String> paymentDetails = new HashMap<>();

        try {
            // Fetch payments for the given Order ID
            JSONObject options = new JSONObject();

            // Fetch max 10 payments for the order
            options.put("count", 10);
            Payment[] payments = razorpayClient.orders.fetchPayments(orderId).toArray(new Payment[0]);

            if (payments.length > 0) {
                Payment payment = payments[0]; // Assuming only one payment per order
                logger.info("Payment Details : "+payment);

                paymentDetails.put("paymentId", payment.get("id"));
                paymentDetails.put("paymentStatus", payment.get("status")); // "captured", "failed", etc.
            } else {
                paymentDetails.put("error", "No payments found for this order ID.");
            }

        } catch (RazorpayException e) {
            paymentDetails.put("error", "Error fetching payment details: " + e.getMessage());
        }

        return paymentDetails;
    }
}
