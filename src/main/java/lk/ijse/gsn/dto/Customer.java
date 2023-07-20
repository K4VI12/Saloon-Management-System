package lk.ijse.gsn.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString


public class Customer {
    private String customer_id;
    private String fist_name;
    private String second_name;
    private String contact_number;
    private String NIC;
    private String address;
}
