package lk.ijse.gsn.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class Appoinment {
    private String appointmentid;
    private String time;
    private String date;
    private String customer_id;
    private String address;
    private String pnnumber;
    private String service;
    private String endtime;
}
