package lk.ijse.gsn.dto.tm;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString


public class PaymentTM {
    String payment_id;
    String appointment_id;
    String price;
    String time;
    String Date;
}
