package lk.ijse.gsn.dto.tm;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString

public class AppointmentTM {
    private String appointmentid;
    private String time;
    private String date;
    private String customer_id;
    private String address;
    private String pnnumber;
    private String service;
    private String endtime;
}
