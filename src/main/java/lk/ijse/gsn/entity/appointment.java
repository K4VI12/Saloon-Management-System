package lk.ijse.gsn.entity;

import lombok.*;

import java.sql.Date;
import java.sql.Time;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString


public class appointment {
    String appointment_id;
    Time time;
    Date date;
    String customer_id;
    String address;
    String pnnumber;
    String service;
    Time endtime;
}
