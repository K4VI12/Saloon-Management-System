package lk.ijse.gsn.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class Payment {
    String payment_id;
    String appointment_id;
    String price;
    String time;
    String Date;
}
